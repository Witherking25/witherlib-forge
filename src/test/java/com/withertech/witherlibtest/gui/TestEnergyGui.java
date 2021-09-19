package com.withertech.witherlibtest.gui;

import com.withertech.witherlib.gui.TileGui;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.blocks.TestEnergyBlock;
import com.withertech.witherlibtest.containers.TestEnergyContainer;
import com.withertech.witherlibtest.screens.TestEnergyScreen;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fmllegacy.RegistryObject;

public class TestEnergyGui extends TileGui<TestEnergyBlock, TestEnergyTile, TestEnergyContainer, TestEnergyScreen>
{
    @Override
    protected RegistryObject<TestEnergyBlock> registerBlock()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_energy_block", TestEnergyBlock.class));
    }

    @Override
    protected RegistryObject<BlockEntityType<TestEnergyTile>> registerTile()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.baseTile("test_energy_tile", TestEnergyTile.class));
    }

    @Override
    protected RegistryObject<MenuType<TestEnergyContainer>> registerContainer()
    {
        return WitherLibTest.INSTANCE.REGISTRY.getContainer(TypedRegKey.baseContainer("test_energy_container", TestEnergyContainer.class));
    }

    @Override
    protected MenuScreens.ScreenConstructor<TestEnergyContainer, TestEnergyScreen> registerScreen()
    {
        return TestEnergyScreen::new;
    }
}
