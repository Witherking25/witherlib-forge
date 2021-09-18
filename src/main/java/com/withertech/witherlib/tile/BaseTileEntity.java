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

package com.withertech.witherlib.tile;

import com.withertech.witherlib.nbt.SyncVariable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class BaseTileEntity<T extends BaseTileEntity<T>> extends BlockEntity
{
	private boolean dataChanged = false;

	public BaseTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state)
	{
		super(tileEntityTypeIn, pos, state);
	}

	/**
	 * Marks the tile entity as dirty and send an update packet to clients.
	 */
	public void dataChanged()
	{
		this.dataChanged = true;
		this.setChanged();
		assert this.level != null;
		this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 2 | 4);
	}

	/**
	 * Writes tile entity data to be saved with the chunk.
	 * The stored data will be read in {@link #readData(CompoundTag)}.
	 *
	 * @return a {@link CompoundTag} with the stored data
	 */
	protected CompoundTag writeData()
	{
		return SyncVariable.Helper.writeSyncVars(this, new CompoundTag(), SyncVariable.Type.WRITE);
	}

	/**
	 * Writes tile entity data to be sent to the client.
	 * The stored data will be read in {@link #readData(CompoundTag)}.
	 *
	 * @return a {@link CompoundTag} with the stored client data
	 */
	protected CompoundTag writeClientData()
	{
		return SyncVariable.Helper.writeSyncVars(this, new CompoundTag(), SyncVariable.Type.PACKET);
	}

	/**
	 * Writes tile entity data to be stored on item stacks.
	 * The stored data will be read in {@link #readData(CompoundTag)}.
	 *
	 * @return a {@link CompoundTag} with the stored item stack data
	 */
	public CompoundTag writeItemStackData()
	{
		return this.writeData();
	}

	/**
	 * Reads data stored by {@link #writeData()}, {@link #writeClientData()},
	 * and {@link #writeItemStackData()}.
	 *
	 * @param tag data to be read
	 */
	public void readData(CompoundTag tag)
	{
		SyncVariable.Helper.readSyncVars(this, tag);
	}

	@Nonnull
	@Override
	public CompoundTag save(@Nonnull CompoundTag compound)
	{
		super.save(compound);
		CompoundTag data = this.writeData();
		if (data != null && !data.isEmpty())
		{
			compound.put("data", data);
		}
		return compound;
	}

	@Override
	public void load(@Nonnull CompoundTag nbt)
	{
		super.load(nbt);
		this.readData(nbt.getCompound("data"));
	}

	@Nonnull
	@Override
	public CompoundTag getUpdateTag()
	{
		CompoundTag tag = super.save(new CompoundTag());
		CompoundTag data = this.writeClientData();
		if (data != null && !data.isEmpty())
		{
			tag.put("data", data);
		}
		return tag;
	}

	@Override
	public void handleUpdateTag(CompoundTag tag)
	{
		super.load(tag);
		this.readData(tag.getCompound("data"));
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket()
	{
		if (this.dataChanged)
		{
			this.dataChanged = false;
			return new ClientboundBlockEntityDataPacket(this.worldPosition, 0, this.writeClientData());
		}
		return null;
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt)
	{
		this.readData(pkt.getTag());
	}
}
