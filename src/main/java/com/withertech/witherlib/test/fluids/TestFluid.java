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

package com.withertech.witherlib.test.fluids;

import com.withertech.witherlib.WitherLib;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public abstract class TestFluid extends FlowingFluid
{
    @Override
    @Nonnull
    public Fluid getFlowing()
    {
        return WitherLib.INSTANCE.REGISTRY.getFluid("test_fluid_flowing").get();
    }

    @Override
    @Nonnull
    public Fluid getSource()
    {
        return WitherLib.INSTANCE.REGISTRY.getFluid("test_fluid").get();
    }

    @Override
    @Nonnull
    public Item getBucket()
    {
        return WitherLib.INSTANCE.REGISTRY.getItem("test_fluid_bucket").get();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull World world, @Nonnull BlockPos pos, FluidState state, @Nonnull Random random)
    {
        if (!state.isSource() && !state.getValue(FALLING))
        {
            if (random.nextInt(64) == 0)
            {
                world.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        }
        else if (random.nextInt(10) == 0)
        {
            world.addParticle(ParticleTypes.UNDERWATER, (double) pos.getX() + random.nextDouble(), (double) pos.getY() + random.nextDouble(), (double) pos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
        }

    }

    @Override
    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IParticleData getDripParticle()
    {
        return ParticleTypes.DRIPPING_WATER;
    }

    @Override
    protected boolean canConvertToSource()
    {
        return true;
    }

    @Override
    protected void beforeDestroyingBlock(@Nonnull IWorld world, @Nonnull BlockPos pos, BlockState state)
    {
        TileEntity tileentity = state.hasTileEntity() ? world.getBlockEntity(pos) : null;
        Block.dropResources(state, world, pos, tileentity);
    }

    @Override
    public int getSlopeFindDistance(@Nonnull IWorldReader world)
    {
        return 4;
    }

    @Override
    @Nonnull
    public BlockState createLegacyBlock(@Nonnull FluidState state)
    {
        return WitherLib.INSTANCE.REGISTRY.getBlock("test_fluid").get().defaultBlockState().setValue(FlowingFluidBlock.LEVEL, getLegacyLevel(state));
    }

    public boolean isSame(@Nonnull Fluid fluid)
    {
        return fluid == WitherLib.INSTANCE.REGISTRY.getFluid("test_fluid").get() || fluid == WitherLib.INSTANCE.REGISTRY.getFluid("test_fluid_flowing").get();
    }

    public int getDropOff(@Nonnull IWorldReader world)
    {
        return 1;
    }

    public int getTickDelay(@Nonnull IWorldReader world)
    {
        return 5;
    }

    public boolean canBeReplacedWith(@Nonnull FluidState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull Fluid fluid, @Nonnull Direction direction)
    {
        return direction == Direction.DOWN && !fluid.is(WitherLib.INSTANCE.REGISTRY.getFluidTag("test_fluid"));
    }

    protected float getExplosionResistance()
    {
        return 100.0F;
    }

    @Override
    @Nonnull
    protected FluidAttributes createAttributes()
    {
        return FluidAttributes.builder(WitherLib.INSTANCE.MOD.modLocation("block/test_fluid_still"), WitherLib.INSTANCE.MOD.modLocation("block/test_fluid_flow"))
                .translationKey("block.witherlib.test_fluid")
                .build(this);
    }

    public static class Flowing extends TestFluid
    {
        protected void createFluidStateDefinition(@Nonnull StateContainer.Builder<Fluid, FluidState> stateBuilder)
        {
            super.createFluidStateDefinition(stateBuilder);
            stateBuilder.add(LEVEL);
        }

        public int getAmount(FluidState state)
        {
            return state.getValue(LEVEL);
        }

        public boolean isSource(@Nonnull FluidState state)
        {
            return false;
        }
    }

    public static class Source extends TestFluid
    {
        public int getAmount(@Nonnull FluidState state)
        {
            return 8;
        }

        public boolean isSource(@Nonnull FluidState state)
        {
            return true;
        }
    }
}
