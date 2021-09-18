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

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class BuilderEntityRendererRegistry
{
	private final Map<String, EntityRendererProvider<?>> ENTITIES;

	private BuilderEntityRendererRegistry(Builder builder)
	{
		ENTITIES = builder.ENTITIES;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public EntityRendererProvider<?> get(String key)
	{
		return ENTITIES.get(key);
	}

	public boolean containsKey(String key)
	{
		return ENTITIES.containsKey(key);
	}

	public static class Builder
	{
		private final Map<String, EntityRendererProvider<?>> ENTITIES = new HashMap<>();

		private Builder()
		{

		}

		public <T extends Entity> Builder add(String name, EntityRendererProvider<T> renderer)
		{
			ENTITIES.put(name, renderer);
			return this;
		}

		public BuilderEntityRendererRegistry build()
		{
			return new BuilderEntityRendererRegistry(this);
		}

	}
}
