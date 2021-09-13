package com.withertech.witherlibtest.blocks;

import com.withertech.witherlib.block.BaseTileBlock;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.util.TextComponents;
import com.withertech.witherlibtest.WitherLibTest;
import com.withertech.witherlibtest.containers.TestEnergyContainer;
import com.withertech.witherlibtest.tiles.TestEnergyTile;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class TestEnergyBlock extends BaseTileBlock<TestEnergyTile>
{
    public TestEnergyBlock()
    {
        super(true, AbstractBlock.Properties.of(Material.STONE));
    }

    @Override
    protected boolean hasContainer()
    {
        return true;
    }

    @Override
    protected Container createMenu(int id, PlayerEntity player, BlockPos pos)
    {
        return new TestEnergyContainer(id, player, pos);
    }

    @Override
    protected ITextComponent getDisplayName(TestEnergyTile tile)
    {
        return TextComponents.block(this).get();
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_energy_tile", TestEnergyTile.class)).get().create();
    }
}
