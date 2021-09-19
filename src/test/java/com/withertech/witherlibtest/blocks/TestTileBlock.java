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

package com.withertech.witherlibtest.blocks;

import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.containers.TestContainer;
import com.withertech.witherlibtest.tiles.TestTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestTileBlock extends BaseTileBlock<TestTile>
{
    public TestTileBlock()
    {
        super(true, AbstractBlock.Properties.of(Material.STONE));
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return new TestContainer(id, player, pos);
    }

    @Override
    protected ITextComponent getDisplayName(TestTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderShape(@Nonnull BlockState state)
    {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected boolean hasContainer()
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.baseTile("test_tile", TestTile.class)).get().create();
    }
}
