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

package com.withertech.witherlib.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created 7/30/2021 by SuperMartijn642
 */
public class EnergyFormat
{

	private static EnergyType type = EnergyType.RF;

	public static void cycleEnergyType(boolean forward)
	{
		type =
				EnergyType.values()[(type.ordinal() + (forward ? 1 : EnergyType.values().length - 1)) % EnergyType.values().length];
	}

	public static String formatEnergy(int energy)
	{
		return type.convertEnergy(energy) + " " + type.unit;
	}

	public static String formatEnergyPerTick(int energy)
	{
		return type.convertEnergy(energy) + " " + type.unit + "/t";
	}

	public static String formatCapacity(int energy, int capacity)
	{
		return type.convertEnergy(energy) + " / " + type.convertEnergy(capacity) + " " + type.unit;
	}

	private enum EnergyType
	{
		RF("RF")/*, MJ("MJ")*/, FE("FE");

		private final String unit;

		EnergyType(String unit)
		{
			this.unit = unit;
		}

		public String getUnit()
		{
			return this.unit;
		}

		public String convertEnergy(int energy)
		{
			return NumberFormat.getNumberInstance(Locale.getDefault()).format(energy);
		}
	}
}
