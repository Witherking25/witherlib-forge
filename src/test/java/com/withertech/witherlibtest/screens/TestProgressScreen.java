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
import com.withertech.witherlib.gui.widget.ProgressBarWidget;
import com.withertech.witherlib.gui.widget.ToggleButtonWidget;
import com.withertech.witherlib.util.TextComponents;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.containers.TestProgressContainer;
import com.withertech.witherlibtest.network.TestProgressTilePacket;
import com.withertech.witherlibtest.tiles.TestProgressTile;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestProgressScreen extends TileEntityBaseContainerScreen<TestProgressTile, TestProgressContainer>
{
    public TestProgressScreen(TestProgressContainer testProgressContainer, @Nullable Inventory playerInventory, Component textComponent)
    {
        super(testProgressContainer, playerInventory, textComponent);
    }

    @Override
    protected int sizeX(@Nonnull TestProgressTile tile)
    {
        return 176;
    }

    @Override
    protected int sizeY(@Nonnull TestProgressTile tile)
    {
        return 222;
    }

    @Override
    protected void addWidgets(@Nonnull TestProgressTile tile)
    {
        addWidget(new ProgressBarWidget(72, 64, tile::getProgress, tile::getMaxProgress));
        addWidget(new ToggleButtonWidget(16, 64, 48, 16, TextComponents.string("Start").get(), () -> WitherLibTest.INSTANCE.REGISTRY.getNet("main").sendToServer(new TestProgressTilePacket.TestProgressTileEnablePacket(this.container.getTilePos())), tile::isRunning));
    }
}
