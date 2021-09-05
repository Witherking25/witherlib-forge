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

package com.withertech.witherlib.nbt.wrappers;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyStorageNBTWrapper extends AbstractNBTWrapper<EnergyStorage, CompoundNBT>
{
	public EnergyStorageNBTWrapper(EnergyStorage value)
	{
		super(value);
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		try
		{
			int energy     = get().getEnergyStored();
			int capacity   = get().getMaxEnergyStored();
			int maxReceive = value.getClass().getDeclaredField("maxReceive").getInt(value);
			int maxExtract = value.getClass().getDeclaredField("maxExtract").getInt(value);
			nbt.putInt("energy", energy);
			nbt.putInt("capacity", capacity);
			nbt.putInt("maxReceive", maxReceive);
			nbt.putInt("maxExtract", maxExtract);
		}
		catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		int energy     = nbt.getInt("energy");
		int capacity   = nbt.getInt("capacity");
		int maxReceive = nbt.getInt("maxReceive");
		int maxExtract = nbt.getInt("maxExtract");
		set(new EnergyStorage(capacity, maxReceive, maxExtract, energy));
	}
}
