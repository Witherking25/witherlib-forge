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
import net.minecraft.data.DataGenerator;
import net.minecraft.util.IItemProvider;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Objects;

public abstract class BuilderItemModelProvider extends ItemModelProvider
{

    public BuilderItemModelProvider(DataGenerator generator, String modid, ExistingFileHelper existingFileHelper)
    {
        super(generator, modid, existingFileHelper);
    }

    public void blockBuilder(@Nonnull Block block)
    {
        String name = Objects.requireNonNull(block.getRegistryName()).getPath();
        withExistingParent(name, modLoc("block/" + name));
    }

    public void builder(@Nonnull IItemProvider item, ModelFile parent)
    {
        String name = Objects.requireNonNull(item.asItem().getRegistryName()).getPath();
        builder(item, parent, "item/" + name);
    }

    public void builder(@Nonnull IItemProvider item, ModelFile parent, String texture)
    {
        getBuilder(Objects.requireNonNull(item.asItem().getRegistryName()).getPath())
                .parent(parent)
                .texture("layer0", modLoc(texture));
    }

    public ModelFile getGenerated()
    {
        return getExistingFile(mcLoc("item/generated"));
    }

    public ModelFile getHandheld()
    {
        return getExistingFile(mcLoc("item/handheld"));
    }
}
