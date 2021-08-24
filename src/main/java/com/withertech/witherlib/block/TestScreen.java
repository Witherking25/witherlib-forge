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

import com.mojang.blaze3d.matrix.MatrixStack;
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class TestScreen extends TileEntityBaseContainerScreen<TestTileEntity, TestContainer>
{
    public TestScreen(TestContainer screenContainer, PlayerInventory playerInventory, ITextComponent title)
    {
        super(screenContainer, title);
    }

    @Override
    protected int sizeX(@Nonnull TestTileEntity object)
    {
        return 200;
    }

    @Override
    protected int sizeY(@Nonnull TestTileEntity object)
    {
        return 200;
    }

    @Override
    protected void addWidgets(@Nonnull TestTileEntity object)
    {

    }


}
