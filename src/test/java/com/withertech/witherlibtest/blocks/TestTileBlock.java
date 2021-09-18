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
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;

public class TestTileBlock extends BaseTileBlock<TestTile>
{
    public TestTileBlock()
    {
        super(true, Properties.of(Material.STONE));
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Player player, BlockPos pos)
    {
        return new TestContainer(id, player, pos);
    }



    @Override
    protected Component getDisplayName(TestTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Override
    public BlockEntityType<TestTile> getBlockEntityType()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_tile", TestTile.class)).get();
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@Nonnull BlockState state)
    {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected boolean hasContainer()
    {
        return true;
    }
}
