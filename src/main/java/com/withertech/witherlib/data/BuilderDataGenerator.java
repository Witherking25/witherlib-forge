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

package com.withertech.witherlib.data;


import com.mojang.datafixers.util.Pair;
import com.withertech.witherlib.registration.ModData;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BuilderDataGenerator
{
	private final List<Consumer<BuilderBlockStateProvider>> BLOCK_STATES;
	private final List<Consumer<BuilderItemModelProvider>> ITEM_MODELS;
	private final List<Consumer<BuilderBlockTagsProvider>> BLOCK_TAGS;
	private final List<Consumer<BuilderItemTagsProvider>> ITEM_TAGS;
	private final List<Consumer<BuilderFluidTagsProvider>> FLUID_TAGS;
	private final List<Pair<Consumer<BuilderBlockLootTableProvider>, List<RegistryObject<Block>>>> BLOCK_LOOT_TABLES;
	private final List<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>> CHEST_LOOT_TABLES;
	private final List<Pair<Consumer<BuilderEntityLootTableProvider>, List<RegistryObject<EntityType<?>>>>> ENTITY_LOOT_TABLES;
	private final List<Consumer<Consumer<FinishedRecipe>>> RECIPES;
	private final List<Consumer<BuilderLangProvider>> LANGS;
	private final ModData MOD;

	private BuilderDataGenerator(Builder builder)
	{
		MOD = builder.MOD;

		BLOCK_STATES = builder.BLOCK_STATES;
		ITEM_MODELS = builder.ITEM_MODELS;
		BLOCK_TAGS = builder.BLOCK_TAGS;
		ITEM_TAGS = builder.ITEM_TAGS;
		FLUID_TAGS = builder.FLUID_TAGS;
		BLOCK_LOOT_TABLES = builder.BLOCK_LOOT_TABLES;
		CHEST_LOOT_TABLES = builder.CHEST_LOOT_TABLES;
		ENTITY_LOOT_TABLES = builder.ENTITY_LOOT_TABLES;
		RECIPES = builder.RECIPES;
		LANGS = builder.LANGS;
	}

	public static Builder builder(ModData mod)
	{
		return new Builder(mod);
	}

	public void forEachBlockStates(Consumer<? super Consumer<BuilderBlockStateProvider>> action)
	{
		BLOCK_STATES.forEach(action);
	}

	public boolean isBlockStatesEmpty()
	{
		return BLOCK_STATES.isEmpty();
	}

	public void forEachItemModels(Consumer<? super Consumer<BuilderItemModelProvider>> action)
	{
		ITEM_MODELS.forEach(action);
	}

	public boolean isItemModelsEmpty()
	{
		return ITEM_MODELS.isEmpty();
	}

	public void forEachBlockTags(Consumer<? super Consumer<BuilderBlockTagsProvider>> action)
	{
		BLOCK_TAGS.forEach(action);
	}

	public boolean isBlockTagsEmpty()
	{
		return BLOCK_TAGS.isEmpty();
	}

	public void forEachItemTags(Consumer<? super Consumer<BuilderItemTagsProvider>> action)
	{
		ITEM_TAGS.forEach(action);
	}

	public boolean isItemTagsEmpty()
	{
		return ITEM_TAGS.isEmpty();
	}

	public void forEachFluidTags(Consumer<? super Consumer<BuilderFluidTagsProvider>> action)
	{
		FLUID_TAGS.forEach(action);
	}

	public boolean isFluidTagsEmpty()
	{
		return FLUID_TAGS.isEmpty();
	}

	public void forEachBlockLootTables(Consumer<? super Pair<Consumer<BuilderBlockLootTableProvider>,
			List<RegistryObject<Block>>>> action)
	{
		BLOCK_LOOT_TABLES.forEach(action);
	}

	public boolean isBlockLootTablesEmpty()
	{
		return BLOCK_LOOT_TABLES.isEmpty();
	}

	public void forEachChestLootTables(Consumer<? super Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>> action)
	{
		CHEST_LOOT_TABLES.forEach(action);
	}

	public boolean isChestLootTablesEmpty()
	{
		return CHEST_LOOT_TABLES.isEmpty();
	}

	public void forEachEntityLootTables(Consumer<? super Pair<Consumer<BuilderEntityLootTableProvider>,
			List<RegistryObject<EntityType<?>>>>> action)
	{
		ENTITY_LOOT_TABLES.forEach(action);
	}

	public boolean isEntityLootTablesEmpty()
	{
		return ENTITY_LOOT_TABLES.isEmpty();
	}

	public void forEachRecipes(Consumer<? super Consumer<Consumer<FinishedRecipe>>> action)
	{
		RECIPES.forEach(action);
	}

	public boolean isRecipesEmpty()
	{
		return RECIPES.isEmpty();
	}

	public void forEachLangs(Consumer<? super Consumer<BuilderLangProvider>> action)
	{
		LANGS.forEach(action);
	}

	public boolean isLangsEmpty()
	{
		return LANGS.isEmpty();
	}

	public static class Builder
	{
		private final ModData MOD;

		private final List<Consumer<BuilderBlockStateProvider>> BLOCK_STATES = new ArrayList<>();
		private final List<Consumer<BuilderItemModelProvider>> ITEM_MODELS = new ArrayList<>();
		private final List<Consumer<BuilderBlockTagsProvider>> BLOCK_TAGS = new ArrayList<>();
		private final List<Consumer<BuilderItemTagsProvider>> ITEM_TAGS = new ArrayList<>();
		private final List<Consumer<BuilderFluidTagsProvider>> FLUID_TAGS = new ArrayList<>();
		private final List<Pair<Consumer<BuilderBlockLootTableProvider>, List<RegistryObject<Block>>>> BLOCK_LOOT_TABLES = new ArrayList<>();
		private final List<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>> CHEST_LOOT_TABLES = new ArrayList<>();
		private final List<Pair<Consumer<BuilderEntityLootTableProvider>, List<RegistryObject<EntityType<?>>>>> ENTITY_LOOT_TABLES = new ArrayList<>();
		private final List<Consumer<Consumer<FinishedRecipe>>> RECIPES = new ArrayList<>();
		private final List<Consumer<BuilderLangProvider>> LANGS = new ArrayList<>();

		private Builder(ModData mod)
		{
			MOD = mod;
		}

		public Builder addBlockState(Consumer<BuilderBlockStateProvider> generator)
		{
			BLOCK_STATES.add(generator);
			return this;
		}

		public Builder addItemModel(Consumer<BuilderItemModelProvider> generator)
		{
			ITEM_MODELS.add(generator);
			return this;
		}

		public Builder addBlockTag(Consumer<BuilderBlockTagsProvider> generator)
		{
			BLOCK_TAGS.add(generator);
			return this;
		}

		public Builder addItemTag(Consumer<BuilderItemTagsProvider> generator)
		{
			ITEM_TAGS.add(generator);
			return this;
		}

		public Builder addFluidTag(Consumer<BuilderFluidTagsProvider> generator)
		{
			FLUID_TAGS.add(generator);
			return this;
		}

		public Builder addBlockLootTable(
				Consumer<BuilderBlockLootTableProvider> generator,
				List<RegistryObject<Block>> knownBlocks
		)
		{
			BLOCK_LOOT_TABLES.add(Pair.of(generator, knownBlocks));
			return this;
		}

		public Builder addChestLootTable(Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> generator)
		{
			CHEST_LOOT_TABLES.add(generator);
			return this;
		}

		public Builder addEntityLootTable(
				Consumer<BuilderEntityLootTableProvider> generator,
				List<RegistryObject<EntityType<?>>> knownBlocks
		)
		{
			ENTITY_LOOT_TABLES.add(Pair.of(generator, knownBlocks));
			return this;
		}

		public Builder addRecipe(Consumer<Consumer<FinishedRecipe>> generator)
		{
			RECIPES.add(generator);
			return this;
		}

		public Builder addLang(Consumer<BuilderLangProvider> generator)
		{
			LANGS.add(generator);
			return this;
		}

		public BuilderDataGenerator build()
		{
			return new BuilderDataGenerator(this);
		}

	}
}
