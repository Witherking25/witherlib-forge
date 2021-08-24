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
import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.registration.TypedRegKey;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class TestContainer extends TileEntityBaseContainer<TestTileEntity>
{
    public TestContainer(int id, PlayerEntity player, BlockPos pos)
    {
        super(WitherLib.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test", TestContainer.class)).get(), id, player, pos);
        addSlots();
    }

    @Override
    protected void addSlots(PlayerEntity player, @Nonnull TestTileEntity object)
    {
        addSlot(new SlotItemHandler(object.items, 0, 20, 50));
        addPlayerSlots(20, 100);
    }

    @Override
    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_)
    {
        return super.quickMoveStack(p_82846_1_, p_82846_2_);
    }
}
