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

package com.withertech.witherlib.registration;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;

public class BuilderCustomRegistryEntryRegistry
{
    private final Map<TypedRegKey<? extends IForgeRegistryEntry<?>>, BuilderForgeRegistry<?>> REGISTRIES;

    private BuilderCustomRegistryEntryRegistry(Builder builder)
    {
        REGISTRIES = builder.REGISTRIES;
    }

    public static Builder builder()
    {
        return new Builder();
    }

    public boolean containsKey(TypedRegKey<?> key)
    {
        return REGISTRIES.containsKey(key);
    }

    @SuppressWarnings("unchecked")
    public <T extends IForgeRegistryEntry<T>> BuilderForgeRegistry<T> get(TypedRegKey<T> type)
    {
        return (BuilderForgeRegistry<T>) REGISTRIES.get(type);
    }

    public void register(IEventBus bus)
    {
        REGISTRIES.forEach((typedRegKey, builderForgeRegistry) ->
                builderForgeRegistry.register(bus));
    }

    public static class Builder
    {
        private final Map<TypedRegKey<? extends IForgeRegistryEntry<?>>, BuilderForgeRegistry<?>> REGISTRIES = new HashMap<>();

        private Builder()
        {
        }

        public <T extends IForgeRegistryEntry<T>> Builder add(TypedRegKey<T> type, BuilderForgeRegistry<T> registry)
        {
            REGISTRIES.put(type, registry);
            return this;
        }

        public BuilderCustomRegistryEntryRegistry build()
        {
            return new BuilderCustomRegistryEntryRegistry(this);
        }
    }
}
