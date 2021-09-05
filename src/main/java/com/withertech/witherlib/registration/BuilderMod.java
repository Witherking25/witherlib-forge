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

/**
 * The Abstract class BuilderMod
 * Your Mod class should extend this
 */
public abstract class BuilderMod
{
	/**
	 * The Main Registry
	 */
	public final ModRegistry REGISTRY;
	/**
	 * The ModData for your mod
	 */
	public final ModData     MOD;

	private BuilderCustomRegistryRegistry      CUSTOM_REGISTRIES;
	private BuilderCustomRegistryEntryRegistry CUSTOM_REGISTRY_ENTRIES;

	private BuilderForgeRegistry<Block>             BLOCKS;
	private BuilderForgeRegistry<Item>              ITEMS;
	private BuilderForgeRegistry<TileEntityType<?>> TILES;
	private BuilderForgeRegistry<ContainerType<?>>  CONTAINERS;
	private BuilderForgeRegistry<Fluid>             FLUIDS;
	private BuilderForgeRegistry<EntityType<?>>     ENTITIES;

	private BuilderEntityAttributeRegistry    ENTITY_ATTRIBUTES;
	private BuilderEntityRendererRegistry     ENTITY_RENDERERS;
	private BuilderTileEntityRendererRegistry TILE_RENDERERS;
	private BuilderDataGenerator              DATA_GENERATORS;
	private BuilderTagRegistry                TAGS;
	private BuilderTabRegistry                TABS;
	private BuilderGuiRegistry                GUIS;
	private BuilderNetworkRegistry            NETS;
	private BuilderConfigRegistry             CONFIGS;


	/**
	 * The constructor you should override for your Mod class
	 *
	 * @param mod
	 * 		Your Mod's {@link ModData}
	 *
	 * @see com.withertech.witherlib.WitherLib
	 */
	protected BuilderMod(ModData mod)
	{
		MOD      = mod;
		REGISTRY = new ModRegistry
				(
						MOD,
						this::getCustomRegistries,
						this::getCustomRegistryEntries,
						this::getBlocks,
						this::getItems,
						this::getTiles,
						this::getContainers,
						this::getFluids,
						this::getEntities,
						this::getEntityAttributes,
						this::getEntityRenderers,
						this::getTileRenderers,
						this::getDataGenerators,
						this::getTags,
						this::getTabs,
						this::getGuis,
						this::getNets,
						this::getConfigs
				);
	}

	/**
	 * Register custom registries.
	 * <p>
	 * Override this in your Mod class to register any custom registries you have
	 *
	 * @return the {@link BuilderCustomRegistryRegistry}
	 */
	protected BuilderCustomRegistryRegistry registerCustomRegistries()
	{
		return BuilderCustomRegistryRegistry.builder(MOD).build();
	}

	/**
	 * Gets custom registries.
	 * <p>
	 * Use this method to retrieve an instance of your custom registry
	 * <p>
	 * <code>getCustomRegistries().get(TypedRegKey.registry("id", Type.class))<code/>
	 *
	 * @return the {@link BuilderCustomRegistryRegistry}
	 */
	protected final BuilderCustomRegistryRegistry getCustomRegistries()
	{
		if (CUSTOM_REGISTRIES == null)
		{
			CUSTOM_REGISTRIES = registerCustomRegistries();
		}
		return CUSTOM_REGISTRIES;
	}

	/**
	 * Register custom registry entries
	 * <p>
	 * Override this in your Mod class to register entries for any custom registries you have
	 *
	 * @return the {@link BuilderCustomRegistryEntryRegistry}
	 */
	protected BuilderCustomRegistryEntryRegistry registerCustomRegistryEntries()
	{
		return BuilderCustomRegistryEntryRegistry.builder().build();
	}

	/**
	 * Gets custom registry entries.
	 * <p>
	 * Use this method to retrieve an instance of an entry in your custom registry
	 * <p>
	 * <code>getCustomRegistryEntries().get(TypedRegKey.registry("id", Type.class)).get(TypedRegKey.custom("id",
	 * Type.class))<code/>
	 *
	 * @return the {@link BuilderCustomRegistryEntryRegistry}
	 */
	protected final BuilderCustomRegistryEntryRegistry getCustomRegistryEntries()
	{
		if (CUSTOM_REGISTRY_ENTRIES == null)
		{
			CUSTOM_REGISTRY_ENTRIES = registerCustomRegistryEntries();
		}
		return CUSTOM_REGISTRY_ENTRIES;
	}

	/**
	 * Register blocks builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<Block> registerBlocks()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS).build();
	}

	/**
	 * Gets blocks.
	 *
	 * @return the blocks
	 */
	protected final BuilderForgeRegistry<Block> getBlocks()
	{
		if (BLOCKS == null)
		{
			BLOCKS = registerBlocks();
		}
		return BLOCKS;
	}

	/**
	 * Register items builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<Item> registerItems()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS).build();
	}

	/**
	 * Gets items.
	 *
	 * @return the items
	 */
	protected final BuilderForgeRegistry<Item> getItems()
	{
		if (ITEMS == null)
		{
			ITEMS = registerItems();
		}
		return ITEMS;
	}

	/**
	 * Register tiles builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES).build();
	}

	/**
	 * Gets tiles.
	 *
	 * @return the tiles
	 */
	protected final BuilderForgeRegistry<TileEntityType<?>> getTiles()
	{
		if (TILES == null)
		{
			TILES = registerTiles();
		}
		return TILES;
	}

	/**
	 * Register containers builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<ContainerType<?>> registerContainers()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.CONTAINERS).build();
	}

	/**
	 * Gets containers.
	 *
	 * @return the containers
	 */
	protected final BuilderForgeRegistry<ContainerType<?>> getContainers()
	{
		if (CONTAINERS == null)
		{
			CONTAINERS = registerContainers();
		}
		return CONTAINERS;
	}

	/**
	 * Register fluids builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<Fluid> registerFluids()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.FLUIDS).build();
	}

	/**
	 * Gets fluids.
	 *
	 * @return the fluids
	 */
	protected final BuilderForgeRegistry<Fluid> getFluids()
	{
		if (FLUIDS == null)
		{
			FLUIDS = registerFluids();
		}
		return FLUIDS;
	}

	/**
	 * Register entities builder forge registry.
	 *
	 * @return the builder forge registry
	 */
	protected BuilderForgeRegistry<EntityType<?>> registerEntities()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ENTITIES).build();
	}

	/**
	 * Gets entities.
	 *
	 * @return the entities
	 */
	protected final BuilderForgeRegistry<EntityType<?>> getEntities()
	{
		if (ENTITIES == null)
		{
			ENTITIES = registerEntities();
		}
		return ENTITIES;
	}

	/**
	 * Register entity attributes builder entity attribute registry.
	 *
	 * @return the builder entity attribute registry
	 */
	protected BuilderEntityAttributeRegistry registerEntityAttributes()
	{
		return BuilderEntityAttributeRegistry.builder().build();
	}

	/**
	 * Gets entity attributes.
	 *
	 * @return the entity attributes
	 */
	protected final BuilderEntityAttributeRegistry getEntityAttributes()
	{
		if (ENTITY_ATTRIBUTES == null)
		{
			ENTITY_ATTRIBUTES = registerEntityAttributes();
		}
		return ENTITY_ATTRIBUTES;
	}

	/**
	 * Register entity renderers builder entity renderer registry.
	 *
	 * @return the builder entity renderer registry
	 */
	protected BuilderEntityRendererRegistry registerEntityRenderers()
	{
		return BuilderEntityRendererRegistry.builder().build();
	}

	/**
	 * Gets entity renderers.
	 *
	 * @return the entity renderers
	 */
	protected final BuilderEntityRendererRegistry getEntityRenderers()
	{
		if (ENTITY_RENDERERS == null)
		{
			ENTITY_RENDERERS = registerEntityRenderers();
		}
		return ENTITY_RENDERERS;
	}

	/**
	 * Register tile renderers builder tile entity renderer registry.
	 *
	 * @return the builder tile entity renderer registry
	 */
	protected BuilderTileEntityRendererRegistry registerTileRenderers()
	{
		return BuilderTileEntityRendererRegistry.builder().build();
	}

	/**
	 * Gets tile renderers.
	 *
	 * @return the tile renderers
	 */
	protected final BuilderTileEntityRendererRegistry getTileRenderers()
	{
		if (TILE_RENDERERS == null)
		{
			TILE_RENDERERS = registerTileRenderers();
		}
		return TILE_RENDERERS;
	}

	/**
	 * Register data generators builder data generator.
	 *
	 * @return the builder data generator
	 */
	protected BuilderDataGenerator registerDataGenerators()
	{
		return BuilderDataGenerator.builder(MOD).build();
	}

	/**
	 * Gets data generators.
	 *
	 * @return the data generators
	 */
	protected final BuilderDataGenerator getDataGenerators()
	{
		if (DATA_GENERATORS == null)
		{
			DATA_GENERATORS = registerDataGenerators();
		}
		return DATA_GENERATORS;
	}

	/**
	 * Register tags builder tag registry.
	 *
	 * @return the builder tag registry
	 */
	protected BuilderTagRegistry registerTags()
	{
		return BuilderTagRegistry.builder(MOD).build();
	}

	/**
	 * Gets tags.
	 *
	 * @return the tags
	 */
	protected final BuilderTagRegistry getTags()
	{
		if (TAGS == null)
		{
			TAGS = registerTags();
		}
		return TAGS;
	}

	/**
	 * Register tabs builder tab registry.
	 *
	 * @return the builder tab registry
	 */
	protected BuilderTabRegistry registerTabs()
	{
		return BuilderTabRegistry.builder().build();
	}

	/**
	 * Gets tabs.
	 *
	 * @return the tabs
	 */
	protected final BuilderTabRegistry getTabs()
	{
		if (TABS == null)
		{
			TABS = registerTabs();
		}
		return TABS;
	}

	/**
	 * Register guis builder gui registry.
	 *
	 * @return the builder gui registry
	 */
	protected BuilderGuiRegistry registerGuis()
	{
		return BuilderGuiRegistry.builder().build();
	}

	/**
	 * Gets guis.
	 *
	 * @return the guis
	 */
	protected final BuilderGuiRegistry getGuis()
	{
		if (GUIS == null)
		{
			GUIS = registerGuis();
		}
		return GUIS;
	}

	/**
	 * Register nets builder network registry.
	 *
	 * @return the builder network registry
	 */
	protected BuilderNetworkRegistry registerNets()
	{
		return BuilderNetworkRegistry.builder(MOD).build();
	}

	/**
	 * Gets nets.
	 *
	 * @return the nets
	 */
	protected final BuilderNetworkRegistry getNets()
	{
		if (NETS == null)
		{
			NETS = registerNets();
		}
		return NETS;
	}

	/**
	 * Register configs builder config registry.
	 *
	 * @return the builder config registry
	 */
	protected BuilderConfigRegistry registerConfigs()
	{
		return BuilderConfigRegistry.builder(MOD).build();
	}

	/**
	 * Gets configs.
	 *
	 * @return the configs
	 */
	protected final BuilderConfigRegistry getConfigs()
	{
		if (CONFIGS == null)
		{
			CONFIGS = registerConfigs();
		}
		return CONFIGS;
	}
}
