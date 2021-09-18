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
import com.mojang.blaze3d.systems.RenderSystem;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.util.ScreenUtils;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class FluidTankWidget extends AbstractButtonWidget implements IHoverTextWidget
{
	private static final ResourceLocation TANK = WitherLib.INSTANCE.MOD.modLocation("textures/gui/fluid_tank.png");

	private static final int WIDTH = 20;
	private static final int HEIGHT = 52;

	private final Supplier<FluidTank> tank;

	public FluidTankWidget(int x, int y, Supplier<FluidTank> tank, Runnable onPress)
	{
		this(x, y, 1, tank, onPress);
	}

	public FluidTankWidget(int x, int y, int scale, Supplier<FluidTank> tank, Runnable onPress)
	{
		super(x, y, WIDTH * scale, HEIGHT * scale, onPress);
		this.tank = tank;
	}

	@Override
	public List<ITextComponent> getHoverText()
	{
		int amount = this.tank.get().getFluidAmount();
		int capacity = this.tank.get().getCapacity();
		List<ITextComponent> text = new ArrayList<>();
		text.add(TextComponents.string(
				((!tank.get().getFluid().isEmpty()) ?
						(TextComponents.fluid(tank.get().getFluid().getFluid()).get().getString()) :
						"Empty") + ": ").get());
		text.add(TextComponents.string(
				NumberFormat.getNumberInstance(Locale.getDefault()).format(amount) +
						" / " +
						NumberFormat.getNumberInstance(Locale.getDefault()).format(capacity) +
						" mb").get());
		return text;
	}


	@Override
	protected List<ITextComponent> getNarrationMessage()
	{
		return this.getHoverText();
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		ScreenUtils.drawFluidStack(matrixStack, tank.get().getFluid(), tank.get().getCapacity(), x, y, width, height);
		ScreenUtils.bindTexture(TANK);
		//noinspection deprecation
		RenderSystem.enableAlphaTest();
		ScreenUtils.drawTexture(
				matrixStack,
				this.x,
				this.y,
				this.width,
				this.height,
				this.isHovered() ? 1 / 2f : 0,
				0,
				1 / 2f,
				1
		);
		//noinspection deprecation
		RenderSystem.disableAlphaTest();
	}
}
