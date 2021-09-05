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

import com.withertech.witherlib.util.ClientUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class TileEntityBaseScreen<T extends TileEntity> extends ObjectBaseScreen<T>
{

	protected final BlockPos tilePos;

	protected TileEntityBaseScreen(ITextComponent title, BlockPos tilePos)
	{
		super(title);
		this.tilePos = tilePos;
	}

	protected TileEntityBaseScreen(ITextComponent title)
	{
		this(title, null);
	}

	@Override
	protected T getObject()
	{
		return this.getTileEntity();
	}

	@SuppressWarnings("unchecked")
	protected T getTileEntity()
	{
		TileEntity tile = ClientUtils.getWorld().getBlockEntity(this.tilePos);

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
