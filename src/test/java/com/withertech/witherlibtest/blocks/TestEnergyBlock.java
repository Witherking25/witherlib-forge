package com.withertech.witherlibtest.blocks;

import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.containers.TestEnergyContainer;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;

public class TestEnergyBlock extends BaseTileBlock<TestEnergyTile>
{
    public TestEnergyBlock()
    {
        super(true, Properties.of(Material.STONE));
    }

    @Override
    protected boolean hasContainer()
    {
        return true;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Player player, BlockPos pos)
    {
        return new TestEnergyContainer(id, player, pos);
    }

    @Override
    protected Component getDisplayName(TestEnergyTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Override
    public BlockEntityType<TestEnergyTile> getBlockEntityType()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.baseTile("test_energy_tile", TestEnergyTile.class)).get();
    }
}
