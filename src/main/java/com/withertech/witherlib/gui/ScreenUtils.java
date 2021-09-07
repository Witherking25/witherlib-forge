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

package com.withertech.witherlib.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.util.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;

/**
 * Created 1/20/2021 by SuperMartijn642
 */
public class ScreenUtils
{

	public static final int DEFAULT_TEXT_COLOR = 4210752, ACTIVE_TEXT_COLOR = 14737632, INACTIVE_TEXT_COLOR = 7368816;
	private static final ResourceLocation BUTTON_BACKGROUND = WitherLib.INSTANCE.MOD.modLocation(
			"textures/gui/buttons.png");
	private static final ResourceLocation SCREEN_BACKGROUND = WitherLib.INSTANCE.MOD.modLocation(
			"textures/gui/background.png");

	public static void drawString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(matrixStack, text, x, y, color);
	}

	public static void drawString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y
	)
	{
		fontRenderer.draw(matrixStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawString(MatrixStack matrixStack, ITextComponent text, float x, float y, int color)
	{
		drawString(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawString(MatrixStack matrixStack, ITextComponent text, float x, float y)
	{
		drawString(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x, y, color);
	}

	public static void drawStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawStringWithShadow(MatrixStack matrixStack, ITextComponent text, float x, float y, int color)
	{
		drawStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawStringWithShadow(MatrixStack matrixStack, ITextComponent text, float x, float y)
	{
		drawStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawCenteredString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(matrixStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y
	)
	{
		fontRenderer.draw(matrixStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredString(MatrixStack matrixStack, ITextComponent text, float x, float y, int color)
	{
		drawCenteredString(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawCenteredString(MatrixStack matrixStack, ITextComponent text, float x, float y)
	{
		drawCenteredString(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawCenteredStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			ITextComponent text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredStringWithShadow(
			MatrixStack matrixStack,
			ITextComponent text,
			float x,
			float y,
			int color
	)
	{
		drawCenteredStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawCenteredStringWithShadow(MatrixStack matrixStack, ITextComponent text, float x, float y)
	{
		drawCenteredStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(matrixStack, text, x, y, color);
	}

	public static void drawString(MatrixStack matrixStack, FontRenderer fontRenderer, String text, float x, float y)
	{
		fontRenderer.draw(matrixStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawString(MatrixStack matrixStack, String text, float x, float y, int color)
	{
		drawString(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawString(MatrixStack matrixStack, String text, float x, float y)
	{
		drawString(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawStringWithShadow(MatrixStack matrixStack, String text, float x, float y, int color)
	{
		drawStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawStringWithShadow(MatrixStack matrixStack, String text, float x, float y)
	{
		drawStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawCenteredString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(matrixStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredString(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.draw(matrixStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredString(MatrixStack matrixStack, String text, float x, float y, int color)
	{
		drawCenteredString(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawCenteredString(MatrixStack matrixStack, String text, float x, float y)
	{
		drawCenteredString(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawCenteredStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredStringWithShadow(
			MatrixStack matrixStack,
			FontRenderer fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(matrixStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredStringWithShadow(MatrixStack matrixStack, String text, float x, float y, int color)
	{
		drawCenteredStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y, color);
	}

	public static void drawCenteredStringWithShadow(MatrixStack matrixStack, String text, float x, float y)
	{
		drawCenteredStringWithShadow(matrixStack, ClientUtils.getFontRenderer(), text, x, y);
	}

	public static void drawScreenBackground(MatrixStack matrixStack, float x, float y, float width, float height)
	{
		Minecraft.getInstance().textureManager.bind(SCREEN_BACKGROUND);
		// corners
		drawTexture(matrixStack, x, y, 4, 4, 0, 0, 4 / 9f, 4 / 9f);
		drawTexture(matrixStack, x + width - 4, y, 4, 4, 5 / 9f, 0, 4 / 9f, 4 / 9f);
		drawTexture(matrixStack, x + width - 4, y + height - 4, 4, 4, 5 / 9f, 5 / 9f, 4 / 9f, 4 / 9f);
		drawTexture(matrixStack, x, y + height - 4, 4, 4, 0, 5 / 9f, 4 / 9f, 4 / 9f);
		// edges
		drawTexture(matrixStack, x + 4, y, width - 8, 4, 4 / 9f, 0, 1 / 9f, 4 / 9f);
		drawTexture(matrixStack, x + 4, y + height - 4, width - 8, 4, 4 / 9f, 5 / 9f, 1 / 9f, 4 / 9f);
		drawTexture(matrixStack, x, y + 4, 4, height - 8, 0, 4 / 9f, 4 / 9f, 1 / 9f);
		drawTexture(matrixStack, x + width - 4, y + 4, 4, height - 8, 5 / 9f, 4 / 9f, 4 / 9f, 1 / 9f);
		// center
		drawTexture(matrixStack, x + 4, y + 4, width - 8, height - 8, 4 / 9f, 4 / 9f, 1 / 9f, 1 / 9f);
	}

	public static void drawButtonBackground(
			MatrixStack matrixStack,
			float x,
			float y,
			float width,
			float height,
			float xOffset,
			float yOffset
	)
	{
		Minecraft.getInstance().getTextureManager().bind(BUTTON_BACKGROUND);
		// corners
		drawTexture(matrixStack, x, y, 2, 2, xOffset, yOffset, 2 / 10f, 2 / 15f);
		drawTexture(matrixStack, x + width - 2, y, 2, 2, xOffset + 3 / 10f, yOffset, 2 / 10f, 2 / 15f);
		drawTexture(
				matrixStack,
				x + width - 2,
				y + height - 2,
				2,
				2,
				xOffset + 3 / 10f,
				yOffset + 3 / 15f,
				2 / 10f,
				2 / 15f
		);
		drawTexture(matrixStack, x, y + height - 2, 2, 2, xOffset, yOffset + 3 / 15f, 2 / 10f, 2 / 15f);
		// edges
		drawTexture(matrixStack, x + 2, y, width - 4, 2, xOffset + 2 / 10f, yOffset, 1 / 10f, 2 / 15f);
		drawTexture(
				matrixStack,
				x + 2,
				y + height - 2,
				width - 4,
				2,
				xOffset + 2 / 10f,
				yOffset + 3 / 15f,
				1 / 10f,
				2 / 15f
		);
		drawTexture(matrixStack, x, y + 2, 2, height - 4, xOffset, yOffset + 2 / 15f, 2 / 10f, 1 / 15f);
		drawTexture(
				matrixStack,
				x + width - 2,
				y + 2,
				2,
				height - 4,
				xOffset + 3 / 10f,
				yOffset + 2 / 15f,
				2 / 10f,
				1 / 15f
		);
		// center
		drawTexture(
				matrixStack,
				x + 2,
				y + 2,
				width - 4,
				height - 4,
				xOffset + 2 / 10f,
				yOffset + 2 / 15f,
				1 / 10f,
				1 / 15f
		);
	}

	public static void drawTexture(MatrixStack matrixStack, float x, float y, float width, float height)
	{
		drawTexture(matrixStack, x, y, width, height, 0, 0, 1, 1);
	}

	public static void drawTexture(
			MatrixStack matrixStack,
			float x,
			float y,
			float width,
			float height,
			float tx,
			float ty,
			float twidth,
			float theight
	)
	{
		GlStateManager._color4f(1, 1, 1, 1);

		Matrix4f matrix = matrixStack.last().pose();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
		buffer.vertex(matrix, x, y + height, 0).uv(tx, ty + theight).endVertex();
		buffer.vertex(matrix, x + width, y + height, 0).uv(tx + twidth, ty + theight).endVertex();
		buffer.vertex(matrix, x + width, y, 0).uv(tx + twidth, ty).endVertex();
		buffer.vertex(matrix, x, y, 0).uv(tx, ty).endVertex();
		tessellator.end();
	}

	public static void fillRect(MatrixStack matrixStack, float x, float y, float width, float height, int color)
	{
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		fillRect(matrixStack, x, y, width, height, red, green, blue, alpha);
	}

	public static void fillRect(
			MatrixStack matrixStack,
			float x,
			float y,
			float width,
			float height,
			float red,
			float green,
			float blue,
			float alpha
	)
	{
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();

		Matrix4f matrix = matrixStack.last().pose();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		buffer.vertex(matrix, x, y + height, 0).color(red, green, blue, alpha).endVertex();
		buffer.vertex(matrix, x + width, y + height, 0).color(red, green, blue, alpha).endVertex();
		buffer.vertex(matrix, x + width, y, 0).color(red, green, blue, alpha).endVertex();
		buffer.vertex(matrix, x, y, 0).color(red, green, blue, alpha).endVertex();
		tessellator.end();

		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}

	public static void bindTexture(ResourceLocation location)
	{
		Minecraft.getInstance().textureManager.bind(location);
	}
}
