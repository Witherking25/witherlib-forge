package com.withertech.witherlibtest.tiles;

import com.withertech.witherlib.nbt.SyncVariable;
import com.withertech.witherlib.nbt.wrappers.EnergyStorageNBTWrapper;
import com.withertech.witherlib.nbt.wrappers.FluidTankNBTWrapper;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.tile.BaseTileEntity;
import com.withertech.witherlibtest.WitherLibTest;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TestEnergyTile extends BaseTileEntity<TestEnergyTile>
{
    @SyncVariable(name = "Energy")
    public final EnergyStorageNBTWrapper energy = new EnergyStorageNBTWrapper(new EnergyStorage(1000, 10)
    {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate)
        {
            dataChanged();
            return super.receiveEnergy(maxReceive, simulate);
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate)
        {
            dataChanged();
            return super.extractEnergy(maxExtract, simulate);
        }
    });

    @SyncVariable(name = "Fluid")
    public final FluidTankNBTWrapper fluid = new FluidTankNBTWrapper(new FluidTank(10000)
    {
        @Override
        protected void onContentsChanged()
        {
            dataChanged();
        }
    });

    public TestEnergyTile()
    {
        super(WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.baseTile("test_energy_tile", TestEnergyTile.class)).get());
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
    {
        if (cap == CapabilityEnergy.ENERGY)
        {
            return LazyOptional.of(energy::get).cast();
        }
        else if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
        {
            return LazyOptional.of(fluid::get).cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps()
    {
        super.invalidateCaps();
    }
}
