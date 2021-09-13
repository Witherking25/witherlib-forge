package com.withertech.witherlibtest.screens;

import com.withertech.witherlib.gui.TileEntityBaseContainerScreen;
import com.withertech.witherlib.gui.widget.EnergyBarWidget;
import com.withertech.witherlib.gui.widget.FluidTankWidget;
import com.withertech.witherlib.util.ClientUtils;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.containers.TestEnergyContainer;
import com.withertech.witherlibtest.network.TestEnergyTilePacket;
import com.withertech.witherlibtest.network.TestProgressTilePacket;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;

public class TestEnergyScreen extends TileEntityBaseContainerScreen<TestEnergyTile, TestEnergyContainer>
{
    public TestEnergyScreen(TestEnergyContainer screenContainer, PlayerInventory playerInventory, ITextComponent title)
    {
        super(screenContainer, playerInventory, title);
    }

    @Override
    protected int sizeX(@Nonnull TestEnergyTile tile)
    {
        return 176;
    }

    @Override
    protected int sizeY(@Nonnull TestEnergyTile tile)
    {
        return 222;
    }

    @Override
    protected void addWidgets(@Nonnull TestEnergyTile tile)
    {
        addWidget(new EnergyBarWidget(8, 16, tile.energy::get));
        addWidget(new FluidTankWidget(32, 16, tile.fluid::get, () -> WitherLibTest.INSTANCE.REGISTRY.getNet("main").sendToServer(new TestEnergyTilePacket.TestEnergyTileFluidInteractPacket(this.container.getTilePos()))));
    }
}
