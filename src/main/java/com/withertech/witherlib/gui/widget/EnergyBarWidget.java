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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.gui.ScreenUtils;
import com.withertech.witherlib.util.EnergyFormat;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.EnergyStorage;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Created 11/17/2020 by SuperMartijn642
 */
public class EnergyBarWidget extends AbstractButtonWidget implements IHoverTextWidget
{

	private static final ResourceLocation BARS = WitherLib.INSTANCE.MOD.modLocation("textures/gui/energy_bars.png");

	private static final int WIDTH = 20;
	private static final int HEIGHT = 52;

	private final Supplier<EnergyStorage> energy;

	public EnergyBarWidget(int x, int y, Supplier<EnergyStorage> energy)
	{
		this(x, y, 1, energy);
	}

	public EnergyBarWidget(int x, int y, int scale, Supplier<EnergyStorage> energy)
	{
		super(x, y, WIDTH * scale, HEIGHT * scale, () -> EnergyFormat.cycleEnergyType(!Screen.hasShiftDown()));
		this.energy = energy;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		ScreenUtils.bindTexture(BARS);
//		RenderSystem.enableAlphaTest();
		ScreenUtils.drawTexture(
				poseStack,
				this.x,
				this.y,
				this.width,
				this.height,
				this.isHovered() ? 1 / 11f : 0,
				0,
				1 / 11f,
				1
		);
		int energy = this.energy.get().getEnergyStored();
		int capacity = this.energy.get().getMaxEnergyStored();
		float percentage = capacity == 0 ? 1 : Math.max(Math.min(energy / (float) capacity, 1), 0);
		if (percentage != 0)
		{
			ScreenUtils.drawTexture(
					poseStack,
					this.x,
					this.y + this.height * (1 - percentage),
					this.width,
					this.height * percentage,
					(EnergyFormat.getType() == EnergyFormat.EnergyType.RF)? 2 / 11f : 3 / 11f,
					1 - percentage,
					1 / 11f,
					percentage
			);
		}
//		RenderSystem.disableAlphaTest();
	}

	@Override
	public List<Component> getHoverText()
	{
		int energy = this.energy.get().getEnergyStored();
		int capacity = this.energy.get().getMaxEnergyStored();
		return Collections.singletonList(TextComponents.string(EnergyFormat.formatCapacity(energy, capacity)).get());
	}

	@Override
	protected List<Component> getNarrationMessage()
	{
		return this.getHoverText();
	}
}
