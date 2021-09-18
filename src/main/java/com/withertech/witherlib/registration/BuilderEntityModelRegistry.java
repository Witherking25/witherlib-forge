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

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderEntityModelRegistry
{
	private final Map<String, Model> models;

	public Map<String, Model> getModels()
	{
		return models;
	}

	private BuilderEntityModelRegistry(Builder builder)
	{
		models = builder.models;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public boolean containsKey(String key)
	{
		return models.containsKey(key);
	}

	public static class Builder
	{
		private final Map<String, Model> models = new HashMap<>();

		private Builder() {}

		public Model.ModelBuilder model(String name)
		{
			return new Model.ModelBuilder(name, this);
		}

		public BuilderEntityModelRegistry build()
		{
			return new BuilderEntityModelRegistry(this);
		}
	}

	public static class Model
	{
		private final Map<String, Pair<ModelLayerLocation, Supplier<LayerDefinition>>> layers;

		private Model(ModelBuilder builder)
		{
			layers = builder.layers;
		}

		public Map<String, Pair<ModelLayerLocation, Supplier<LayerDefinition>>> getLayers()
		{
			return layers;
		}

		public static class ModelBuilder
		{
			private final String name;
			private final Builder builder;

			private final Map<String, Pair<ModelLayerLocation, Supplier<LayerDefinition>>> layers = new HashMap<>();

			private ModelBuilder(String name, Builder builder)
			{
				this.name = name;
				this.builder = builder;
			}

			public ModelBuilder layer(String name, ModelLayerLocation loc, Supplier<LayerDefinition> layer)
			{
				layers.put(name, Pair.of(loc, layer));
				return this;
			}

			public Builder build()
			{
				builder.models.put(name, new Model(this));
				return builder;
			}
		}
	}
}
