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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class PacketUtils
{
	public static <T extends TileEntity> void interactCarriedItemWithFluidHandler(T tile, @Nonnull PacketContext context)
	{
		PlayerEntity player = context.getSendingPlayer();
		if (!player.inventory.getCarried().isEmpty() && player.inventory.getCarried().getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY).isPresent())
		{
			player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(playerInventory ->
					tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent(fluidTank ->
					{
						FluidActionResult fluidActionResult = FluidUtil.tryFillContainerAndStow(player.inventory.getCarried(), fluidTank, playerInventory, Integer.MAX_VALUE, player, true);
						if (!fluidActionResult.isSuccess())
						{
							fluidActionResult = FluidUtil.tryEmptyContainerAndStow(player.inventory.getCarried(), fluidTank, playerInventory, Integer.MAX_VALUE, player, true);
						}

						if (fluidActionResult.isSuccess())
						{
							player.inventory.setCarried(fluidActionResult.getResult());
							if (player instanceof ServerPlayerEntity)
							{
								((ServerPlayerEntity) player).broadcastCarriedItem();
							}
						}
					})
			);
		}
	}
}
