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

import com.withertech.witherlib.util.ClientUtils;
import com.withertech.witherlib.util.CoreSide;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

/**
 * Created 5/30/2021 by SuperMartijn642
 */
public class PacketContext
{

	private final NetworkEvent.Context context;

	public PacketContext(NetworkEvent.Context context)
	{
		this.context = context;
	}

	/**
	 * @return the side the packet is received on
	 */
	public CoreSide getHandlingSide()
	{
		return this.context.getDirection().getReceptionSide() == LogicalSide.CLIENT ? CoreSide.CLIENT :
				CoreSide.SERVER;
	}

	/**
	 * @return the side the packet is originating from
	 */
	public CoreSide getOriginatingSide()
	{
		return this.context.getDirection().getOriginationSide() == LogicalSide.CLIENT ? CoreSide.CLIENT :
				CoreSide.SERVER;
	}

	public Player getSendingPlayer()
	{
		return this.context.getSender();
	}

	/**
	 * @return the client world if client-side, or the sending player's world if server-side
	 */
	public Level getWorld()
	{
		return this.getHandlingSide() == CoreSide.CLIENT ? ClientUtils.getWorld() : this.getSendingPlayer().level;
	}

	public void queueTask(Runnable task)
	{
		if (this.getHandlingSide() == CoreSide.SERVER)
		{
			this.context.enqueueWork(task);
		} else
		{
			ClientUtils.queueTask(task);
		}
	}

	@Deprecated
	public NetworkEvent.Context getUnderlyingContext()
	{
		return this.context;
	}

}
