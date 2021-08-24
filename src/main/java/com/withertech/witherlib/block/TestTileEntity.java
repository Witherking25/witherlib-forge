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

package com.withertech.witherlib.block;

import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.SyncVariable;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.LockableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestTileEntity extends BaseTileEntity
{
    @SyncVariable(name = "Test")
    public boolean test = true;

    @SyncVariable(name = "Items")
    public final ItemStackHandler items = new ItemStackHandler();
    public final LazyOptional<ItemStackHandler> itemHandler = LazyOptional.of(() -> items);

    public TestTileEntity()
    {
        super(WitherLib.INSTANCE.REGISTRY.TILES.get().get(TypedRegKey.tile("test", TestTileEntity.class)).get());
    }

    @Override
    public ITextComponent getDisplayName()
    {
        return TextComponents.string("Test").get();
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity player)
    {
        return WitherLib.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test", TestContainer.class)).get().create(id, playerInventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps()
    {
        super.invalidateCaps();
        itemHandler.invalidate();
    }
}
