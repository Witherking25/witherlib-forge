package com.withertech.witherlibtest.containers;

import com.withertech.witherlib.gui.TileEntityBaseContainer;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TestEnergyContainer extends TileEntityBaseContainer<TestEnergyContainer, TestEnergyTile>
{
    public TestEnergyContainer(int id, Player player, BlockPos pos)
    {
        super(WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.container("test_energy_container", TestEnergyContainer.class)).get(), id, player, pos);
        addSlots();
    }

    @Override
    protected void addSlots(Player player, @Nonnull TestEnergyTile object)
    {
        addPlayerSlots(8, 140);
    }

    @Nonnull
    @Override
    public ItemStack quickMoveStack(@Nonnull Player p_82846_1_, int p_82846_2_)
    {
        return ItemStack.EMPTY;
    }
}
