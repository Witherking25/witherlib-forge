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

import com.withertech.witherlib.tile.BaseTileEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import java.util.HashMap;
import java.util.Map;

public class BuilderTileEntityRendererRegistry
{
	private final Map<String, BlockEntityRendererProvider<?>> TILES;

	private BuilderTileEntityRendererRegistry(Builder builder)
	{
		TILES = builder.TILES;
	}

	public static Builder builder()
	{
		return new Builder();
	}

	public BlockEntityRendererProvider<?> get(String key)
	{
		return TILES.get(key);
	}

	public boolean containsKey(String key)
	{
		return TILES.containsKey(key);
	}

	public static class Builder
	{
		private final Map<String, BlockEntityRendererProvider<?>> TILES = new HashMap<>();

		private Builder()
		{
		}

		public <T extends BaseTileEntity<T>> Builder add(String name, BlockEntityRendererProvider<T> renderer)
		{
			TILES.put(name, renderer);
			return this;
		}

		public BuilderTileEntityRendererRegistry build()
		{
			return new BuilderTileEntityRendererRegistry(this);
		}

	}
}
