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

package com.withertech.witherlibtest.tiles;

import com.withertech.witherlib.nbt.SyncVariable;
import com.withertech.witherlib.nbt.wrappers.BlockPosNBTWrapper;
import com.withertech.witherlib.nbt.wrappers.ListNBTWrapper;
import com.withertech.witherlib.nbt.wrappers.MapNBTWrapper;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlibtest.WitherLibTest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestNBTTile extends BaseTileEntity<TestNBTTile>
{
	@SyncVariable(name = "TestList")
	public final ListNBTWrapper<BlockPosNBTWrapper> blockPosNBTWrapperListNBTWrapper = new ListNBTWrapper<>(new ArrayList<>(Arrays.asList(new BlockPosNBTWrapper(new BlockPos(0, 0, 0)), new BlockPosNBTWrapper(new BlockPos(1, 1, 1)))), () -> new BlockPosNBTWrapper(new BlockPos(0,0,0)));

	@SyncVariable(name = "TestMap")
	public final MapNBTWrapper<BlockPosNBTWrapper> blockPosNBTWrapperMapNBTWrapper = new MapNBTWrapper<>(
		Stream.of(new Object[][]
		{
			{ "key1", new BlockPosNBTWrapper(new BlockPos(0,0,0)) },
			{ "key2", new BlockPosNBTWrapper(new BlockPos(1,1,1)) },
		}).collect(Collectors.toMap(data -> (String) data[0], data -> (BlockPosNBTWrapper) data[1])),
		() -> new BlockPosNBTWrapper(new BlockPos(0,0,0))
	);

	public TestNBTTile(BlockPos pos, BlockState state)
	{
		super(WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_nbt_tile", TestNBTTile.class)).get(), pos, state);
	}
}
