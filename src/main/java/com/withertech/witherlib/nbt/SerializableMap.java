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

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class SerializableMap<T extends INBTSerializable<CompoundNBT>> extends HashMap<String, T> implements INBTSerializable<CompoundNBT>
{
    private final Supplier<T> template;

    public SerializableMap(Supplier<T> template)
    {
        super();
        this.template = template;
    }

    public SerializableMap(int initialCapacity, Supplier<T> template)
    {
        super(initialCapacity);
        this.template = template;
    }

    public SerializableMap(int initialCapacity, float loadFactor, Supplier<T> template)
    {
        super(initialCapacity, loadFactor);
        this.template = template;
    }

    public SerializableMap(Map<? extends String, ? extends T> m, Supplier<T> template)
    {
        super(m);
        this.template = template;
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.forEach((key, value) ->
                nbt.put(key, value.serializeNBT()));
        return nbt;
    }

    @Override
    public void deserializeNBT(@Nonnull CompoundNBT nbt)
    {
        nbt.getAllKeys().stream().collect(Collectors.toMap(Function.identity(), nbt::getCompound)).forEach((key, nbtValue) ->
        {
            T objValue = template.get();
            objValue.deserializeNBT(nbtValue);
            this.put(key, objValue);
        });
    }
}
