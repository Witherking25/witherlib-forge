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

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

import java.util.HashMap;
import java.util.Map;

public class BuilderTagRegistry
{
	private final Map<String, Tag.Named<Block>> BLOCKS;
	private final Map<String, Tag.Named<Item>> ITEMS;
	private final Map<String, Tag.Named<Fluid>> FLUIDS;

	private BuilderTagRegistry(Builder builder)
	{
		BLOCKS = builder.BLOCKS;
		ITEMS = builder.ITEMS;
		FLUIDS = builder.FLUIDS;
	}

	public static Builder builder(ModData mod)
	{
		return new Builder(mod);
	}

	public boolean containsBlock(String key)
	{
		return BLOCKS.containsKey(key);
	}

	public boolean containsItem(String key)
	{
		return ITEMS.containsKey(key);
	}

	public boolean containsFluid(String key)
	{
		return FLUIDS.containsKey(key);
	}

	public Tag.Named<Block> getBlock(String key)
	{
		return BLOCKS.get(key);
	}

	public Tag.Named<Item> getItem(String key)
	{
		return ITEMS.get(key);
	}

	public Tag.Named<Fluid> getFluid(String key)
	{
		return FLUIDS.get(key);
	}

	public static class Builder
	{
		private final String MODID;

		private final Map<String, Tag.Named<Block>> BLOCKS = new HashMap<>();
		private final Map<String, Tag.Named<Item>> ITEMS = new HashMap<>();
		private final Map<String, Tag.Named<Fluid>> FLUIDS = new HashMap<>();

		private Builder(ModData mod)
		{
			MODID = mod.MODID;
		}

		public Builder addBlock(String name)
		{
			BLOCKS.put(name, BlockTags.bind(new ResourceLocation(MODID, name).toString()));
			return this;
		}

		public Builder addItem(String name)
		{
			ITEMS.put(name, ItemTags.bind(new ResourceLocation(MODID, name).toString()));
			return this;
		}

		public Builder addFluid(String name)
		{
			FLUIDS.put(name, FluidTags.bind(new ResourceLocation(MODID, name).toString()));
			return this;
		}

		public BuilderTagRegistry build()
		{
			return new BuilderTagRegistry(this);
		}

	}
}
