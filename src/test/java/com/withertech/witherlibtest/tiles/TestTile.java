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

package com.withertech.witherlibtest.tiles;

import com.withertech.witherlib.nbt.SyncVariable;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlibtest.WitherLibTest;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TestTile extends BaseTileEntity<TestTile>
{
    @SyncVariable(name = "Items")
    public final LazyOptional<ItemStackHandler> items = LazyOptional.of(this::createHandler);

    public TestTile()
    {
        super(WitherLibTest.INSTANCE.REGISTRY.getTile((TypedRegKey.baseTile("test_tile", TestTile.class))).get());
    }

    @OnlyIn(Dist.CLIENT)
    public boolean shouldRenderFace(Direction face)
    {
        return Block.shouldRenderFace(this.getBlockState(), Objects.requireNonNull(this.level), this.getBlockPos(), face);
    }

    @Nonnull
    private ItemStackHandler createHandler()
    {
        return new ItemStackHandler(54)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                dataChanged();
            }

        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
        {
            return items.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps()
    {
        super.invalidateCaps();
        items.invalidate();
    }
}
