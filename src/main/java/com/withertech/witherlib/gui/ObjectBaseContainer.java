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
import net.minecraft.world.inventory.MenuType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class ObjectBaseContainer<T> extends BaseContainer
{

	public ObjectBaseContainer(MenuType<?> type, int id, Player player)
	{
		super(type, id, player);
	}

	@Override
	protected void addSlots(Player player)
	{
		T object = this.getObjectOrClose();
		if (object != null)
		{
			this.addSlots(player, object);
		}
	}

	/**
	 * Adds slots to the container
	 */
	protected abstract void addSlots(Player player, @Nonnull T object);

	/**
	 * Gets the object from {@link #getObject()}, if {@code null} the screen
	 * will be closed, the object from {@link #getObject()} will be returned.
	 *
	 * @return the object from {@link #getObject()} or {@code null}
	 */
	@Nullable
	protected T getObjectOrClose()
	{
		T object = this.getObject();
		if (object == null)
		{
			this.player.closeContainer();
		}
		return object;
	}

	/**
	 * @return the object required for the container to remain open
	 */
	@Nullable
	protected abstract T getObject();
}
