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
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MapNBTWrapper<T extends INBTSerializable<INBT>> extends AbstractNBTWrapper<Map<String, T>, CompoundNBT>
{
	private final Supplier<T> factory;

	public MapNBTWrapper(Map<String, T> value, Supplier<T> factory)
	{
		super(value);
		this.factory = factory;
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		value.forEach((key, value) ->
				             nbt.put(key, value.serializeNBT()));
		return nbt;
	}

	@Override
	public void deserializeNBT(@Nonnull CompoundNBT nbt)
	{
		this.value.clear();
		nbt.getAllKeys().stream().collect(Collectors.toMap(Function.identity(), nbt::getCompound)).forEach((key, nbtValue) ->
        {
            T objValue = factory.get();
            objValue.deserializeNBT(nbtValue);
            this.value.put(key, objValue);
        });
	}
}
