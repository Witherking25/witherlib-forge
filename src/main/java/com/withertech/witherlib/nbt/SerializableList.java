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
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SerializableList<T extends INBTSerializable<CompoundNBT>> extends ArrayList<T> implements INBTSerializable<ListNBT>
{
    private final Supplier<T> template;

    public SerializableList(Supplier<T> template)
    {
        super();
        this.template = template;
    }

    public SerializableList(int initialCapacity, Supplier<T> template)
    {
        super(initialCapacity);
        this.template = template;
    }

    public SerializableList(Collection<? extends T> c, Supplier<T> template)
    {
        super(c);
        this.template = template;
    }

    @Override
    public ListNBT serializeNBT()
    {
        ListNBT nbt = new ListNBT();
        nbt.addAll(this.stream().map(INBTSerializable::serializeNBT).collect(Collectors.toList()));
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull ListNBT list)
    {
        this.clear();
        list.stream().map(nbt -> (CompoundNBT) nbt).collect(Collectors.toList()).forEach(nbt ->
        {
            T value = template.get();
            value.deserializeNBT(nbt);
            this.add(value);
        });
    }
}
