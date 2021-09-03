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

package com.withertech.witherlib.network;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

public abstract class TileEntityLambdaPacket<T extends TileEntity> extends TileEntityBasePacket<T>
{
    private final BiConsumer<T, PacketContext> handler;

    public TileEntityLambdaPacket()
    {
        this.handler = null;
    }

    public TileEntityLambdaPacket(RegistryKey<World> dimension, BlockPos pos, BiConsumer<T, PacketContext> handler)
    {
        super(dimension, pos);
        this.handler = handler;
    }

    public TileEntityLambdaPacket(World world, BlockPos pos, BiConsumer<T, PacketContext> handler)
    {
        super(world, pos);
        this.handler = handler;
    }

    public TileEntityLambdaPacket(BlockPos pos, BiConsumer<T, PacketContext> handler)
    {
        super(pos);
        this.handler = handler;
    }

    @Override
    protected void handle(T tile, PacketContext context)
    {
        if (handler != null)
            handler.accept(tile, context);
    }
}
