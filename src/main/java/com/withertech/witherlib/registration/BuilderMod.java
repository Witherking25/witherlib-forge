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
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public abstract class BuilderMod
{
    public final ModRegistry REGISTRY;
    public final ModData MOD;

    private BuilderForgeRegistry<Block> BLOCKS;
    private BuilderForgeRegistry<Item> ITEMS;
    private BuilderForgeRegistry<TileEntityType<?>> TILES;
    private BuilderForgeRegistry<ContainerType<?>> CONTAINERS;
    private BuilderForgeRegistry<Fluid> FLUIDS;
    private BuilderForgeRegistry<EntityType<?>> ENTITIES;

    private BuilderEntityAttributeRegistry ENTITY_ATTRIBUTES;
    private BuilderEntityRendererRegistry ENTITY_RENDERERS;
    private BuilderDataGenerator DATA_GENERATORS;
    private BuilderTagRegistry TAGS;
    private BuilderTabRegistry TABS;

    private BuilderGuiTileRegistry GUIS;

    protected BuilderMod(ModData mod)
    {
        MOD = mod;
        REGISTRY = new ModRegistry
                (
                        MOD,
                        this::getBlocks,
                        this::getItems,
                        this::getTiles,
                        this::getContainers,
                        this::getFluids,
                        this::getEntities,
                        this::getEntityAttributes,
                        this::getEntityRenderers,
                        this::getDataGenerators,
                        this::getTags,
                        this::getTabs,
                        this::getGuis
                );
    }

    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS).build();
    }

    protected final BuilderForgeRegistry<Block> getBlocks()
    {
        if (BLOCKS == null)
        {
            BLOCKS = registerBlocks();
        }
        return BLOCKS;
    }

    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS).build();
    }

    protected final BuilderForgeRegistry<Item> getItems()
    {
        if (ITEMS == null)
        {
            ITEMS = registerItems();
        }
        return ITEMS;
    }

    protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES).build();
    }

    protected final BuilderForgeRegistry<TileEntityType<?>> getTiles()
    {
        if (TILES == null)
        {
            TILES = registerTiles();
        }
        return TILES;
    }

    protected BuilderForgeRegistry<ContainerType<?>> registerContainers()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.CONTAINERS).build();
    }

    protected final BuilderForgeRegistry<ContainerType<?>> getContainers()
    {
        if (CONTAINERS == null)
        {
            CONTAINERS = registerContainers();
        }
        return CONTAINERS;
    }

    protected BuilderForgeRegistry<Fluid> registerFluids()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.FLUIDS).build();
    }

    protected final BuilderForgeRegistry<Fluid> getFluids()
    {
        if (FLUIDS == null)
        {
            FLUIDS = registerFluids();
        }
        return FLUIDS;
    }

    protected BuilderForgeRegistry<EntityType<?>> registerEntities()
    {
        return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ENTITIES).build();
    }

    protected final BuilderForgeRegistry<EntityType<?>> getEntities()
    {
        if (ENTITIES == null)
        {
            ENTITIES = registerEntities();
        }
        return ENTITIES;
    }

    protected BuilderEntityAttributeRegistry registerEntityAttributes()
    {
        return BuilderEntityAttributeRegistry.builder().build();
    }

    protected final BuilderEntityAttributeRegistry getEntityAttributes()
    {
        if (ENTITY_ATTRIBUTES == null)
        {
            ENTITY_ATTRIBUTES = registerEntityAttributes();
        }
        return ENTITY_ATTRIBUTES;
    }

    protected BuilderEntityRendererRegistry registerEntityRenderers()
    {
        return BuilderEntityRendererRegistry.builder().build();
    }

    protected final BuilderEntityRendererRegistry getEntityRenderers()
    {
        if (ENTITY_RENDERERS == null)
        {
            ENTITY_RENDERERS = registerEntityRenderers();
        }
        return ENTITY_RENDERERS;
    }

    protected BuilderDataGenerator registerDataGenerators()
    {
        return BuilderDataGenerator.builder(MOD).build();
    }

    protected final BuilderDataGenerator getDataGenerators()
    {
        if (DATA_GENERATORS == null)
        {
            DATA_GENERATORS = registerDataGenerators();
        }
        return DATA_GENERATORS;
    }

    protected BuilderTagRegistry registerTags()
    {
        return BuilderTagRegistry.builder(MOD).build();
    }

    protected final BuilderTagRegistry getTags()
    {
        if (TAGS == null)
        {
            TAGS = registerTags();
        }
        return TAGS;
    }

    protected BuilderTabRegistry registerTabs()
    {
        return BuilderTabRegistry.builder().build();
    }

    protected final BuilderTabRegistry getTabs()
    {
        if (TABS == null)
        {
            TABS = registerTabs();
        }
        return TABS;
    }

    protected BuilderGuiTileRegistry registerGuis()
    {
        return BuilderGuiTileRegistry.builder().build();
    }

    protected final BuilderGuiTileRegistry getGuis()
    {
        if (GUIS == null)
        {
            GUIS = registerGuis();
        }
        return GUIS;
    }
}
