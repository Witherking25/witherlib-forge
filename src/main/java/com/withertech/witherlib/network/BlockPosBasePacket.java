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

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;

/**
 * Created 5/30/2021 by SuperMartijn642
 */
public abstract class BlockPosBasePacket implements BasePacket
{

	public BlockPos pos;

	public BlockPosBasePacket()
	{
	}

	/**
	 * Stores the given {@code pos} with the packet data.
	 *
	 * @param pos
	 * 		position to be stored
	 */
	public BlockPosBasePacket(BlockPos pos)
	{
		this.pos = pos;
	}

	@Override
	public void write(PacketBuffer buffer)
	{
		buffer.writeBlockPos(this.pos);
	}

	@Override
	public void read(PacketBuffer buffer)
	{
		this.pos = buffer.readBlockPos();
	}

	@Override
	public void handle(PacketContext context)
	{
		this.handle(this.pos, context);
	}

	protected abstract void handle(BlockPos pos, PacketContext context);
}
