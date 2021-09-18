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

package com.withertech.witherlib.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.withertech.witherlib.util.ScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;

/**
 * Created 10/15/2020 by SuperMartijn642
 */
public class ButtonWidget extends AbstractButtonWidget
{

	private ITextComponent text;

	/**
	 * @param text    the text to be displayed on the button
	 * @param onPress the action which will called when the user clicks the
	 *                widget
	 */
	public ButtonWidget(int x, int y, int width, int height, ITextComponent text, Runnable onPress)
	{
		super(x, y, width, height, onPress);
		this.text = text;
	}

	/**
	 * Sets the text which is displayed on the button.
	 */
	public void setText(ITextComponent text)
	{
		this.text = text;
	}

	@Override
	protected List<ITextComponent> getNarrationMessage()
	{
		return Collections.singletonList(this.text);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		ScreenUtils.drawButtonBackground(
				matrixStack,
				this.x,
				this.y,
				this.width,
				this.height,
				0,
				(this.active ? this.isHovered() ? 5 : 0 : 10) / 15f
		);
		ScreenUtils.drawCenteredStringWithShadow(
				matrixStack,
				Minecraft.getInstance().font,
				this.text,
				this.x + this.width / 2f,
				this.y + this.height / 2f - 5,
				this.active ? 0xFFFFFFFF : Integer.MAX_VALUE
		);
	}
}
