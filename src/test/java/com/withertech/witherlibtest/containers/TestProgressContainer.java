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

package com.withertech.witherlibtest.containers;

import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.tiles.TestProgressTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TestProgressContainer extends TileEntityBaseContainer<TestProgressContainer, TestProgressTile>
{
    public TestProgressContainer(int id, Player player, BlockPos tilePos)
    {
        super(WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_progress_container", TestProgressContainer.class)).get(), id, player, tilePos);
        addSlots();
    }

    @Override
    protected void addSlots(Player player, @Nonnull TestProgressTile object)
    {
        addPlayerSlots(8, 140);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player p_82846_1_, int p_82846_2_)
    {
        return ItemStack.EMPTY;
    }
}
