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

package com.withertech.witherlib.nbt;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;

public class SerializableEnergyStorage extends EnergyStorage implements INBTSerializable<CompoundNBT>
{
    public SerializableEnergyStorage(int capacity)
    {
        super(capacity);
    }

    public SerializableEnergyStorage(int capacity, int maxTransfer)
    {
        super(capacity, maxTransfer);
    }

    public SerializableEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        super(capacity, maxReceive, maxExtract);
    }

    public SerializableEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("energy", energy);
        nbt.putInt("capacity", capacity);
        nbt.putInt("maxReceive", maxReceive);
        nbt.putInt("maxExtract", maxExtract);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        energy = nbt.getInt("energy");
        capacity = nbt.getInt("capacity");
        maxReceive = nbt.getInt("maxReceive");
        maxExtract = nbt.getInt("maxExtract");
    }
}
