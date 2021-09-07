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

import com.withertech.witherlib.config.BaseConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BuilderConfigRegistry
{
	private final Map<TypedRegKey<? extends BaseConfig>, Function<ForgeConfigSpec.Builder, BaseConfig>> FACTORIES;
	private final Map<TypedRegKey<? extends BaseConfig>, Pair<BaseConfig, ForgeConfigSpec>> CONFIGS =
			new HashMap<>();

	private final ModData MOD;

	public BuilderConfigRegistry(Builder builder)
	{
		FACTORIES = builder.CONFIGS;
		MOD = builder.MOD;
	}

	public static Builder builder(ModData mod)
	{
		return new Builder(mod);
	}

	public boolean containsFactory(TypedRegKey<?> key)
	{
		return FACTORIES.containsKey(key);
	}

	public boolean containsConfig(TypedRegKey<?> key)
	{
		return CONFIGS.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseConfig> Function<ForgeConfigSpec.Builder, T> getFactory(TypedRegKey<T> key)
	{
		return (Function<ForgeConfigSpec.Builder, T>) FACTORIES.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseConfig> T getConfig(TypedRegKey<T> key)
	{
		return (T) CONFIGS.get(key).getLeft();
	}

	public <T extends BaseConfig> ForgeConfigSpec getSpec(TypedRegKey<T> key)
	{
		return CONFIGS.get(key).getRight();
	}

	public Map<TypedRegKey<? extends BaseConfig>, Function<ForgeConfigSpec.Builder, BaseConfig>> getFACTORIES()
	{
		return FACTORIES;
	}

	public Map<TypedRegKey<? extends BaseConfig>, Pair<BaseConfig, ForgeConfigSpec>> getCONFIGS()
	{
		return CONFIGS;
	}

	public void register()
	{
		FACTORIES.forEach((typedRegKey, builderBaseConfigFunction) ->
		{
			Pair<BaseConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(
					builderBaseConfigFunction);
			CONFIGS.put(typedRegKey, pair);
			ModLoadingContext.get().registerConfig(
					pair.getLeft().getType(),
					pair.getRight(),
					String.format("%s-%s.toml",
							MOD.MODID,
							typedRegKey.getId()
					)
			);
		});
	}

	public static class Builder
	{
		private final Map<TypedRegKey<? extends BaseConfig>, Function<ForgeConfigSpec.Builder, BaseConfig>> CONFIGS =
				new HashMap<>();
		private final ModData MOD;

		private Builder(ModData mod)
		{
			this.MOD = mod;
		}

		@SuppressWarnings("unchecked")
		public <T extends BaseConfig> Builder add(TypedRegKey<T> key, Function<ForgeConfigSpec.Builder, T> value)
		{
			CONFIGS.put(key, (Function<ForgeConfigSpec.Builder, BaseConfig>) value);
			return this;
		}

		public BuilderConfigRegistry build()
		{
			return new BuilderConfigRegistry(this);
		}
	}
}
