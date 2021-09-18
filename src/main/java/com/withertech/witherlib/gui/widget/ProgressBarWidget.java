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
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.util.ScreenUtils;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class ProgressBarWidget extends Widget
{
	private static final ResourceLocation PROGRESS = WitherLib.INSTANCE.MOD.modLocation("textures/gui/progress_bar"
			+ ".png");

	private final Supplier<Integer> progress;
	private final Supplier<Integer> maxProgress;

	public ProgressBarWidget(int x, int y, Supplier<Integer> progress, Supplier<Integer> maxProgress)
	{
		this(x, y, 24, 16, progress, maxProgress);
	}

	public ProgressBarWidget(
			int x,
			int y,
			int width,
			int height,
			Supplier<Integer> progress,
			Supplier<Integer> maxProgress
	)
	{
		super(x, y, width, height);
		this.progress = progress;
		this.maxProgress = maxProgress;
	}


	@Override
	protected List<ITextComponent> getNarrationMessage()
	{
		return Collections.singletonList(TextComponents.string("").get());
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		ScreenUtils.bindTexture(PROGRESS);
		float percentage = maxProgress.get() == 0 ? 1 : Math.max(Math.min(
				progress.get() / (float) maxProgress.get(),
				1
		), 0);
		ScreenUtils.drawTexture(matrixStack, this.x, this.y, this.width, this.height, 0, 0, 1, 16 / 32f);
		ScreenUtils.drawTexture(
				matrixStack,
				this.x,
				this.y,
				this.width * percentage,
				this.height,
				0,
				16 / 32f,
				percentage,
				16 / 32f
		);
	}
}
