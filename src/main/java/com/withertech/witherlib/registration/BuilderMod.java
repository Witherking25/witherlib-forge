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

import com.withertech.witherlib.data.BuilderDataGenerator;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;

public abstract class BuilderMod
{
    public ModRegistry REGISTRY;
    public ModData MOD;

    private BuilderForgeRegistry<Block> BLOCKS;
    private BuilderForgeRegistry<Item> ITEMS;
    private BuilderForgeRegistry<Fluid> FLUIDS;
    private BuilderForgeRegistry<EntityType<?>> ENTITIES;

    private BuilderEntityAttributeRegistry ENTITY_ATTRIBUTES;
    private BuilderEntityRendererRegistry ENTITY_RENDERERS;
    private BuilderDataGenerator DATA_GENERATORS;
    private BuilderTagRegistry TAGS;
    private BuilderTabRegistry TABS;

    protected BuilderMod(ModData mod)
    {
        MOD = mod;
        REGISTRY = new ModRegistry
                (
                        MOD,
                        this::getBlocks,
                        this::getItems,
                        this::getFluids,
                        this::getEntities,
                        this::getEntityAttributes,
                        this::getEntityRenderers,
                        this::getDataGenerators,
                        this::getTags,
                        this::getTabs
                );
    }

    protected abstract BuilderForgeRegistry<Block> registerBlocks();

    protected BuilderForgeRegistry<Block> getBlocks()
    {
        if (BLOCKS == null)
        {
            BLOCKS = registerBlocks();
        }
        return BLOCKS;
    }

    protected abstract BuilderForgeRegistry<Item> registerItems();

    protected BuilderForgeRegistry<Item> getItems()
    {
        if (ITEMS == null)
        {
            ITEMS = registerItems();
        }
        return ITEMS;
    }

    protected abstract BuilderForgeRegistry<Fluid> registerFluids();

    protected BuilderForgeRegistry<Fluid> getFluids()
    {
        if (FLUIDS == null)
        {
            FLUIDS = registerFluids();
        }
        return FLUIDS;
    }

    protected abstract BuilderForgeRegistry<EntityType<?>> registerEntities();

    protected BuilderForgeRegistry<EntityType<?>> getEntities()
    {
        if (ENTITIES == null)
        {
            ENTITIES = registerEntities();
        }
        return ENTITIES;
    }

    protected abstract BuilderEntityAttributeRegistry registerEntityAttributes();

    protected BuilderEntityAttributeRegistry getEntityAttributes()
    {
        if (ENTITY_ATTRIBUTES == null)
        {
            ENTITY_ATTRIBUTES = registerEntityAttributes();
        }
        return ENTITY_ATTRIBUTES;
    }

    protected abstract BuilderEntityRendererRegistry registerEntityRenderers();

    protected BuilderEntityRendererRegistry getEntityRenderers()
    {
        if (ENTITY_RENDERERS == null)
        {
            ENTITY_RENDERERS = registerEntityRenderers();
        }
        return ENTITY_RENDERERS;
    }

    protected abstract BuilderDataGenerator registerDataGenerators();

    protected BuilderDataGenerator getDataGenerators()
    {
        if (DATA_GENERATORS == null)
        {
            DATA_GENERATORS = registerDataGenerators();
        }
        return DATA_GENERATORS;
    }

    protected abstract BuilderTagRegistry registerTags();

    protected BuilderTagRegistry getTags()
    {
        if (TAGS == null)
        {
            TAGS = registerTags();
        }
        return TAGS;
    }

    protected abstract BuilderTabRegistry registerTabs();

    protected BuilderTabRegistry getTabs()
    {
        if (TABS == null)
        {
            TABS = registerTabs();
        }
        return TABS;
    }
}
