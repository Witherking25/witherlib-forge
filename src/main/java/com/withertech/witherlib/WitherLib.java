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

import com.withertech.witherlib.registration.BuilderRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(WitherLib.MODID)
public class WitherLib
{
    public static final String MODID = "witherlib";
    public static final Logger LOGGER = LogManager.getLogger();


    public static ItemGroup TEST_TAB;
    public static BuilderRegistry<Block> BLOCKS;
    public static BuilderRegistry<Item> ITEMS;


    public WitherLib()
    {
        MinecraftForge.EVENT_BUS.register(this);
        if (System.getenv().containsKey("witherlib.dev"))
        {
            TEST_TAB = new ItemGroup("witherlib")
            {
                @Nonnull
                @Override
                public ItemStack createIcon()
                {
                    return new ItemStack(ITEMS.get("test_item").get());
                }
            };
            LOGGER.debug("Registering Blocks");
            BLOCKS = BuilderRegistry.Builder.create(WitherLib.MODID, Block.class)
                    .add("test_block", () -> new Block(AbstractBlock.Properties.create(Material.ROCK)))
                    .build(FMLJavaModLoadingContext.get().getModEventBus());
            LOGGER.debug("Registering Items");
            ITEMS = BuilderRegistry.Builder.create(WitherLib.MODID, Item.class)
                    .add("test_item", () -> new Item(new Item.Properties().group(TEST_TAB)))
                    .add("test_block_item", () -> new BlockItem(BLOCKS.get("test_block").get(), new Item.Properties().group(TEST_TAB)))
                    .build(FMLJavaModLoadingContext.get().getModEventBus());
        }
    }
}
