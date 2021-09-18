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

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nonnull;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class TileEntityBaseContainer<X extends TileEntityBaseContainer<X, T>, T extends BlockEntity>
		extends ObjectBaseContainer<T>
{

	protected final Level tileWorld;
	protected final BlockPos tilePos;

	public TileEntityBaseContainer(
			MenuType<X> type,
			int id,
			Player player,
			Level tileWorld,
			BlockPos tilePos
	)
	{
		super(type, id, player);
		this.tileWorld = tileWorld;
		this.tilePos = tilePos;
	}

	public TileEntityBaseContainer(MenuType<X> type, int id, Player player, BlockPos tilePos)
	{
		this(type, id, player, player.level, tilePos);
	}

	public BlockPos getTilePos()
	{
		return tilePos;
	}

	@Override
	public boolean stillValid(@Nonnull Player playerIn)
	{
		return super.stillValid(playerIn);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T getObject()
	{
		BlockEntity tile = this.tileWorld.getBlockEntity(this.tilePos);

		if (tile == null)
		{
			return null;
		}

		try
		{
			return (T) tile;
		} catch (ClassCastException ignore)
		{
		}
		return null;
	}
}
