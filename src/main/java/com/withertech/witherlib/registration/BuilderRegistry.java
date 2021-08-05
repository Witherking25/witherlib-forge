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
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderRegistry<T extends IForgeRegistryEntry<T>>
{
    protected final String MODID;
    protected final DeferredRegister<T> REGISTRY;
    protected final Map<String, RegistryObject<T>> ENTRIES;

    protected BuilderRegistry(Builder<T> builder, IEventBus bus)
    {
        MODID = builder.modid;
        REGISTRY = builder.REGISTRY;
        ENTRIES = builder.ENTRIES;
        REGISTRY.register(bus);
    }

    public RegistryObject<T> get(String key)
    {
        return ENTRIES.get(key);
    }

    public static class Builder<T extends IForgeRegistryEntry<T>>
    {
        private final String modid;
        private final DeferredRegister<T> REGISTRY;
        private final Map<String, RegistryObject<T>> ENTRIES = new HashMap<>();

        private Builder(String modid, Class<T> registry)
        {
            this.modid = modid;
            REGISTRY = DeferredRegister.create(registry, modid);
        }

        public static <T extends IForgeRegistryEntry<T>> Builder<T> create(String modid, Class<T> registry)
        {
            return new Builder<>(modid, registry);
        }

        public Builder<T> add(String name, Supplier<T> supplier)
        {
            ENTRIES.put(name, REGISTRY.register(name, supplier));
            return this;
        }

        public BuilderRegistry<T> build(IEventBus bus)
        {
            return new BuilderRegistry<>(this, bus);
        }
    }
}
