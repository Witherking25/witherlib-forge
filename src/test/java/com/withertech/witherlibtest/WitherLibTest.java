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

package com.withertech.witherlibtest;

import com.withertech.witherlib.registration.BuilderForgeRegistry;
import com.withertech.witherlib.registration.BuilderMod;
import com.withertech.witherlib.registration.ModData;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.block.TestBlock;
import com.withertech.witherlibtest.tile.TestTile;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(WitherLibTest.MODID)
public class WitherLibTest extends BuilderMod
{
	public static final String MODID = "witherlibtest";

	public static WitherLibTest INSTANCE;

	public WitherLibTest()
	{
		super(new ModData(MODID, FMLJavaModLoadingContext.get().getModEventBus()));
		INSTANCE = this;
	}

	@Override
	protected BuilderForgeRegistry<Block> registerBlocks()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.BLOCKS)
				.add(TypedRegKey.block("test_block", TestBlock.class), TestBlock::new)
				.build();
	}

	@Override
	protected BuilderForgeRegistry<Item> registerItems()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.ITEMS)
				.add(TypedRegKey.item("test_block", BlockItem.class), () -> new BlockItem(getBlocks().get(TypedRegKey.block("test_block", TestBlock.class)).get(), new Item.Properties()))
				.build();
	}

	@Override
	protected BuilderForgeRegistry<TileEntityType<?>> registerTiles()
	{
		return BuilderForgeRegistry.builder(MOD, ForgeRegistries.TILE_ENTITIES)
				.add(TypedRegKey.tile("test_tile", TestTile.class), () -> TileEntityType.Builder.of(TestTile::new).build(null))
				.build();
	}
}
