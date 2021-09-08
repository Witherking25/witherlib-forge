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
import com.withertech.witherlibtest.block.TestBlock;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UnitTests
{
	@Test
	public void testBlockRegistry()
	{
		assertNotNull(WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_block", TestBlock.class)).get(), "\"Test Block\" not found");
		assertEquals("witherlibtest:test_block", WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_block", TestBlock.class)).get().getRegistryName().toString());
	}

	@Test
	public void testItemRegistry()
	{
		assertNotNull(WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_block", BlockItem.class)).get(), "\"Test Block Item\" not found");
		assertEquals("witherlibtest:test_block", WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_block", BlockItem.class)).get().getRegistryName().toString());
	}
}
