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

import com.mojang.datafixers.util.Pair;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.config.BaseConfig;
import com.withertech.witherlib.data.*;
import com.withertech.witherlib.network.PacketChannel;
import com.withertech.witherlib.render.ITileEntityRendererFactory;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.tags.ITag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.registries.ForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModRegistry
{
	public final ModData MOD;

	public final Supplier<BuilderCustomRegistryRegistry> REGISTRIES;

	public final Supplier<BuilderCustomRegistryEntryRegistry> CUSTOM_REGISTRY_ENTRIES;

	public final Supplier<BuilderForgeRegistry<Block>> BLOCKS;

	public final Supplier<BuilderForgeRegistry<Item>> ITEMS;

	public final Supplier<BuilderForgeRegistry<TileEntityType<?>>> TILES;

	public final Supplier<BuilderForgeRegistry<ContainerType<?>>> CONTAINERS;

	public final Supplier<BuilderForgeRegistry<Fluid>> FLUIDS;

	public final Supplier<BuilderForgeRegistry<EntityType<?>>> ENTITIES;

	public final Supplier<BuilderEntityAttributeRegistry> ENTITY_ATTRIBUTES;

	public final Supplier<BuilderEntityRendererRegistry> ENTITY_RENDERERS;

	public final Supplier<BuilderTileEntityRendererRegistry> TILE_RENDERERS;

	public final Supplier<BuilderDataGenerator> DATA_GENERATOR;

	public final Supplier<BuilderTagRegistry> TAGS;

	public final Supplier<BuilderTabRegistry> TABS;

	public final Supplier<BuilderGuiRegistry> GUIS;

	public final Supplier<BuilderNetworkRegistry> NETS;

	public final Supplier<BuilderConfigRegistry> CONFIGS;

	public ModRegistry(
			ModData mod,
			Supplier<BuilderCustomRegistryRegistry> registries,
			Supplier<BuilderCustomRegistryEntryRegistry> custom_registry_entries,
			Supplier<BuilderForgeRegistry<Block>> blocks,
			Supplier<BuilderForgeRegistry<Item>> items,
			Supplier<BuilderForgeRegistry<TileEntityType<?>>> tiles,
			Supplier<BuilderForgeRegistry<ContainerType<?>>> containers,
			Supplier<BuilderForgeRegistry<Fluid>> fluids,
			Supplier<BuilderForgeRegistry<EntityType<?>>> entities,
			Supplier<BuilderEntityAttributeRegistry> entity_attributes,
			Supplier<BuilderEntityRendererRegistry> entity_renderers,
			Supplier<BuilderTileEntityRendererRegistry> tile_renderers,
			Supplier<BuilderDataGenerator> data_generator,
			Supplier<BuilderTagRegistry> tags,
			Supplier<BuilderTabRegistry> tabs,
			Supplier<BuilderGuiRegistry> guis,
			Supplier<BuilderNetworkRegistry> nets,
			Supplier<BuilderConfigRegistry> configs
	)
	{
		MOD                     = mod;
		REGISTRIES              = registries;
		CUSTOM_REGISTRY_ENTRIES = custom_registry_entries;
		BLOCKS                  = blocks;
		ITEMS                   = items;
		TILES                   = tiles;
		CONTAINERS              = containers;
		FLUIDS                  = fluids;
		ENTITIES                = entities;
		ENTITY_ATTRIBUTES       = entity_attributes;
		ENTITY_RENDERERS        = entity_renderers;
		TILE_RENDERERS          = tile_renderers;
		DATA_GENERATOR          = data_generator;
		TAGS                    = tags;
		TABS                    = tabs;
		GUIS                    = guis;
		NETS                    = nets;
		CONFIGS                 = configs;

		MOD.MOD_EVENT_BUS.addListener(this::onGatherData);
		MOD.MOD_EVENT_BUS.addListener(this::onClientSetup);
		MOD.MOD_EVENT_BUS.addListener(this::onEntityAttributeCreation);
		MOD.MOD_EVENT_BUS.addListener(this::onNewRegistry);
		register();
	}

	private void register()
	{
		WitherLib.LOGGER.info("Registering Blocks for " + MOD.MODID);
		BLOCKS.get().register(MOD.MOD_EVENT_BUS);
		WitherLib.LOGGER.info("Registering Items for " + MOD.MODID);
		ITEMS.get().register(MOD.MOD_EVENT_BUS);
		WitherLib.LOGGER.info("Registering Tiles for " + MOD.MODID);
		TILES.get().register(MOD.MOD_EVENT_BUS);
		WitherLib.LOGGER.info("Registering Containers for " + MOD.MODID);
		CONTAINERS.get().register(MOD.MOD_EVENT_BUS);
		WitherLib.LOGGER.info("Registering Fluids for " + MOD.MODID);
		FLUIDS.get().register(MOD.MOD_EVENT_BUS);
		WitherLib.LOGGER.info("Registering Entities for " + MOD.MODID);
		ENTITIES.get().register(MOD.MOD_EVENT_BUS);

		WitherLib.LOGGER.info("Registering Custom Registry Entries for " + MOD.MODID);
		CUSTOM_REGISTRY_ENTRIES.get().register(MOD.MOD_EVENT_BUS);

		WitherLib.LOGGER.info("Registering Networks for " + MOD.MODID);
		NETS.get().register();
		WitherLib.LOGGER.info("Registering Configs for " + MOD.MODID);
		CONFIGS.get().register();
	}

	public <T extends Block> RegistryObject<T> getBlock(TypedRegKey<RegistryObject<T>> key)
	{
		return BLOCKS.get().get(key);
	}

	public ITag.INamedTag<Block> getBlockTag(String key)
	{
		return TAGS.get().getBlock(key);
	}

	public <T extends Item> RegistryObject<T> getItem(TypedRegKey<RegistryObject<T>> key)
	{
		return ITEMS.get().get(key);
	}

	public ITag.INamedTag<Item> getItemTag(String key)
	{
		return TAGS.get().getItem(key);
	}

	public <T extends TileEntity> RegistryObject<TileEntityType<T>> getTile(TypedRegKey<RegistryObject<TileEntityType<T>>> key)
	{
		return TILES.get().get(key);
	}

	public <T extends Container> RegistryObject<ContainerType<T>> getContainer(TypedRegKey<RegistryObject<ContainerType<T>>> key)
	{
		return CONTAINERS.get().get(key);
	}

	public <T extends Fluid> RegistryObject<T> getFluid(TypedRegKey<RegistryObject<T>> key)
	{
		return FLUIDS.get().get(key);
	}

	public ITag.INamedTag<Fluid> getFluidTag(String key)
	{
		return TAGS.get().getFluid(key);
	}

	public <T extends Entity> RegistryObject<EntityType<T>> getEntity(TypedRegKey<RegistryObject<EntityType<T>>> key)
	{
		return ENTITIES.get().get(key);
	}

	public ItemGroup getTab(String key)
	{
		return TABS.get().getTab(key);
	}

	public PacketChannel getNet(String key)
	{
		return NETS.get().getCHANNELS().get(key);
	}

	public PacketChannel getNet()
	{
		return getNet("main");
	}

	public <T extends BaseConfig> T getConfig(TypedRegKey<T> key)
	{
		return CONFIGS.get().getConfig(key);
	}

	public <T extends BaseConfig> ForgeConfigSpec getSpec(TypedRegKey<T> key)
	{
		return CONFIGS.get().getSpec(key);
	}

	public <T extends IForgeRegistryEntry<T>> ForgeRegistry<T> getCustomRegistry(TypedRegKey<T> type)
	{
		return REGISTRIES.get().get(type);
	}

	public <T extends IForgeRegistryEntry<T>> BuilderForgeRegistry<T> getCustomRegistryEntry(TypedRegKey<T> type)
	{
		return CUSTOM_REGISTRY_ENTRIES.get().get(type);
	}

	public void onClientSetup(FMLClientSetupEvent event)
	{
		ENTITIES.get().getENTRIES().forEach((key, entityTypeRegistryObject) ->
		                                    {
			                                    if (ENTITY_RENDERERS.get().containsKey(key.getId()))
			                                    {
				                                    registerEntityRenderingHandler(
						                                    entityTypeRegistryObject.get(),
						                                    ENTITY_RENDERERS.get().get(key.getId())
				                                    );
			                                    }
		                                    });
		TILES.get().getENTRIES().forEach((key, tileEntityTypeRegistryObject) ->
		                                 {
			                                 if (TILE_RENDERERS.get().containsKey(key.getId()))
			                                 {
				                                 bindTileEntityRenderer(
						                                 tileEntityTypeRegistryObject.get(),
						                                 TILE_RENDERERS.get().get(key.getId())
				                                 );
			                                 }
		                                 });
		GUIS.get().getGUIS().forEach((key, tileGui) ->
				                             registerScreen(tileGui.getContainer().get(), tileGui.getScreen()));
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void bindTileEntityRenderer(TileEntityType tile, ITileEntityRendererFactory renderer)
	{
		ClientRegistry.bindTileEntityRenderer(tile, renderer);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerScreen(ContainerType container, ScreenManager.IScreenFactory screen)
	{
		ScreenManager.register(container, screen);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	private void registerEntityRenderingHandler(EntityType entity, IRenderFactory renderer)
	{
		RenderingRegistry.registerEntityRenderingHandler(entity, renderer);
	}

	@SuppressWarnings("unchecked")
	public void onEntityAttributeCreation(EntityAttributeCreationEvent event)
	{
		ENTITIES.get().getENTRIES().forEach((key, entityTypeRegistryObject) ->
		                                    {
			                                    if (ENTITY_ATTRIBUTES.get().containsKey(key.getId()))
			                                    {
				                                    event.put(
						                                    (EntityType<? extends LivingEntity>) entityTypeRegistryObject.get(),
						                                    ENTITY_ATTRIBUTES.get().get(key.getId()).build()
				                                    );
			                                    }
		                                    });
	}

	@SubscribeEvent
	public void onNewRegistry(RegistryEvent.NewRegistry event)
	{
		REGISTRIES.get().register();
	}

	public void onGatherData(GatherDataEvent event)
	{
		DataGenerator      dataGenerator      = event.getGenerator();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
		dataGenerator.addProvider(new BuilderBlockStateProvider(dataGenerator, MOD.MODID, existingFileHelper)
		{
			@Override
			protected void registerStatesAndModels()
			{
				if (!DATA_GENERATOR.get().isBlockStatesEmpty())
				{
					DATA_GENERATOR.get().forEachBlockStates(builderBlockStateGeneratorConsumer ->
							                                        builderBlockStateGeneratorConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - BlockStates";
			}
		});

		dataGenerator.addProvider(new BuilderItemModelProvider(dataGenerator, MOD.MODID, existingFileHelper)
		{
			@Override
			protected void registerModels()
			{
				if (!DATA_GENERATOR.get().isItemModelsEmpty())
				{
					DATA_GENERATOR.get().forEachItemModels(builderItemModelProviderConsumer ->
							                                       builderItemModelProviderConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Item Models";
			}
		});

		BuilderBlockTagsProvider blockTagsProvider = new BuilderBlockTagsProvider(
				dataGenerator,
				MOD.MODID,
				existingFileHelper
		)
		{
			@Override
			protected void addTags()
			{
				if (!DATA_GENERATOR.get().isBlockTagsEmpty())
				{
					DATA_GENERATOR.get().forEachBlockTags(builderBlockTagsProviderConsumer ->
							                                      builderBlockTagsProviderConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Block Tags";
			}
		};
		dataGenerator.addProvider(blockTagsProvider);

		dataGenerator.addProvider(new BuilderItemTagsProvider(
				dataGenerator,
				blockTagsProvider,
				MOD.MODID,
				existingFileHelper
		)
		{
			@Override
			protected void addTags()
			{
				if (!DATA_GENERATOR.get().isItemTagsEmpty())
				{
					DATA_GENERATOR.get().forEachItemTags(builderItemTagsProviderConsumer ->
							                                     builderItemTagsProviderConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Item Tags";
			}
		});

		dataGenerator.addProvider(new BuilderFluidTagsProvider(dataGenerator, MOD.MODID, existingFileHelper)
		{
			@Override
			protected void addTags()
			{
				if (!DATA_GENERATOR.get().isFluidTagsEmpty())
				{
					DATA_GENERATOR.get().forEachFluidTags(builderFluidTagsProviderConsumer ->
							                                      builderFluidTagsProviderConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Fluid Tags";
			}
		});

		dataGenerator.addProvider(new BuilderLootTableProvider(dataGenerator)
		{
			@Nonnull
			@Override
			protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
			{
				List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> pairs = new java.util.ArrayList<>();
				if (!DATA_GENERATOR.get().isBlockLootTablesEmpty())
				{
					pairs.add(Pair.of(() -> new BuilderBlockLootTableProvider()
					{
						@Override
						protected void addTables()
						{

							DATA_GENERATOR.get().forEachBlockLootTables(consumerIterablePair ->
									                                            consumerIterablePair.getFirst().accept(
											                                            this));
						}

						@Nonnull
						@Override
						protected Iterable<Block> getKnownBlocks()
						{
							Stream.Builder<Block> knownBlocks = Stream.builder();
							DATA_GENERATOR.get().forEachBlockLootTables(consumerIterablePair ->
									                                            consumerIterablePair.getSecond().stream().map(
											                                            RegistryObject::get).forEach(
											                                            knownBlocks::add));
							return knownBlocks.build().collect(Collectors.toList());
						}
					}, LootParameterSets.BLOCK));
				}
				if (!DATA_GENERATOR.get().isChestLootTablesEmpty())
				{
					pairs.add(Pair.of(() -> new BuilderChestLootTableProvider()
					{
						@Override
						public void accept(@Nonnull BiConsumer<ResourceLocation, LootTable.Builder> consumer)
						{
							DATA_GENERATOR.get().forEachChestLootTables(biConsumerConsumer ->
									                                            biConsumerConsumer.accept(consumer));
						}
					}, LootParameterSets.CHEST));
				}
				if (!DATA_GENERATOR.get().isEntityLootTablesEmpty())
				{
					pairs.add(Pair.of(() -> new BuilderEntityLootTableProvider()
					{
						@Override
						protected void addTables()
						{

							DATA_GENERATOR.get().forEachEntityLootTables(consumerIterablePair ->
									                                             consumerIterablePair.getFirst().accept(
											                                             this));
						}

						@Nonnull
						@Override
						protected Iterable<EntityType<?>> getKnownEntities()
						{
							Stream.Builder<EntityType<?>> knownEntities = Stream.builder();
							DATA_GENERATOR.get().forEachEntityLootTables(consumerIterablePair ->
									                                             consumerIterablePair.getSecond().stream().map(
											                                             RegistryObject::get).forEach(
											                                             knownEntities::add));
							return knownEntities.build().collect(Collectors.toList());
						}
					}, LootParameterSets.ENTITY));
				}
				return pairs;
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Loot";
			}
		});

		dataGenerator.addProvider(new BuilderRecipeProvider(dataGenerator)
		{
			@Override
			protected void buildShapelessRecipes(@Nonnull Consumer<IFinishedRecipe> consumer)
			{
				if (!DATA_GENERATOR.get().isRecipesEmpty())
				{
					DATA_GENERATOR.get().forEachRecipes(consumerConsumer ->
							                                    consumerConsumer.accept(consumer));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Recipes";
			}
		});

		dataGenerator.addProvider(new BuilderLangProvider(dataGenerator, MOD.MODID, "en_us")
		{
			@Override
			protected void addTranslations()
			{
				if (!DATA_GENERATOR.get().isLangsEmpty())
				{
					DATA_GENERATOR.get().forEachLangs(builderLangProviderConsumer ->
							                                  builderLangProviderConsumer.accept(this));
				}
			}

			@Nonnull
			@Override
			public String getName()
			{
				return MOD.MODID + " - Langs";
			}
		});
	}
}
