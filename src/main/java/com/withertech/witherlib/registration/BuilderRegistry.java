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

/**
 * The type Builder registry.
 *
 * @param <T> the type parameter
 */
public class BuilderRegistry<T extends IForgeRegistryEntry<T>>
{
    /**
     * The Modid.
     */
    protected final String MODID;
    /**
     * The Registry.
     */
    protected final DeferredRegister<T> REGISTRY;
    /**
     * The Entries.
     */
    protected final Map<String, RegistryObject<T>> ENTRIES;

    /**
     * Instantiates a new Builder registry.
     *
     * @param builder the builder
     * @param bus     the bus
     */
    protected BuilderRegistry(Builder<T> builder, IEventBus bus)
    {
        MODID = builder.modid;
        REGISTRY = builder.REGISTRY;
        ENTRIES = builder.ENTRIES;
        REGISTRY.register(bus);
    }

    /**
     * Gets modid.
     *
     * @return the modid
     */
    public String getMODID()
    {
        return MODID;
    }

    /**
     * Gets registry.
     *
     * @return the registry
     */
    public DeferredRegister<T> getREGISTRY()
    {
        return REGISTRY;
    }

    /**
     * Gets entries.
     *
     * @return the entries
     */
    public Map<String, RegistryObject<T>> getENTRIES()
    {
        return ENTRIES;
    }

    /**
     * Get registry object.
     *
     * @param key the key
     * @return the registry object
     */
    public RegistryObject<T> get(String key)
    {
        return ENTRIES.get(key);
    }

    /**
     * The type Builder.
     *
     * @param <T> the type parameter
     */
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

        /**
         * Create builder.
         *
         * @param <T>   the type parameter
         * @param modid the modid
         * @param type  the type
         * @return the builder
         */
        public static <T extends IForgeRegistryEntry<T>> Builder<T> create(String modid, Class<T> type)
        {
            return new Builder<>(modid, type);
        }

        /**
         * Add builder.
         *
         * @param name     the name
         * @param supplier the supplier
         * @return the builder
         */
        public Builder<T> add(String name, Supplier<T> supplier)
        {
            ENTRIES.put(name, REGISTRY.register(name, supplier));
            return this;
        }

        /**
         * Build builder registry.
         *
         * @param bus the bus
         * @return the builder registry
         */
        public BuilderRegistry<T> build(IEventBus bus)
        {
            return new BuilderRegistry<>(this, bus);
        }
    }
}
