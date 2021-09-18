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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public class ClientUtils
{

	public static Minecraft getMinecraft()
	{
		return Minecraft.getInstance();
	}

	public static TextureManager getTextureManager()
	{
		return getMinecraft().getTextureManager();
	}

	public static Font getFont()
	{
		return getMinecraft().font;
	}

	public static Player getPlayer()
	{
		return getMinecraft().player;
	}

	public static Level getWorld()
	{
		return getMinecraft().level;
	}

	public static BlockRenderDispatcher getBlockRenderer()
	{
		return getMinecraft().getBlockRenderer();
	}

	public static ItemRenderer getItemRenderer()
	{
		return getMinecraft().getItemRenderer();
	}

	public static float getPartialTicks()
	{
		return getMinecraft().getFrameTime();
	}

	/**
	 * Closes the player's opened screen
	 */
	public static void closeScreen()
	{
		getPlayer().closeContainer();
	}

	/**
	 * Queues the given task on the main thread
	 *
	 * @param task task to be queued
	 */
	public static void queueTask(Runnable task)
	{
		getMinecraft().tell(task);
	}

	public static String translate(String translationKey, Object... args)
	{
		return I18n.get(translationKey, args);
	}

	public static void displayScreen(Screen screen)
	{
		getMinecraft().setScreen(screen);
	}

}
