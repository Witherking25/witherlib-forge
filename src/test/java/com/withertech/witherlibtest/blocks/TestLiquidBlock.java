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

package com.withertech.witherlibtest.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Forge's LiquidBlock has incomplete patches, and causes a NullPointerException, so this class is temporary until they fix it
 */
public class TestLiquidBlock extends LiquidBlock
{
	public TestLiquidBlock(Supplier<? extends FlowingFluid> p_54694_, Properties p_54695_)
	{
		super(p_54694_, p_54695_);
	}

	@Nonnull
	@Override
	public VoxelShape getCollisionShape(@Nonnull BlockState p_54760_, @Nonnull BlockGetter p_54761_, @Nonnull BlockPos p_54762_, CollisionContext p_54763_)
	{
		return p_54763_.isAbove(STABLE_SHAPE, p_54762_, true) && p_54760_.getValue(LEVEL) == 0 && p_54763_.canStandOnFluid(p_54761_.getFluidState(p_54762_.above()), this.getFluid()) ? STABLE_SHAPE : Shapes.empty();
	}

	@Override
	public boolean isPathfindable(@Nonnull BlockState p_54704_, @Nonnull BlockGetter p_54705_, @Nonnull BlockPos p_54706_, @Nonnull PathComputationType p_54707_)
	{
		return !this.getFluid().is(FluidTags.LAVA);
	}

	@Override
	public boolean skipRendering(@Nonnull BlockState p_54716_, BlockState p_54717_, @Nonnull Direction p_54718_)
	{
		return p_54717_.getFluidState().getType().isSame(this.getFluid());
	}

	@Override
	public void onPlace(BlockState p_54754_, Level p_54755_, BlockPos p_54756_, BlockState p_54757_, boolean p_54758_)
	{
		if (this.shouldSpreadLiquid(p_54755_, p_54756_, p_54754_)) {
			p_54755_.getLiquidTicks().scheduleTick(p_54756_, p_54754_.getFluidState().getType(), this.getFluid().getTickDelay(p_54755_));
		}
	}

	@Nonnull
	@Override
	public BlockState updateShape(@Nonnull BlockState p_54723_, @Nonnull Direction p_54724_, @Nonnull BlockState p_54725_, @Nonnull LevelAccessor p_54726_, @Nonnull BlockPos p_54727_, @Nonnull BlockPos p_54728_)
	{
		if (p_54723_.getFluidState().isSource() || p_54725_.getFluidState().isSource()) {
			p_54726_.getLiquidTicks().scheduleTick(p_54727_, p_54723_.getFluidState().getType(), this.getFluid().getTickDelay(p_54726_));
		}

		return p_54723_;
	}

	@Override
	public void neighborChanged(BlockState p_54709_, Level p_54710_, BlockPos p_54711_, Block p_54712_, BlockPos p_54713_, boolean p_54714_)
	{
		if (this.shouldSpreadLiquid(p_54710_, p_54711_, p_54709_)) {
			p_54710_.getLiquidTicks().scheduleTick(p_54711_, p_54709_.getFluidState().getType(), this.getFluid().getTickDelay(p_54710_));
		}
	}

	private boolean shouldSpreadLiquid(Level p_54697_, BlockPos p_54698_, BlockState p_54699_) {
		if (this.getFluid().is(FluidTags.LAVA)) {
			boolean flag = p_54697_.getBlockState(p_54698_.below()).is(Blocks.SOUL_SOIL);

			for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
				BlockPos blockpos = p_54698_.relative(direction.getOpposite());
				if (p_54697_.getFluidState(blockpos).is(FluidTags.WATER)) {
					Block block = p_54697_.getFluidState(p_54698_).isSource() ? Blocks.OBSIDIAN : Blocks.COBBLESTONE;
					p_54697_.setBlockAndUpdate(p_54698_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_54697_, p_54698_, p_54698_, block.defaultBlockState()));
					this.fizz(p_54697_, p_54698_);
					return false;
				}

				if (flag && p_54697_.getBlockState(blockpos).is(Blocks.BLUE_ICE)) {
					p_54697_.setBlockAndUpdate(p_54698_, net.minecraftforge.event.ForgeEventFactory.fireFluidPlaceBlockEvent(p_54697_, p_54698_, p_54698_, Blocks.BASALT.defaultBlockState()));
					this.fizz(p_54697_, p_54698_);
					return false;
				}
			}
		}

		return true;
	}

	private void fizz(@Nonnull LevelAccessor p_54701_, BlockPos p_54702_) {
		p_54701_.levelEvent(1501, p_54702_, 0);
	}

	@Nonnull
	@Override
	public ItemStack pickupBlock(@Nonnull LevelAccessor p_153772_, @Nonnull BlockPos p_153773_, BlockState p_153774_)
	{
		if (p_153774_.getValue(LEVEL) == 0) {
			p_153772_.setBlock(p_153773_, Blocks.AIR.defaultBlockState(), 11);
			return new ItemStack(this.getFluid().getBucket());
		} else {
			return ItemStack.EMPTY;
		}
	}

	@Nonnull
	@Override
	public Optional<SoundEvent> getPickupSound()
	{
		return this.getFluid().getPickupSound();
	}
}
