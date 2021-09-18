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

package com.withertech.witherlib.data;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;

public abstract class BuilderBlockLootTableProvider extends BlockLoot
{
	@Override
	protected abstract void addTables();

	@Nonnull
	@Override
	protected abstract Iterable<Block> getKnownBlocks();
}
