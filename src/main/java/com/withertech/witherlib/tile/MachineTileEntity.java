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

package com.withertech.witherlib.tile;

import com.withertech.witherlib.nbt.SyncVariable;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class MachineTileEntity<T extends MachineTileEntity<T>> extends BaseTileEntity<T>
		implements ITickableTileEntity
{
	@SyncVariable(name = "progress")
	protected int progress = 0;

	public MachineTileEntity(TileEntityType<T> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	public abstract boolean canMachineRun();

	public abstract void onStart();

	public abstract void onTick(int progress);

	public abstract void onFinish();

	public abstract int getMaxProgress();

	public int getProgress()
	{
		return progress;
	}

	@Override
	protected CompoundNBT writeData()
	{
		return SyncVariable.Helper.writeSyncVars(
				MachineTileEntity.class,
				this,
				super.writeData(),
				SyncVariable.Type.WRITE
		);
	}

	@Override
	protected CompoundNBT writeClientData()
	{
		return SyncVariable.Helper.writeSyncVars(
				MachineTileEntity.class,
				this,
				super.writeClientData(),
				SyncVariable.Type.PACKET
		);
	}

	@Override
	public void readData(CompoundNBT tag)
	{
		super.readData(tag);
		SyncVariable.Helper.readSyncVars(MachineTileEntity.class, this, tag);
	}

	@Override
	public void tick()
	{
		if (level != null && !level.isClientSide())
		{
			boolean needUpdate = false;
			if (canMachineRun())
			{
				if (progress == 0)
				{
					onStart();
					progress++;
					needUpdate = true;
				} else if (progress < getMaxProgress())
				{
					onTick(progress);
					progress++;
					if (progress % 2 == 0)
					{
						needUpdate = true;
					}
				} else if (progress >= getMaxProgress())
				{
					onFinish();
					progress = 0;
					needUpdate = true;
				}
			} else
			{
				progress = 0;
				needUpdate = true;
			}
			if (needUpdate)
			{
				dataChanged();
			}
		}
	}
}
