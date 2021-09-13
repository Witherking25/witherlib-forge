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

import com.alcatrazescapee.mcjunitlib.framework.IntegrationTest;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestClass;
import com.alcatrazescapee.mcjunitlib.framework.IntegrationTestHelper;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.blocks.TestNBTBlock;
import com.withertech.witherlibtest.tiles.TestNBTTile;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

@IntegrationTestClass("tests")
public class IntegrationTests
{
	@IntegrationTest("test_nbt_tile")
	public void testNBT(IntegrationTestHelper helper)
	{

		helper.placeBlock(BlockPos.ZERO, Direction.DOWN, WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_nbt_block", TestNBTBlock.class)).get());
		helper.assertTileEntityAt(BlockPos.ZERO, TestNBTTile.class, "Tile entity not found");
		TestNBTTile testNBTTile = (TestNBTTile) helper.getTileEntity(BlockPos.ZERO);

		assert testNBTTile != null;
		CompoundNBT tileData = testNBTTile.serializeNBT();
		helper.assertTrue(() -> tileData.contains("data"), "\"data\" tag missing");
		CompoundNBT data = tileData.getCompound("data");

		{
			helper.assertTrue(() -> data.contains("TestList"), "\"TestList\" tag missing");
			ListNBT testList = data.getCompound("TestList").getList("values", Constants.NBT.TAG_COMPOUND);
			helper.assertTrue(() -> testList.size() == 2, "\"TestList\" has wrong size");

			CompoundNBT key1 = testList.getCompound(0);
			helper.assertTrue(() -> key1.contains("X") && key1.contains("Y") && key1.contains("Z"), "\"pos1\" contents missing");
			BlockPos pos1 = NBTUtil.readBlockPos(key1);
			helper.assertTrue(() -> pos1.getX() == 0 && pos1.getY() == 0 && pos1.getZ() == 0, "\"pos1\" has wrong contents");

			CompoundNBT key2 = testList.getCompound(1);
			helper.assertTrue(() -> key2.contains("X") && key2.contains("Y") && key2.contains("Z"), "\"pos2\" contents missing");
			BlockPos pos2 = NBTUtil.readBlockPos(key2);
			helper.assertTrue(() -> pos2.getX() == 1 && pos2.getY() == 1 && pos2.getZ() == 1, "\"pos2\" has wrong contents");
		}

		{
			helper.assertTrue(() -> data.contains("TestMap"), "\"TestMap\" tag missing");
			CompoundNBT testMap = data.getCompound("TestMap");

			helper.assertTrue(() -> testMap.contains("key1"), "\"key1\" tag missing");
			CompoundNBT key1 = testMap.getCompound("key1");
			helper.assertTrue(() -> key1.contains("X") && key1.contains("Y") && key1.contains("Z"), "\"key1\" contents missing");
			BlockPos pos1 = NBTUtil.readBlockPos(key1);
			helper.assertTrue(() -> pos1.getX() == 0 && pos1.getY() == 0 && pos1.getZ() == 0, "\"key1\" has wrong contents");

			helper.assertTrue(() -> testMap.contains("key2"), "\"key2\" tag missing");
			CompoundNBT key2 = testMap.getCompound("key2");
			helper.assertTrue(() -> key2.contains("X") && key2.contains("Y") && key2.contains("Z"), "\"key2\" contents missing");
			BlockPos pos2 = NBTUtil.readBlockPos(key2);
			helper.assertTrue(() -> pos2.getX() == 1 && pos2.getY() == 1 && pos2.getZ() == 1, "\"key2\" has wrong contents");
		}

	}
}
