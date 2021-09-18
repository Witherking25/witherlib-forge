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

import com.withertech.witherlib.item.IWrench;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlib.tile.ITickableTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class BaseTileBlock<T extends BaseTileEntity<T>> extends BaseEntityBlock
{
	private final boolean saveTileData;

	private final BlockEntityTicker<T> ticker = (world, pos, state, tile) ->
	{
		if (tile instanceof ITickableTileEntity)
		{
			((ITickableTileEntity) tile).tick();
		}
	};

	public BaseTileBlock(boolean saveTileData, Properties properties)
	{
		super(properties);
		this.saveTileData = saveTileData;
	}

	@Nonnull
	@Override
	public RenderShape getRenderShape(@Nonnull BlockState state)
	{
		return RenderShape.MODEL;
	}

	@SuppressWarnings({"deprecation", "unchecked"})
	@Override
	@Nonnull
	public InteractionResult use(
			@Nonnull BlockState state,
			@Nonnull Level world,
			@Nonnull BlockPos pos,
			@Nonnull Player player,
			@Nonnull InteractionHand hand,
			@Nonnull BlockHitResult rayTraceResult
	)
	{
		if (player.getItemInHand(hand).getItem() instanceof IWrench wrench)
		{
			wrench.wrench(state, world, pos, player, hand, rayTraceResult);
			if (state.getBlock() instanceof IWrenchable wrenchable)
			{
				wrenchable.wrench(state, world, pos, player, hand, rayTraceResult);
			}
			if (world.getBlockEntity(pos) instanceof IWrenchable wrenchable)
			{
				wrenchable.wrench(state, world, pos, player, hand, rayTraceResult);
			}
			return InteractionResult.CONSUME;
		}
		else if (hasContainer())
		{
			if (!world.isClientSide())
			{
				NetworkHooks.openGui((ServerPlayer) player, new MenuProvider()
				{
					@Nonnull
					@Override
					public Component getDisplayName()
					{
						return BaseTileBlock.this.getDisplayName((T) world.getBlockEntity(pos));
					}

					@Override
					public AbstractContainerMenu createMenu(
							int id,
							@Nonnull Inventory playerInventory,
							@Nonnull Player player
					)
					{
						return BaseTileBlock.this.createMenu(id, player, pos);
					}
				}, pos);
			}
			return InteractionResult.CONSUME;
		}
		else
		{
			return InteractionResult.PASS;
		}

	}

	protected abstract boolean hasContainer();

	protected abstract AbstractContainerMenu createMenu(int id, Player player, BlockPos pos);

	protected abstract Component getDisplayName(T tile);

	@SuppressWarnings("rawtypes")
	@Override
	public void setPlacedBy(
			@Nonnull Level worldIn,
			@Nonnull BlockPos pos,
			@Nonnull BlockState state,
			LivingEntity placer,
			@Nonnull ItemStack stack
	)
	{
		if (!this.saveTileData)
		{
			return;
		}

		CompoundTag tag = stack.getTag();
		tag = tag == null ? null : tag.contains("tileData") ? tag.getCompound("tileData") : null;
		if (tag == null || tag.isEmpty())
		{
			return;
		}

		BlockEntity tile = worldIn.getBlockEntity(pos);
		if (tile instanceof BaseTileEntity)
		{
			((BaseTileEntity) tile).readData(tag);
		}
	}

	@SuppressWarnings({"deprecation", "rawtypes"})
	@Nonnull
	@Override
	public List<ItemStack> getDrops(@Nonnull BlockState state, @Nonnull LootContext.Builder builder)
	{
		List<ItemStack> items = super.getDrops(state, builder);

		if (!this.saveTileData)
		{
			return items;
		}

		BlockEntity tile = builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
		if (!(tile instanceof BaseTileEntity))
		{
			return items;
		}

		CompoundTag tileTag = ((BaseTileEntity) tile).writeItemStackData();
		if (tileTag == null || tileTag.isEmpty())
		{
			return items;
		}

		CompoundTag tag = new CompoundTag();
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

	@SuppressWarnings("rawtypes")
	@Override
	public ItemStack getPickBlock(
			BlockState state,
			HitResult target,
			BlockGetter world,
			BlockPos pos,
			Player player
	)
	{
		ItemStack stack = super.getPickBlock(state, target, world, pos, player);

		if (!this.saveTileData)
		{
			return stack;
		}

		BlockEntity tile = world.getBlockEntity(pos);
		if (!(tile instanceof BaseTileEntity))
		{
			return stack;
		}

		CompoundTag tileTag = ((BaseTileEntity) tile).writeItemStackData();
		if (tileTag == null || tileTag.isEmpty())
		{
			return stack;
		}

		CompoundTag tag = new CompoundTag();
		tag.put("tileData", tileTag);

		if (stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() == this)
		{
			stack.setTag(tag);
		}

		return stack;
	}

	public abstract BlockEntityType<T> getBlockEntityType();

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@Nonnull BlockPos pos, @Nonnull BlockState state)
	{
		return getBlockEntityType().create(pos, state);
	}

	@Nullable
	@Override
	public <X extends BlockEntity> BlockEntityTicker<X> getTicker(@Nonnull Level world, @Nonnull BlockState state, @Nonnull BlockEntityType<X> type)
	{
		return world.isClientSide()? null : BaseTileBlock.createTickerHelper(type, getBlockEntityType(), ticker);
	}
}
