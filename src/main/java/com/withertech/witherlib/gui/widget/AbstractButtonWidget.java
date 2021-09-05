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

package com.withertech.witherlib.gui.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundEvents;

/**
 * Created 10/8/2020 by SuperMartijn642
 */
public abstract class AbstractButtonWidget extends Widget
{

	private final Runnable pressable;

	/**
	 * @param onPress
	 * 		the action which will called when the user clicks the
	 * 		widget
	 */
	public AbstractButtonWidget(int x, int y, int width, int height, Runnable onPress)
	{
		super(x, y, width, height);
		this.pressable = onPress;
	}

	/**
	 * Plays the default Minecraft button sound.
	 */
	public static void playClickSound()
	{
		Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
	}

	/**
	 * Called when the user clicks the widget.
	 */
	public void onPress()
	{
		playClickSound();
		if (this.pressable != null)
		{
			this.pressable.run();
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button)
	{
		if (this.active && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height)
		{
			this.onPress();
		}
	}
}
