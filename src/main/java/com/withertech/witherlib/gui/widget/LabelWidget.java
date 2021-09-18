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

import com.mojang.blaze3d.vertex.PoseStack;
import com.withertech.witherlib.gui.ScreenUtils;
import net.minecraft.network.chat.Component;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created 10/29/2020 by SuperMartijn642
 */
public class LabelWidget extends Widget
{

	private final Supplier<Component> text;

	/**
	 * @param text the text to be displayed on the label
	 */
	public LabelWidget(int x, int y, int width, int height, Supplier<Component> text)
	{
		super(x, y, width, height);
		this.text = text;
	}

	/**
	 * @param text the text to be displayed on the label
	 */
	public LabelWidget(int x, int y, int width, int height, Component text)
	{
		this(x, y, width, height, () -> text);
	}

	@Override
	protected List<Component> getNarrationMessage()
	{
		return Collections.singletonList(this.text.get());
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		if (this.active)
		{
			ScreenUtils.fillRect(
					poseStack,
					this.x - 1,
					this.y - 1,
					this.x + this.width + 1,
					this.y + this.height + 1,
					-6250336
			);
			ScreenUtils.fillRect(poseStack, this.x, this.y, this.x + this.width, this.y + this.height, 0xff404040);

			ScreenUtils.drawCenteredStringWithShadow(
					poseStack,
					text.get(),
					this.x,
					this.y + 2,
					this.active ? ScreenUtils.ACTIVE_TEXT_COLOR : ScreenUtils.INACTIVE_TEXT_COLOR
			);
		}
	}
}
