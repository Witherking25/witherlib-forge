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
import com.withertech.witherlibtest.containers.TestProgressContainer;
import com.withertech.witherlibtest.tiles.TestProgressTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TestProgressBlock extends BaseTileBlock<TestProgressTile>
{
    public TestProgressBlock()
    {
        super(true, Properties.of(Material.METAL));
    }

    @Override
    protected boolean hasContainer()
    {
        return true;
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return new TestProgressContainer(id, player, pos);
    }

    @Override
    protected ITextComponent getDisplayName(TestProgressTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_progress_tile", TestProgressTile.class)).get().create();
    }

//    @Override
//    public void neighborChanged(BlockState p_220069_1_, World p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_)
//    {
//        super.neighborChanged(p_220069_1_, p_220069_2_, p_220069_3_, p_220069_4_, p_220069_5_, p_220069_6_);
//        if (!p_220069_2_.isClientSide())
//        {
//            TestTile tile = (TestTile) p_220069_2_.getBlockEntity(p_220069_3_);
//            assert tile != null;
//            if (tile.running != p_220069_2_.hasNeighborSignal(p_220069_3_))
//                tile.running = !tile.running;
//        }
//    }
}
