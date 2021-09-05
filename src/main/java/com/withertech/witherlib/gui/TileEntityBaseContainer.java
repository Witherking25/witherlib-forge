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

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class TileEntityBaseContainer<X extends TileEntityBaseContainer<X, T>, T extends TileEntity>
		extends ObjectBaseContainer<T>
{

	protected final World tileWorld;
	protected final BlockPos tilePos;

	public TileEntityBaseContainer(
			ContainerType<X> type,
			int id,
			PlayerEntity player,
			World tileWorld,
			BlockPos tilePos
	)
	{
		super(type, id, player);
		this.tileWorld = tileWorld;
		this.tilePos   = tilePos;
	}

	public TileEntityBaseContainer(ContainerType<X> type, int id, PlayerEntity player, BlockPos tilePos)
	{
		this(type, id, player, player.level, tilePos);
	}

	public BlockPos getTilePos()
	{
		return tilePos;
	}

	@Override
	public boolean stillValid(PlayerEntity playerIn)
	{
		return super.stillValid(playerIn);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected T getObject()
	{
		TileEntity tile = this.tileWorld.getBlockEntity(this.tilePos);

		if (tile == null)
		{
			return null;
		}

		try
		{
			return (T) tile;
		}
		catch (ClassCastException ignore)
		{
		}
		return null;
	}
}
