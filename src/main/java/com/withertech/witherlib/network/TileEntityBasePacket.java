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

import com.withertech.witherlib.util.CoreSide;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

/**
 * Created 5/30/2021 by SuperMartijn642
 */
public abstract class TileEntityBasePacket<T extends BlockEntity> extends BlockPosBasePacket
{

	public ResourceKey<Level> dimension;

	public TileEntityBasePacket()
	{
	}

	/**
	 * Grabs the tile entity in {@code dimension} at {@code pos}.
	 *
	 * @param dimension dimension of the tile entity
	 * @param pos       position of the tile entity
	 */
	public TileEntityBasePacket(ResourceKey<Level> dimension, BlockPos pos)
	{
		super(pos);
		this.dimension = dimension;
	}

	/**
	 * Grabs the tile entity in {@code world} at {@code pos}.
	 *
	 * @param world world the tile entity is in
	 * @param pos   position of the tile entity
	 */
	public TileEntityBasePacket(Level world, BlockPos pos)
	{
		this(world == null ? null : world.dimension(), pos);
	}

	/**
	 * Grabs the tile entity at {@code pos} in the relevant player's dimension.
	 *
	 * @param pos position of the tile entity
	 */
	public TileEntityBasePacket(BlockPos pos)
	{
		this((ResourceKey<Level>) null, pos);
	}

	@Override
	public void write(FriendlyByteBuf buffer)
	{
		super.write(buffer);
		buffer.writeBoolean(this.dimension != null);
		if (this.dimension != null)
		{
			buffer.writeResourceLocation(this.dimension.location());
		}
	}

	@Override
	public void read(FriendlyByteBuf buffer)
	{
		super.read(buffer);
		if (buffer.readBoolean())
		{
			this.dimension = ResourceKey.create(Registry.DIMENSION_REGISTRY, buffer.readResourceLocation());
		}
	}

	@Override
	protected void handle(BlockPos pos, PacketContext context)
	{
		T tile = this.getTileEntity(context);
		if (tile != null)
		{
			this.handle(tile, context);
		}
	}

	protected abstract void handle(T tile, PacketContext context);

	@Override
	public boolean verify(PacketContext context)
	{
		return getTileEntity(context) != null;
	}

	@SuppressWarnings("unchecked")
	private T getTileEntity(PacketContext context)
	{
		Level world = this.dimension == null ? context.getWorld() :
				context.getHandlingSide() == CoreSide.CLIENT ?
						context.getWorld().dimension() == this.dimension ? context.getWorld() : null :
						Objects.requireNonNull(context.getWorld().getServer()).getLevel(this.dimension);

		if (world == null)
		{
			return null;
		}

		BlockEntity tile = world.getBlockEntity(this.pos);

		if (tile == null)
		{
			return null;
		}

		try
		{
			return (T) tile;
		} catch (ClassCastException ignore)
		{
		}
		return null;
	}
}
