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

/**
 * Created 5/30/2021 by SuperMartijn642
 */
public interface BasePacket
{

	/**
	 * Writes the data in the packet to the given {@code buffer}.
	 * The written data will be decoded in {@link #read(PacketBuffer)}.
	 *
	 * @param buffer
	 * 		data buffer to write to
	 */
	void write(PacketBuffer buffer);

	/**
	 * Reads data written by {@link #write(PacketBuffer)} from the given
	 * {@code buffer} into the packet.
	 *
	 * @param buffer
	 * 		data buffer to read from
	 */
	void read(PacketBuffer buffer);

	/**
	 * Checks whether the received values are valid.
	 * If {@code false} is returned, the packet will be discarded.
	 *
	 * @return {@code true} if all received values are valid
	 */
	default boolean verify(PacketContext context)
	{
		return true;
	}

	void handle(PacketContext context);

}
