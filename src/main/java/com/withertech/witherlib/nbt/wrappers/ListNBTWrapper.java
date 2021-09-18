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

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ListNBTWrapper<T extends INBTSerializable<CompoundTag>> extends AbstractNBTWrapper<List<T>, CompoundTag>
{
	private final Supplier<T> factory;

	public ListNBTWrapper(List<T> value, Supplier<T> factory)
	{
		super(value);
		this.factory = factory;
	}

	@Override
	public CompoundTag serializeNBT()
	{
		CompoundTag nbt = new CompoundTag();
		ListTag list = new ListTag();
		list.addAll(value.stream().map(INBTSerializable::serializeNBT).collect(Collectors.toList()));
		nbt.put("values", list);
		return nbt;
	}

	@Override
	public void deserializeNBT(@Nonnull CompoundTag list)
	{
		value.clear();
		new ArrayList<>(list.getList("values", Constants.NBT.TAG_COMPOUND)).forEach(nbt ->
		{
			T value = factory.get();
			value.deserializeNBT((CompoundTag) nbt);
			this.value.add(value);
		});
	}
}
