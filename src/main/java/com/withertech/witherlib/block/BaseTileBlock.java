/*
 * witherlib-forge
 * Copyright (C) 2021 WitherTech
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.withertech.witherlib.block;

import com.withertech.witherlib.tile.BaseTileEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ContainerBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class BaseTileBlock<T extends BaseTileEntity<T>> extends ContainerBlock
{
    private final boolean saveTileData;

    public BaseTileBlock(boolean saveTileData, Properties properties)
    {
        super(properties);
        this.saveTileData = saveTileData;
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(@Nonnull BlockState state)
    {
        return BlockRenderType.MODEL;
    }


    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public ActionResultType use(@Nonnull BlockState state, World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult rayTraceResult)
    {
        if (!world.isClientSide() && hasContainer())
        {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider()
            {
                @Nonnull
                @Override
                public ITextComponent getDisplayName()
                {
                    return BaseTileBlock.this.getDisplayName((T) world.getBlockEntity(pos));
                }

                @Override
                public Container createMenu(int id, @Nonnull PlayerInventory playerInventory, @Nonnull PlayerEntity player)
                {
                    return BaseTileBlock.this.createMenu(id, player, pos);
                }
            }, pos);
        }
        return ActionResultType.CONSUME;
    }

    protected abstract boolean hasContainer();

    protected abstract Container createMenu(int id, PlayerEntity player, BlockPos pos);

    protected abstract ITextComponent getDisplayName(T tile);

    @Override
    public void setPlacedBy(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull BlockState state, LivingEntity placer, @Nonnull ItemStack stack)
    {
        if (!this.saveTileData)
        {
            return;
        }

        CompoundNBT tag = stack.getTag();
        tag = tag == null ? null : tag.contains("tileData") ? tag.getCompound("tileData") : null;
        if (tag == null || tag.isEmpty())
        {
            return;
        }

        TileEntity tile = worldIn.getBlockEntity(pos);
        if (tile instanceof BaseTileEntity)
        {
            ((BaseTileEntity) tile).readData(tag);
        }
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder)
    {
        List<ItemStack> items = super.getDrops(state, builder);

        if (!this.saveTileData)
        {
            return items;
        }

        TileEntity tile = builder.getOptionalParameter(LootParameters.BLOCK_ENTITY);
        if (!(tile instanceof BaseTileEntity))
        {
            return items;
        }

        CompoundNBT tileTag = ((BaseTileEntity) tile).writeItemStackData();
        if (tileTag == null || tileTag.isEmpty())
        {
            return items;
        }

        CompoundNBT tag = new CompoundNBT();
        tag.put("tileData", tileTag);

        for (ItemStack stack : items)
        {
            if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == this)
            {
                stack.setTag(tag);
            }
        }

        return items;
    }

    @Override
    public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
    {
        ItemStack stack = super.getPickBlock(state, target, world, pos, player);

        if (!this.saveTileData)
        {
            return stack;
        }

        TileEntity tile = world.getBlockEntity(pos);
        if (!(tile instanceof BaseTileEntity))
        {
            return stack;
        }

        CompoundNBT tileTag = ((BaseTileEntity) tile).writeItemStackData();
        if (tileTag == null || tileTag.isEmpty())
        {
            return stack;
        }

        CompoundNBT tag = new CompoundNBT();
        tag.put("tileData", tileTag);

        if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == this)
        {
            stack.setTag(tag);
        }

        return stack;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return newBlockEntity(world);
    }
}
