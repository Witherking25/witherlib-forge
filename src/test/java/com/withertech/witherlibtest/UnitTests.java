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

import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.blocks.*;
import com.withertech.witherlibtest.containers.TestContainer;
import com.withertech.witherlibtest.containers.TestEnergyContainer;
import com.withertech.witherlibtest.containers.TestProgressContainer;
import com.withertech.witherlibtest.entities.TestEntity;
import com.withertech.witherlibtest.fluids.TestFluid;
import com.withertech.witherlibtest.items.TestItem;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import com.withertech.witherlibtest.tiles.TestNBTTile;
import com.withertech.witherlibtest.tiles.TestProgressTile;
import com.withertech.witherlibtest.tiles.TestTile;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTests
{
	@Test
	public void testBlockRegistry()
	{
		assertRegistered("witherlibtest:test_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_block", TestBlock.class)).get(), "\"Test Block\" not found");
		assertRegistered("witherlibtest:test_tile_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_tile_block", TestTileBlock.class)).get(), "\"Test Tile Block\" not found");
		assertRegistered("witherlibtest:test_energy_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_energy_block", TestEnergyBlock.class)).get(), "\"Test Energy Block\" not found");
		assertRegistered("witherlibtest:test_progress_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_progress_block", TestProgressBlock.class)).get(), "\"Test Progress Block\" not found");
		assertRegistered("witherlibtest:test_nbt_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_nbt_block", TestNBTBlock.class)).get(), "\"Test NBT Block\" not found");
	}

	@Test
	public void testItemRegistry()
	{
		assertRegistered("witherlibtest:test_item", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_item", TestItem.class)).get(), "\"Test Item\" not found");
		assertRegistered("witherlibtest:test_fluid_bucket", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_fluid_bucket", BucketItem.class)).get(), "\"Test Fluid Bucket\" not found");
		assertRegistered("witherlibtest:test_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_block", BlockItem.class)).get(), "\"Test Block\" not found");
		assertRegistered("witherlibtest:test_tile_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_tile_block", BlockItem.class)).get(), "\"Test Tile Block\" not found");
		assertRegistered("witherlibtest:test_energy_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_energy_block", BlockItem.class)).get(), "\"Test Energy Block\" not found");
		assertRegistered("witherlibtest:test_progress_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_progress_block", BlockItem.class)).get(), "\"Test Progress Block\" not found");
		assertRegistered("witherlibtest:test_nbt_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_nbt_block", BlockItem.class)).get(), "\"Test NBT Block Item\" not found");
	}

	@Test
	public void testFluidRegistry()
	{
		assertRegistered("witherlibtest:test_fluid", WitherLibTest.INSTANCE.REGISTRY.getFluid(TypedRegKey.fluid("test_fluid", TestFluid.Source.class)).get(), "\"Test Source Fluid\" not found");
		assertRegistered("witherlibtest:test_fluid_flowing", WitherLibTest.INSTANCE.REGISTRY.getFluid(TypedRegKey.fluid("test_fluid_flowing", TestFluid.Flowing.class)).get(), "\"Test Flowing Fluid\" not found");
	}

	@Test
	public void testEntityRegistry()
	{
		assertRegistered("witherlibtest:test_entity", WitherLibTest.INSTANCE.REGISTRY.getEntity(TypedRegKey.entity("test_entity", TestEntity.class)).get(), "\"Test Entity\" not found");
	}

	@Test
	public void testTileRegistry()
	{
		assertRegistered("witherlibtest:test_tile", WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_tile", TestTile.class)).get(), "\"Test Tile\" not found");
		assertRegistered("witherlibtest:test_energy_tile", WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class)).get(), "\"Test Energy Tile\" not found");
		assertRegistered("witherlibtest:test_progress_tile", WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_progress_tile", TestProgressTile.class)).get(), "\"Test Progress Tile\" not found");
		assertRegistered("witherlibtest:test_nbt_tile", WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_nbt_tile", TestNBTTile.class)).get(), "\"Test NBT Tile\" not found");
	}

	@Test
	public void testContainerRegistry()
	{
		assertRegistered("witherlibtest:test_container", WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_container", TestContainer.class)).get(), "\"Test Container\" not found");
		assertRegistered("witherlibtest:test_energy_container", WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_energy_container", TestEnergyContainer.class)).get(), "\"Test Energy Container\" not found");
		assertRegistered("witherlibtest:test_progress_container", WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_progress_container", TestProgressContainer.class)).get(), "\"Test Progress Container\" not found");
	}

	private <T extends IForgeRegistryEntry<T>> void assertRegistered(@Nonnull String registryName, @Nullable T entry, @Nonnull String message)
	{
		assertNotNull(entry, message);
		assertEquals(registryName, Objects.requireNonNull(entry.getRegistryName()).toString(), message);
	}
}
