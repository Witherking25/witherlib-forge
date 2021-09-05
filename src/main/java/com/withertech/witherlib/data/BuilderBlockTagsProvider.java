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

import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.tags.ITag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BuilderBlockTagsProvider extends BlockTagsProvider
{
	public BuilderBlockTagsProvider(DataGenerator gen, String modId, @Nullable ExistingFileHelper existingFileHelper)
	{
		super(gen, modId, existingFileHelper);
	}

	@Override
	protected abstract void addTags();

	@Override
	@Nonnull
	public TagsProvider.Builder<Block> tag(@Nonnull ITag.INamedTag<Block> tag)
	{
		return super.tag(tag);
	}

}
