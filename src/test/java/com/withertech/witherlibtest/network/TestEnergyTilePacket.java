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

package com.withertech.witherlibtest.network;

import com.withertech.witherlib.network.PacketContext;
import com.withertech.witherlib.network.TileEntityBasePacket;
import com.withertech.witherlib.util.PacketUtils;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.util.math.BlockPos;

public abstract class TestEnergyTilePacket extends TileEntityBasePacket<TestEnergyTile>
{
	public TestEnergyTilePacket()
	{
		super();
	}

	public TestEnergyTilePacket(BlockPos pos)
	{
		super(pos);
	}

	public static class TestEnergyTileFluidInteractPacket extends TestEnergyTilePacket
	{
		public TestEnergyTileFluidInteractPacket()
		{
			super();
		}

		public TestEnergyTileFluidInteractPacket(BlockPos pos)
		{
			super(pos);
		}

		@Override
		protected void handle(TestEnergyTile tile, PacketContext context)
		{
			PacketUtils.interactCarriedItemWithFluidHandler(tile, context);
			tile.dataChanged();
		}
	}
}
