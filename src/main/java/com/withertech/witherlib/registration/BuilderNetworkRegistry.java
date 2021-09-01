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
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BuilderNetworkRegistry
{
    private final Map<String, PacketChannel> CHANNELS;
    private final Map<String, List<Channel.Packet<?>>> PACKETS;

    private BuilderNetworkRegistry(Builder builder)
    {
        CHANNELS = builder.CHANNELS;
        PACKETS = builder.PACKETS;
    }

    public Map<String, PacketChannel> getCHANNELS()
    {
        return CHANNELS;
    }

    public Map<String, List<Channel.Packet<?>>> getPACKETS()
    {
        return PACKETS;
    }

    public static Builder builder(ModData mod)
    {
        return new Builder(mod);
    }

    public static Channel.ChannelBuilder channel()
    {
        return new Channel.ChannelBuilder();
    }

    public static class Builder
    {
        private final Map<String, PacketChannel> CHANNELS = new HashMap<>();
        private final Map<String, List<Channel.Packet<?>>> PACKETS = new HashMap<>();
        private final ModData mod;

        private Builder(ModData mod)
        {
            this.mod = mod;
        }

        public Builder add(String name, Channel channel)
        {
            CHANNELS.put(name, PacketChannel.create(mod.MODID, name));
            PACKETS.put(name, channel.PACKETS);
            return this;
        }

        public Builder add(Channel channel)
        {
            return add("main", channel);
        }

        public BuilderNetworkRegistry build()
        {
            return new BuilderNetworkRegistry(this);
        }


    }

    public static class Channel
    {
        private final List<Packet<?>> PACKETS;

        private Channel(ChannelBuilder builder)
        {
            PACKETS = builder.PACKETS;
        }

        public static class ChannelBuilder
        {
            private final List<Packet<?>> PACKETS = new ArrayList<>();

            private ChannelBuilder()
            {

            }

            public <T extends BasePacket> ChannelBuilder add(Class<T> packetClass, Supplier<T> packetSupplier, boolean shouldBeQueued)
            {
                PACKETS.add(new Packet<>(packetClass, packetSupplier, shouldBeQueued));
                return this;
            }

            public Channel build()
            {
                return new Channel(this);
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
