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

import java.lang.reflect.Field;

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
			Field energyField = value.getClass().getDeclaredField("energy");
			Field capacityField = value.getClass().getDeclaredField("capacity");
			Field maxReceiveField = value.getClass().getDeclaredField("maxReceive");
			Field maxExtractField = value.getClass().getDeclaredField("maxExtract");

			energyField.setAccessible(true);
			capacityField.setAccessible(true);
			maxReceiveField.setAccessible(true);
			maxExtractField.setAccessible(true);

			int energy = energyField.getInt(value);
			int capacity = capacityField.getInt(value);
			int maxReceive = maxReceiveField.getInt(value);
			int maxExtract = maxExtractField.getInt(value);

			nbt.putInt("energy", energy);
			nbt.putInt("capacity", capacity);
			nbt.putInt("maxReceive", maxReceive);
			nbt.putInt("maxExtract", maxExtract);
		} catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		try
		{
			Field energyField = value.getClass().getDeclaredField("energy");
			Field capacityField = value.getClass().getDeclaredField("capacity");
			Field maxReceiveField = value.getClass().getDeclaredField("maxReceive");
			Field maxExtractField = value.getClass().getDeclaredField("maxExtract");

			energyField.setAccessible(true);
			capacityField.setAccessible(true);
			maxReceiveField.setAccessible(true);
			maxExtractField.setAccessible(true);

			int energy = nbt.getInt("energy");
			int capacity = nbt.getInt("capacity");
			int maxReceive = nbt.getInt("maxReceive");
			int maxExtract = nbt.getInt("maxExtract");

			energyField.setInt(value, energy);
			capacityField.setInt(value, capacity);
			maxReceiveField.setInt(value, maxReceive);
			maxExtractField.setInt(value, maxExtract);

		} catch (NoSuchFieldException | IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
