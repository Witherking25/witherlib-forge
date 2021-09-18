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

package com.withertech.witherlibtest.fluids;

import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlibtest.WitherLibTest;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public abstract class TestFluid extends ForgeFlowingFluid
{
    private static final Properties properties = new Properties(
            () -> WitherLibTest.INSTANCE.REGISTRY.getFluid(TypedRegKey.fluid("test_fluid", Source.class)).get(),
            () -> WitherLibTest.INSTANCE.REGISTRY.getFluid(TypedRegKey.fluid("test_fluid_flowing", Flowing.class)).get(),
            FluidAttributes.builder(
                    WitherLibTest.INSTANCE.MOD.modLocation("block/test_fluid_still"),
                    WitherLibTest.INSTANCE.MOD.modLocation("block/test_fluid_flow"))
                    .translationKey("block.WitherLibTest.test_fluid")
		            .sound(SoundEvents.BUCKET_FILL, SoundEvents.BUCKET_EMPTY))
            .bucket(() -> WitherLibTest.INSTANCE.REGISTRY.getItem(TypedRegKey.item("test_fluid_bucket", BucketItem.class)).get())
            .block(() -> WitherLibTest.INSTANCE.REGISTRY.getBlock(TypedRegKey.block("test_fluid", LiquidBlock.class)).get())
            .canMultiply()
            .slopeFindDistance(4)
            .tickRate(5)
            .levelDecreasePerBlock(1)
            .explosionResistance(100F);


    protected TestFluid(Properties properties)
    {
        super(properties);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(@Nonnull Level world, @Nonnull BlockPos pos, FluidState state, @Nonnull Random random)
    {
        if (!state.isSource() && !state.getValue(FALLING))
        {
            if (random.nextInt(64) == 0)
            {
                world.playLocalSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.WATER_AMBIENT, SoundSource.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
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
    public ParticleOptions getDripParticle()
    {
        return ParticleTypes.DRIPPING_WATER;
    }


    public static class Flowing extends ForgeFlowingFluid.Flowing
    {
        public Flowing()
        {
            super(properties);
        }
    }

    public static class Source extends ForgeFlowingFluid.Source
    {
        public Source()
        {
            super(properties);
        }
    }
}
