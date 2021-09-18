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
import com.withertech.witherlibtest.tiles.TestProgressTile;
import net.minecraft.core.BlockPos;

public abstract class TestProgressTilePacket extends TileEntityBasePacket<TestProgressTile>
{
    public TestProgressTilePacket()
    {
        super();
    }

    public TestProgressTilePacket(BlockPos pos)
    {
        super(pos);
    }

    public static class TestProgressTileEnablePacket extends TestProgressTilePacket
    {
        public TestProgressTileEnablePacket()
        {
            super();
        }

        public TestProgressTileEnablePacket(BlockPos pos)
        {
            super(pos);
        }

        @Override
        protected void handle(TestProgressTile tile, PacketContext context)
        {
            tile.toggleRunning();
            tile.dataChanged();
        }
    }




}
