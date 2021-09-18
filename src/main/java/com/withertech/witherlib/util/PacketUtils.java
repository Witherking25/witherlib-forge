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

package com.withertech.witherlib.util;

import com.withertech.witherlib.network.PacketContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class PacketUtils
{
	public static <T extends BlockEntity> void interactCarriedItemWithFluidHandler(T tile, @Nonnull PacketContext context)
	{
		Player player = context.getSendingPlayer();
		if (!player.containerMenu.getCarried().isEmpty() && player.containerMenu.getCarried().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent())
		{
			player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(playerInventory ->
					tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(fluidTank ->
					{
						FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(player.containerMenu.getCarried(), fluidTank, playerInventory, Integer.MAX_VALUE, player, true);
						if (!fluidActionResult.isSuccess())
						{
							fluidActionResult = FluidUtil.tryEmptyContainerAndStow(player.containerMenu.getCarried(), fluidTank, playerInventory, Integer.MAX_VALUE, player, true);
						}

						if (fluidActionResult.isSuccess())
						{
							player.containerMenu.setCarried(fluidActionResult.getResult());
						}
					})
			);
		}
	}
}
