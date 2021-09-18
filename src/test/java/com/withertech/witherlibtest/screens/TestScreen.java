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

package com.withertech.witherlibtest.screens;

import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import com.withertech.witherlibtest.containers.TestContainer;
import com.withertech.witherlibtest.tiles.TestTile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;

public class TestScreen extends TileEntityBaseContainerScreen<TestTile, TestContainer>
{
    public TestScreen(TestContainer screenContainer, Inventory playerInventory, Component title)
    {
        super(screenContainer, playerInventory, title);
    }

    @Override
    protected int sizeX(@Nonnull TestTile object)
    {
        return 176;
    }

    @Override
    protected int sizeY(@Nonnull TestTile object)
    {
        return 222;
    }

    @Override
    protected void addWidgets(@Nonnull TestTile object)
    {

    }


}
