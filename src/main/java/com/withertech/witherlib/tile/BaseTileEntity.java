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

import com.withertech.witherlib.util.SyncVariable;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import javax.annotation.Nonnull;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class BaseTileEntity<T extends BaseTileEntity<T>> extends TileEntity
{
    private boolean dataChanged = false;

    public BaseTileEntity(TileEntityType<T> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
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
     * The stored data will be read in {@link #readData(CompoundNBT)}.
     *
     * @return a {@link CompoundNBT} with the stored data
     */
    protected CompoundNBT writeData()
    {
        return SyncVariable.Helper.writeSyncVars(getClass(), this, new CompoundNBT(), SyncVariable.Type.WRITE);
    }

    /**
     * Writes tile entity data to be sent to the client.
     * The stored data will be read in {@link #readData(CompoundNBT)}.
     *
     * @return a {@link CompoundNBT} with the stored client data
     */
    protected CompoundNBT writeClientData()
    {
        return SyncVariable.Helper.writeSyncVars(getClass(), this, new CompoundNBT(), SyncVariable.Type.PACKET);
    }

    /**
     * Writes tile entity data to be stored on item stacks.
     * The stored data will be read in {@link #readData(CompoundNBT)}.
     *
     * @return a {@link CompoundNBT} with the stored item stack data
     */
    public CompoundNBT writeItemStackData()
    {
        return this.writeData();
    }

    /**
     * Reads data stored by {@link #writeData()}, {@link #writeClientData()},
     * and {@link #writeItemStackData()}.
     *
     * @param tag data to be read
     */
    public void readData(CompoundNBT tag)
    {
        SyncVariable.Helper.readSyncVars(getClass(), this, tag);
    }

    @Nonnull
    @Override
    public CompoundNBT save(@Nonnull CompoundNBT compound)
    {
        super.save(compound);
        CompoundNBT data = this.writeData();
        if (data != null && !data.isEmpty())
        {
            compound.put("data", data);
        }
        return compound;
    }

    @Override
    public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt)
    {
        super.load(state, nbt);
        this.readData(nbt.getCompound("data"));
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag()
    {
        CompoundNBT tag = super.save(new CompoundNBT());
        CompoundNBT data = this.writeClientData();
        if (data != null && !data.isEmpty())
        {
            tag.put("data", data);
        }
        return tag;
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag)
    {
        super.load(state, tag);
        this.readData(tag.getCompound("data"));
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        if (this.dataChanged)
        {
            this.dataChanged = false;
            return new SUpdateTileEntityPacket(this.worldPosition, 0, this.writeClientData());
        }
        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        this.readData(pkt.getTag());
    }
}
