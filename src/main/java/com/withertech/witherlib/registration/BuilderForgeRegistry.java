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
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderForgeRegistry<T extends IForgeRegistryEntry<T>>
{
	protected final ModData                                MOD;
	protected final DeferredRegister<T>                    REGISTRY;
	protected final Map<TypedRegKey<?>, RegistryObject<T>> ENTRIES;

	protected BuilderForgeRegistry(Builder<T> builder)
	{
		MOD      = builder.mod;
		REGISTRY = builder.REGISTRY;
		ENTRIES  = builder.ENTRIES;
	}

	public static <T extends IForgeRegistryEntry<T>> Builder<T> builder(ModData mod, Class<T> type)
	{
		return new Builder<>(mod, type);
	}

	public static <T extends IForgeRegistryEntry<T>> Builder<T> builder(ModData mod, IForgeRegistry<T> type)
	{
		return new Builder<>(mod, type);
	}

	public boolean containsKey(TypedRegKey<?> key)
	{
		return ENTRIES.containsKey(key);
	}

	public void register(IEventBus bus)
	{
		REGISTRY.register(bus);
	}

	public DeferredRegister<T> getREGISTRY()
	{
		return REGISTRY;
	}

	public Map<TypedRegKey<?>, RegistryObject<T>> getENTRIES()
	{
		return ENTRIES;
	}

	@SuppressWarnings("unchecked")
	public <S extends T> RegistryObject<S> get(TypedRegKey<RegistryObject<S>> key)
	{
		return (RegistryObject<S>) ENTRIES.get(key);
	}

	public static class Builder<T extends IForgeRegistryEntry<T>>
	{
		private final ModData                                mod;
		private final DeferredRegister<T>                    REGISTRY;
		private final Map<TypedRegKey<?>, RegistryObject<T>> ENTRIES = new HashMap<>();

		private Builder(ModData mod, Class<T> registry)
		{
			this.mod = mod;
			REGISTRY = DeferredRegister.create(registry, mod.MODID);
		}

		private Builder(ModData mod, IForgeRegistry<T> registry)
		{
			this.mod = mod;
			REGISTRY = DeferredRegister.create(registry, mod.MODID);
		}


		public <S extends T> Builder<T> add(TypedRegKey<RegistryObject<S>> key, Supplier<S> supplier)
		{
			ENTRIES.put(key, REGISTRY.register(key.getId(), supplier));
			return this;
		}

		public BuilderForgeRegistry<T> build()
		{
			return new BuilderForgeRegistry<>(this);
		}
	}
}
