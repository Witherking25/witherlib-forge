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

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;

public abstract class BuilderRecipeProvider extends RecipeProvider
{
	public BuilderRecipeProvider(DataGenerator generatorIn)
	{
		super(generatorIn);
	}


	public static InventoryChangeTrigger.TriggerInstance has(@Nonnull ItemLike itemProvider)
	{
		return RecipeProvider.has(itemProvider);
	}

	public static InventoryChangeTrigger.TriggerInstance has(@Nonnull Tag<Item> itemTag)
	{
		return RecipeProvider.has(itemTag);
	}
}
