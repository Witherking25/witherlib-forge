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

package com.withertech.witherlibtest.tiles;

import com.withertech.witherlib.nbt.SyncVariable;
import com.withertech.witherlib.registration.TypedRegKey;
import com.withertech.witherlib.tile.MachineTileEntity;
import com.withertech.witherlibtest.WitherLibTest;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class TestProgressTile extends MachineTileEntity<TestProgressTile>
{
    @SyncVariable(name = "running")
    private boolean running = false;

    public TestProgressTile(BlockPos pos, BlockState state)
    {
        super(WitherLibTest.INSTANCE.REGISTRY.getTile(TypedRegKey.tile("test_progress_tile", TestProgressTile.class)).get(), pos, state);
    }

    public boolean isRunning()
    {
        return running;
    }

    public void toggleRunning()
    {
        running = !running;
    }

    @Override
    public boolean canMachineRun()
    {
        return running;
    }

    @Override
    public void onStart()
    {

    }

    @Override
    public void onTick(int progress)
    {

    }

    @Override
    public void onFinish()
    {

    }

    @Override
    public int getMaxProgress()
    {
        return 100;
    }
}
