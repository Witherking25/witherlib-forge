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

import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.HashMap;
import java.util.Map;

public class BuilderCustomRegistryRegistry
{
	private final Map<TypedRegKey<? extends IForgeRegistryEntry<?>>, RegistryBuilder<?>> BUILDERS;
	private final Map<TypedRegKey<? extends IForgeRegistryEntry<?>>, ForgeRegistry<?>> REGISTRIES = new HashMap<>();

	private BuilderCustomRegistryRegistry(Builder builder)
	{
		BUILDERS = builder.BUILDERS;
	}

	public static Builder builder(ModData mod)
	{
		return new Builder(mod);
	}

	public static <T extends IForgeRegistryEntry<T>> RegistryBuilder<T> registry(Class<T> type)
	{
		return new RegistryBuilder<T>().setType(type);
	}

	public boolean containsRegistry(TypedRegKey<?> key)
	{
		return REGISTRIES.containsKey(key);
	}

	public boolean containsBuilder(TypedRegKey<?> key)
	{
		return BUILDERS.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends IForgeRegistryEntry<T>> ForgeRegistry<T> get(TypedRegKey<T> key)
	{
		return (ForgeRegistry<T>) REGISTRIES.get(key);
	}

	public void register()
	{
		BUILDERS.forEach((typedRegKey, registryBuilder) ->
				REGISTRIES.put(typedRegKey, (ForgeRegistry<?>) registryBuilder.create()));
	}

	public static class Builder
	{
		private final Map<TypedRegKey<? extends IForgeRegistryEntry<?>>, RegistryBuilder<?>> BUILDERS =
				new HashMap<>();
		private final ModData MOD;

		private Builder(ModData mod)
		{
			MOD = mod;
		}

		public <T extends IForgeRegistryEntry<T>> Builder add(TypedRegKey<T> key, RegistryBuilder<T> builder)
		{
			BUILDERS.put(key, builder.setName(MOD.modLocation(key.getId())));
			return this;
		}

		public BuilderCustomRegistryRegistry build()
		{
			return new BuilderCustomRegistryRegistry(this);
		}
	}
}
