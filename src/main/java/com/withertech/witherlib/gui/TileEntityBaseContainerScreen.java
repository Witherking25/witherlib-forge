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

package com.withertech.witherlib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class TileEntityBaseContainerScreen<T extends TileEntity, X extends TileEntityBaseContainer<X, T>> extends ObjectBaseContainerScreen<T, X>
{
    public TileEntityBaseContainerScreen(X screenContainer, PlayerInventory inventory, ITextComponent title)
    {
        super(screenContainer, title);
    }


    protected abstract int sizeX(@Nonnull T tile);

    protected abstract int sizeY(@Nonnull T tile);

    protected abstract void addWidgets(@Nonnull T tile);

    protected void tick(@Nonnull T tile)
    {
    }

    protected void renderBackground(MatrixStack matrixStack, int mouseX, int mouseY, @Nonnull T tile)
    {
        super.renderBackground(matrixStack, mouseX, mouseY, tile);
    }

    protected void renderForeground(MatrixStack matrixStack, int mouseX, int mouseY, @Nonnull T tile)
    {
        super.renderForeground(matrixStack, mouseX, mouseY, tile);
    }

    protected void renderTooltips(MatrixStack matrixStack, int mouseX, int mouseY, @Nonnull T tile)
    {
        super.renderTooltips(matrixStack, mouseX, mouseY, tile);
    }


}
