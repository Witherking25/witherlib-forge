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

package com.withertech.witherlib.registration;

import com.withertech.witherlib.network.BasePacket;
import com.withertech.witherlib.network.PacketChannel;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderNetworkRegistry
{
	private final Map<String, Channel> channels;

	private BuilderNetworkRegistry(Builder builder)
	{
		channels = builder.channels;
	}

	public static Builder builder(ModData mod)
	{
		return new Builder(mod);
	}

	public boolean containsKey(String key)
	{
		return channels.containsKey(key);
	}

	public Map<String, Channel> getChannels()
	{
		return channels;
	}

	public void register()
	{
		getChannels().forEach((id, channel) ->
				channel.getPackets().forEach(packet ->
						registerMessage(
								channel.getChannel(),
								packet.getPacketClass(),
								packet.getPacketSupplier(),
								packet.isShouldBeQueued()
						)
				)
		);
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private void registerMessage(@Nonnull PacketChannel channel, Class type, Supplier supplier, boolean queue)
	{
		channel.registerMessage(type, supplier, queue);
	}

	public static class Builder
	{
		private final Map<String, Channel> channels = new HashMap<>();
		private final ModData mod;

		private Builder(ModData mod)
		{
			this.mod = mod;
		}

		public Channel.ChannelBuilder channel(String name)
		{
			return new Channel.ChannelBuilder(name, PacketChannel.create(mod.MODID, name), this);
		}

		public Channel.ChannelBuilder channel()
		{
			return channel("main");
		}

		public BuilderNetworkRegistry build()
		{
			return new BuilderNetworkRegistry(this);
		}


	}

	public static class Channel
	{

		private final List<Packet<?>> packets;

		private final PacketChannel channel;

		private Channel(ChannelBuilder builder)
		{
			packets = builder.packets;
			channel = builder.channel;
		}

		public List<Packet<?>> getPackets()
		{
			return packets;
		}

		public PacketChannel getChannel()
		{
			return channel;
		}

		public static class ChannelBuilder
		{
			private final List<Packet<?>> packets = new ArrayList<>();
			private final String name;
			private final PacketChannel channel;
			private final Builder builder;

			private ChannelBuilder(String name, PacketChannel channel, Builder builder)
			{
				this.name = name;
				this.channel = channel;
				this.builder = builder;
			}

			public <T extends BasePacket> ChannelBuilder packet(
					Class<T> packetClass,
					Supplier<T> packetSupplier,
					boolean shouldBeQueued
			)
			{
				packets.add(new Packet<>(packetClass, packetSupplier, shouldBeQueued));
				return this;
			}

			public Builder build()
			{
				builder.channels.put(name, new Channel(this));
				return builder;
			}
		}

		public static class Packet<T extends BasePacket>
		{
			private final Class<T> packetClass;
			private final Supplier<T> packetSupplier;
			private final boolean shouldBeQueued;

			private Packet(Class<T> packetClass, Supplier<T> packetSupplier, boolean shouldBeQueued)
			{

				this.packetClass = packetClass;
				this.packetSupplier = packetSupplier;
				this.shouldBeQueued = shouldBeQueued;
			}

			public Class<T> getPacketClass()
			{
				return packetClass;
			}

			public Supplier<T> getPacketSupplier()
			{
				return packetSupplier;
			}

			public boolean isShouldBeQueued()
			{
				return shouldBeQueued;
			}
		}
	}
}
