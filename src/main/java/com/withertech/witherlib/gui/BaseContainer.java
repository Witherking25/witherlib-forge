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

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

/**
 * Created 1/19/2021 by SuperMartijn642
 */
public abstract class BaseContainer extends AbstractContainerMenu
{

	public final Player player;
	public final Level world;

	public BaseContainer(MenuType<?> type, int id, Player player)
	{
		super(type, id);
		this.player = player;
		this.world = player.level;
	}


	/**
	 * Adds slots to the container by calling {@link #addSlots(Player)}.
	 */
	protected void addSlots()
	{
		this.addSlots(this.player);
	}

	/**
	 * Adds slots to the container
	 */
	protected abstract void addSlots(Player player);

	/**
	 * Adds the player's slots to the container at the given {@code x} and {@code y}.
	 *
	 * @param x the x-coordinate of the left side of the left most slots
	 * @param y the y-coordinate of the top edge of the top most slots
	 */
	protected void addPlayerSlots(int x, int y)
	{
		// player
		for (int row = 0; row < 3; row++)
		{
			for (int column = 0; column < 9; column++)
			{
				this.addSlot(new Slot(this.player.getInventory(), row * 9 + column + 9, x + 18 * column, y + 18 * row));
			}
		}

		// hot bar
		for (int column = 0; column < 9; column++)
		{
			this.addSlot(new Slot(this.player.getInventory(), column, x + 18 * column, y + 58));
		}
	}

	@Override
	public boolean stillValid(@Nonnull Player playerIn)
	{
		return true;
	}
}
