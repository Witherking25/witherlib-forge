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

package com.withertech.witherlib;

import com.withertech.witherlib.data.BuilderDataGenerator;
import com.withertech.witherlib.data.BuilderRecipeProvider;
import com.withertech.witherlib.registration.*;
import com.withertech.witherlib.test.blocks.TestBlock;
import com.withertech.witherlib.test.client.entity.render.TestEntityRender;
import com.withertech.witherlib.test.entities.TestEntity;
import com.withertech.witherlib.test.fluids.TestFluid;
import com.withertech.witherlib.test.items.TestItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.*;
import net.minecraft.loot.*;
import net.minecraft.loot.functions.EnchantRandomly;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;

@Mod(WitherLib.MODID)
public class WitherLib extends BuilderMod
{
    public static final String MODID = "witherlib";
    public static final Logger LOGGER = LogManager.getLogger();
    public static WitherLib INSTANCE;


    public WitherLib()
    {
        super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
        INSTANCE = this;
        MinecraftForge.EVENT_BUS.register(this);

//        if (System.getenv().containsKey("witherlib.dev"))
//        {
//        }
    }


    @Override
    protected BuilderForgeRegistry<Block> registerBlocks()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.BLOCKS)
                .add("test_block", () -> new TestBlock(AbstractBlock.Properties.of(Material.STONE)))
                .add("test_fluid", () -> new FlowingFluidBlock(() -> (TestFluid) getFluids().get("test_fluid").get(), AbstractBlock.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Item> registerItems()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.ITEMS)
                .add("test_item", () -> new TestItem(new Item.Properties().tab(getTabs().getTab("witherlib"))))
                .add("test_block", () -> new BlockItem(getBlocks().get("test_block").get(), new Item.Properties().tab(getTabs().getTab("witherlib"))))
                .add("test_fluid_bucket", () -> new BucketItem(() -> getFluids().get("test_fluid").get(), new Item.Properties().tab(ItemGroup.TAB_MISC)))
                .build();
    }

    @Override
    protected BuilderForgeRegistry<Fluid> registerFluids()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.FLUIDS)
                .add("test_fluid", TestFluid.Source::new)
                .add("test_fluid_flowing", TestFluid.Flowing::new)
                .build();
    }

    @Override
    protected BuilderForgeRegistry<EntityType<?>> registerEntities()
    {
        return BuilderForgeRegistry.Builder.create(MOD, ForgeRegistries.ENTITIES)
                .add("test_entity", () -> EntityType.Builder.of(TestEntity::new, EntityClassification.CREATURE)
                        .sized(0.9f, 1.3f)
                        .build(MOD.modLocation("test_entity").toString()))
                .build();
    }

    @Override
    protected BuilderEntityAttributeRegistry registerEntityAttributes()
    {
        return BuilderEntityAttributeRegistry.Builder.create(MOD)
                .addEntity("test_entity", () -> MobEntity.createMobAttributes().add(Attributes.MAX_HEALTH, 8.0D).add(Attributes.MOVEMENT_SPEED, 0.23F))
                .build();
    }

    @Override
    protected BuilderEntityRendererRegistry registerEntityRenderers()
    {
        return BuilderEntityRendererRegistry.Builder.create(MOD)
                .addEntity("test_entity", TestEntityRender::new)
                .build();
    }

    @Override
    protected BuilderDataGenerator registerDataGenerators()
    {
        return BuilderDataGenerator.Builder.create(MOD)

                .addBlockState(builderBlockStateGenerator ->
                {
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get("test_block").get()
                    );
                    builderBlockStateGenerator.simpleBlock(
                            getBlocks().get("test_fluid").get(),
                            builderBlockStateGenerator.models()
                                    .getBuilder("test_fluid")
                                    .texture("particle",
                                            builderBlockStateGenerator.modLoc("block/test_fluid_still")
                                    )
                    );
                })
                .addItemModel(builderItemModelProvider ->
                {
                    builderItemModelProvider.blockBuilder(getBlocks().get("test_block").get());
                    builderItemModelProvider.builder(getItems().get("test_item").get(), builderItemModelProvider.getGenerated());
                    builderItemModelProvider.builder(getItems().get("test_fluid_bucket").get(), builderItemModelProvider.getGenerated());
                })
                .addBlockTag(builderBlockTagsProvider ->
                {
                    builderBlockTagsProvider.tag(getTags().getBlock("test_block"))
                            .add(getBlocks().get("test_block").get());
                })
                .addItemTag(builderItemTagsProvider ->
                {
                    builderItemTagsProvider.tag(getTags().getItem("test_item"))
                            .add(getItems().get("test_item").get());
                })
                .addFluidTag(builderFluidTagsProvider ->
                {
                    builderFluidTagsProvider.tag(getTags().getFluid("test_fluid"))
                            .add(getFluids().get("test_fluid").get())
                            .add(getFluids().get("test_fluid_flowing").get());
                })
                .addBlockLootTable(builderBlockLootTableProvider ->
                {
                    builderBlockLootTableProvider.dropSelf(getBlocks().get("test_block").get());
                }, new ArrayList<>(getBlocks().getREGISTRY().getEntries()))
                .addChestLootTable(consumer ->
                {
                    consumer.accept(MOD.modLocation("chests/test"), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(Items.GOLDEN_APPLE).setWeight(20)).add(ItemLootEntry.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE)).add(ItemLootEntry.lootTableItem(Items.NAME_TAG).setWeight(30)).add(ItemLootEntry.lootTableItem(Items.BOOK).setWeight(10).apply(EnchantRandomly.randomApplicableEnchantment())).add(ItemLootEntry.lootTableItem(Items.IRON_PICKAXE).setWeight(5)).add(EmptyLootEntry.emptyItem().setWeight(5))).withPool(LootPool.lootPool().setRolls(RandomValueRange.between(2.0F, 4.0F)).add(ItemLootEntry.lootTableItem(Items.IRON_INGOT).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(1.0F, 5.0F)))).add(ItemLootEntry.lootTableItem(Items.GOLD_INGOT).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)))).add(ItemLootEntry.lootTableItem(Items.REDSTONE).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(4.0F, 9.0F)))).add(ItemLootEntry.lootTableItem(Items.LAPIS_LAZULI).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(4.0F, 9.0F)))).add(ItemLootEntry.lootTableItem(Items.DIAMOND).setWeight(3).apply(SetCount.setCount(RandomValueRange.between(1.0F, 2.0F)))).add(ItemLootEntry.lootTableItem(Items.COAL).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(3.0F, 8.0F)))).add(ItemLootEntry.lootTableItem(Items.BREAD).setWeight(15).apply(SetCount.setCount(RandomValueRange.between(1.0F, 3.0F)))).add(ItemLootEntry.lootTableItem(Items.MELON_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10).apply(SetCount.setCount(RandomValueRange.between(2.0F, 4.0F))))).withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(3)).add(ItemLootEntry.lootTableItem(Blocks.RAIL).setWeight(20).apply(SetCount.setCount(RandomValueRange.between(4.0F, 8.0F)))).add(ItemLootEntry.lootTableItem(Blocks.POWERED_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.DETECTOR_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.ACTIVATOR_RAIL).setWeight(5).apply(SetCount.setCount(RandomValueRange.between(1.0F, 4.0F)))).add(ItemLootEntry.lootTableItem(Blocks.TORCH).setWeight(15).apply(SetCount.setCount(RandomValueRange.between(1.0F, 16.0F))))));
                })
                .addEntityLootTable(builderEntityLootTableProvider ->
                {
                    builderEntityLootTableProvider.add(getEntities().get("test_entity").get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantRange.exactly(1)).add(ItemLootEntry.lootTableItem(getItems().get("test_item").get()).setWeight(100))));
                }, new ArrayList<>(getEntities().getREGISTRY().getEntries()))
                .addRecipe(iFinishedRecipeConsumer ->
                {
                    ShapelessRecipeBuilder.shapeless(Items.DIAMOND)
                            .requires(Items.COAL_BLOCK, 9)
                            .unlockedBy("has_coal_block", BuilderRecipeProvider.has(Items.COAL_BLOCK))
                            .save(iFinishedRecipeConsumer);
                })
                .addLang(builderLangProvider ->
                {
                    builderLangProvider.add(getItems().get("test_item").get(), "Test Item");
                    builderLangProvider.add(getItems().get("test_fluid_bucket").get(), "Test Fluid Bucket");
                    builderLangProvider.add(getBlocks().get("test_block").get(), "Test Block");
                    builderLangProvider.add(getEntities().get("test_entity").get(), "Test Entity");
                    builderLangProvider.add(getFluids().get("test_fluid").get().getAttributes().getTranslationKey(), "Test Fluid");
                    builderLangProvider.add(((TranslationTextComponent) getTabs().getTab("witherlib").getDisplayName()).getKey(), "Test Tab");
                })
                .build();
    }

    @Override
    protected BuilderTagRegistry registerTags()
    {
        return BuilderTagRegistry.Builder.create(MOD)
                .addBlock("test_block")
                .addItem("test_item")
                .addFluid("test_fluid")
                .build();
    }

    @Override
    protected BuilderTabRegistry registerTabs()
    {
        return BuilderTabRegistry.Builder.create(MOD)
                .addGroup("witherlib", () -> new ItemGroup("witherlib")
                {

                    @Nonnull
                    @Override
                    public ItemStack makeIcon()
                    {
                        return new ItemStack(REGISTRY.getItem("test_item").get());
                    }
                })
                .build();
    }
}
