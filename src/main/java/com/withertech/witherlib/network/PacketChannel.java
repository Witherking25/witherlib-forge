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

import io.netty.util.collection.IntObjectHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

import java.util.HashMap;
import java.util.function.Supplier;

/**
 * Created 5/30/2021 by SuperMartijn642
 */
public class PacketChannel
{

	private final SimpleChannel channel;
	private final HashMap<Class<? extends BasePacket>, Integer> packet_to_index = new HashMap<>();
	private final IntObjectHashMap<Supplier<? extends BasePacket>> index_to_packet = new IntObjectHashMap<>();
	/**
	 * Whether a packet should be handled on the main thread or off thread
	 */
	private final HashMap<Class<? extends BasePacket>, Boolean> packet_to_queued = new HashMap<>();
	private int index = 0;

	private PacketChannel(String modid, String name)
	{
		this.channel = NetworkRegistry.newSimpleChannel(
				new ResourceLocation(modid, name),
				() -> "1",
				"1"::equals,
				"1"::equals
		);
		this.channel.registerMessage(0, InternalPacket.class,
				(message, buffer) -> InternalPacket.write(this, message, buffer),
				buffer -> InternalPacket.read(this, buffer),
				(message, context) -> InternalPacket.handle(this, message, context)
		);
	}

	/**
	 * Creates a channel with the given {@code registryName}.
	 *
	 * @param registryName registry name of the channel
	 * @return a new channel with the given {@code registryName}
	 * @throws IllegalArgumentException if {@code registryName == null}
	 */
	public static PacketChannel create(String modid, String registryName)
	{
		if (modid == null || modid.isEmpty())
		{
			throw new IllegalArgumentException("Modid must not be null!");
		}
		if (registryName == null)
		{
			throw new IllegalArgumentException("Registry name must not be null!");
		}
		return new PacketChannel(modid, registryName);
	}

	/**
	 * Creates a new channel.
	 *
	 * @return a new channel with registry name 'main'
	 */
	public static PacketChannel create(String modid)
	{
		return create(modid, "main");
	}

	@Deprecated
	public static PacketChannel create()
	{
		return create(ModLoadingContext.get().getActiveNamespace(), "main");
	}

	/**
	 * Registers a packet for this channel
	 *
	 * @param packetClass    class of the packet
	 * @param packetSupplier supplier for new packet instances
	 * @param shouldBeQueued whether the packet should be handled on the main thread
	 */
	public <T extends BasePacket> void registerMessage(
			Class<T> packetClass,
			Supplier<T> packetSupplier,
			boolean shouldBeQueued
	)
	{
		if (this.packet_to_index.containsKey(packetClass))
		{
			throw new IllegalArgumentException("Class '" + packetClass + "' has already been registered!");
		}

		int index = this.index++;
		this.packet_to_index.put(packetClass, index);
		this.index_to_packet.put(index, packetSupplier);
		this.packet_to_queued.put(packetClass, shouldBeQueued);
	}

	/**
	 * Sends the given {@code packet} to the server. Must only be used client-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToServer(BasePacket packet)
	{
		this.checkRegistration(packet);
		this.channel.sendToServer(new InternalPacket().setPacket(packet));
	}

	/**
	 * Sends the given {@code packet} to the server. Must only be used server-side.
	 *
	 * @param player player to send the packet to
	 * @param packet packet to be send
	 */
	public void sendToPlayer(Player player, BasePacket packet)
	{
		if (!(player instanceof ServerPlayer))
		{
			throw new IllegalStateException("This must only be called server-side!");
		}
		this.checkRegistration(packet);
		this.channel.send(
				PacketDistributor.PLAYER.with(() -> (ServerPlayer) player),
				new InternalPacket().setPacket(packet)
		);
	}

	/**
	 * Sends the given {@code packet} to all players. Must only be used server-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToAllPlayers(BasePacket packet)
	{
		this.checkRegistration(packet);
		this.channel.send(PacketDistributor.ALL.noArg(), new InternalPacket().setPacket(packet));
	}

	/**
	 * Sends the given {@code packet} to all players in the given {@code dimension}. Must only be used server-side.
	 *
	 * @param dimension dimension to send the packet to
	 * @param packet    packet to be send
	 */
	public void sendToDimension(ResourceKey<Level> dimension, BasePacket packet)
	{
		this.checkRegistration(packet);
		this.channel.send(PacketDistributor.DIMENSION.with(() -> dimension), new InternalPacket().setPacket(packet));
	}

	/**
	 * Sends the given {@code packet} to all players in the given {@code world}. Must only be used server-side.
	 *
	 * @param world  world to send the packet to
	 * @param packet packet to be send
	 */
	public void sendToDimension(Level world, BasePacket packet)
	{
		if (world.isClientSide)
		{
			throw new IllegalStateException("This must only be called server-side!");
		}
		this.sendToDimension(world.dimension(), packet);
	}

	/**
	 * Sends the given {@code packet} to all players tracking the given {@code entity}. Must only be used server-side.
	 *
	 * @param entity entity which should be tracked
	 * @param packet packet to be send
	 */
	public void sendToAllTrackingEntity(Entity entity, BasePacket packet)
	{
		if (entity.level.isClientSide)
		{
			throw new IllegalStateException("This must only be called server-side!");
		}
		this.checkRegistration(packet);
		this.channel.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity),
				new InternalPacket().setPacket(packet));
	}

	/**
	 * Sends the given {@code packet} to all players tracking the given position in the given {@code world}. Must only
	 * be used server-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToAllNear(ResourceKey<Level> world, double x, double y, double z, double radius, BasePacket packet)
	{
		this.checkRegistration(packet);
		this.channel.send(
				PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(x, y, z, radius, world)),
				new InternalPacket().setPacket(packet)
		);
	}

	/**
	 * Sends the given {@code packet} to all players tracking the given position in the given {@code world}. Must only
	 * be used server-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToAllNear(ResourceKey<Level> world, BlockPos pos, double radius, BasePacket packet)
	{
		this.sendToAllNear(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, radius, packet);
	}

	/**
	 * Sends the given {@code packet} to all players tracking the given position in the given {@code world}. Must only
	 * be used server-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToAllNear(Level world, double x, double y, double z, double radius, BasePacket packet)
	{
		if (world.isClientSide)
		{
			throw new IllegalStateException("This must only be called server-side!");
		}
		this.sendToAllNear(world.dimension(), x, y, z, radius, packet);
	}

	/**
	 * Sends the given {@code packet} to all players tracking the given position in the given {@code world}. Must only
	 * be used server-side.
	 *
	 * @param packet packet to be send
	 */
	public void sendToAllNear(Level world, BlockPos pos, double radius, BasePacket packet)
	{
		if (world.isClientSide)
		{
			throw new IllegalStateException("This must only be called server-side!");
		}
		this.sendToAllNear(world.dimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, radius, packet);
	}

	private void checkRegistration(BasePacket packet)
	{
		if (!this.packet_to_index.containsKey(packet.getClass()))
		{
			throw new IllegalArgumentException("Tried to send unregistered packet '" + packet.getClass() + "'!");
		}
	}

	private void write(BasePacket packet, FriendlyByteBuf buffer)
	{
		// assume the packet has already been checked for registration here
		int index = this.packet_to_index.get(packet.getClass());
		buffer.writeInt(index);
		packet.write(buffer);
	}

	private BasePacket read(FriendlyByteBuf buffer)
	{
		int index = buffer.readInt();
		if (!this.index_to_packet.containsKey(index))
		{
			throw new IllegalStateException("Received an unregistered packet with index '" + index + "'!");
		}

		BasePacket packet = this.index_to_packet.get(index).get();
		packet.read(buffer);
		return packet;
	}

	private void handle(BasePacket packet, Supplier<NetworkEvent.Context> contextSupplier)
	{
		contextSupplier.get().setPacketHandled(true);
		PacketContext context = new PacketContext(contextSupplier.get());
		if (packet.verify(context))
		{
			if (this.packet_to_queued.get(packet.getClass()))
			{
				context.queueTask(() -> packet.handle(context));
			} else
			{
				packet.handle(context);
			}
		}
	}

	private static class InternalPacket
	{

		private BasePacket packet;

		public static InternalPacket read(PacketChannel channel, FriendlyByteBuf buffer)
		{
			return new InternalPacket().setPacket(channel.read(buffer));
		}

		public static void write(PacketChannel channel, InternalPacket packet, FriendlyByteBuf buffer)
		{
			channel.write(packet.packet, buffer);
		}

		public static void handle(PacketChannel channel, InternalPacket packet, Supplier<NetworkEvent.Context> context)
		{
			channel.handle(packet.packet, context);
		}

		public InternalPacket setPacket(BasePacket packet)
		{
			this.packet = packet;
			return this;
		}
	}

}
