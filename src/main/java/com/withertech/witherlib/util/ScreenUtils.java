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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.util.ClientUtils;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;

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
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(poseStack, text, x, y, color);
	}

	public static void drawString(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y
	)
	{
		fontRenderer.draw(poseStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawString(PoseStack poseStack, Component text, float x, float y, int color)
	{
		drawString(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawString(PoseStack poseStack, Component text, float x, float y)
	{
		drawString(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(poseStack, text, x, y, color);
	}

	public static void drawStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(poseStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawStringWithShadow(PoseStack poseStack, Component text, float x, float y, int color)
	{
		drawStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawStringWithShadow(PoseStack poseStack, Component text, float x, float y)
	{
		drawStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawCenteredString(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(poseStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredString(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y
	)
	{
		fontRenderer.draw(poseStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredString(PoseStack poseStack, Component text, float x, float y, int color)
	{
		drawCenteredString(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawCenteredString(PoseStack poseStack, Component text, float x, float y)
	{
		drawCenteredString(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawCenteredStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			Component text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredStringWithShadow(
			PoseStack poseStack,
			Component text,
			float x,
			float y,
			int color
	)
	{
		drawCenteredStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawCenteredStringWithShadow(PoseStack poseStack, Component text, float x, float y)
	{
		drawCenteredStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawString(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(poseStack, text, x, y, color);
	}

	public static void drawString(PoseStack poseStack, Font fontRenderer, String text, float x, float y)
	{
		fontRenderer.draw(poseStack, text, x, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawString(PoseStack poseStack, String text, float x, float y, int color)
	{
		drawString(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawString(PoseStack poseStack, String text, float x, float y)
	{
		drawString(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawStringWithShadow(PoseStack poseStack, String text, float x, float y, int color)
	{
		drawStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawStringWithShadow(PoseStack poseStack, String text, float x, float y)
	{
		drawStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawCenteredString(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.draw(poseStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredString(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.draw(poseStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredString(PoseStack poseStack, String text, float x, float y, int color)
	{
		drawCenteredString(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawCenteredString(PoseStack poseStack, String text, float x, float y)
	{
		drawCenteredString(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawCenteredStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y,
			int color
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, color);
	}

	public static void drawCenteredStringWithShadow(
			PoseStack poseStack,
			Font fontRenderer,
			String text,
			float x,
			float y
	)
	{
		fontRenderer.drawShadow(poseStack, text, x - fontRenderer.width(text) / 2f, y, DEFAULT_TEXT_COLOR);
	}

	public static void drawCenteredStringWithShadow(PoseStack poseStack, String text, float x, float y, int color)
	{
		drawCenteredStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y, color);
	}

	public static void drawCenteredStringWithShadow(PoseStack poseStack, String text, float x, float y)
	{
		drawCenteredStringWithShadow(poseStack, ClientUtils.getFont(), text, x, y);
	}

	public static void drawScreenBackground(PoseStack poseStack, float x, float y, float width, float height)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		bindTexture(SCREEN_BACKGROUND);
		// corners
		drawTexture(poseStack, x, y, 4, 4, 0, 0, 4 / 9f, 4 / 9f);
		drawTexture(poseStack, x + width - 4, y, 4, 4, 5 / 9f, 0, 4 / 9f, 4 / 9f);
		drawTexture(poseStack, x + width - 4, y + height - 4, 4, 4, 5 / 9f, 5 / 9f, 4 / 9f, 4 / 9f);
		drawTexture(poseStack, x, y + height - 4, 4, 4, 0, 5 / 9f, 4 / 9f, 4 / 9f);
		// edges
		drawTexture(poseStack, x + 4, y, width - 8, 4, 4 / 9f, 0, 1 / 9f, 4 / 9f);
		drawTexture(poseStack, x + 4, y + height - 4, width - 8, 4, 4 / 9f, 5 / 9f, 1 / 9f, 4 / 9f);
		drawTexture(poseStack, x, y + 4, 4, height - 8, 0, 4 / 9f, 4 / 9f, 1 / 9f);
		drawTexture(poseStack, x + width - 4, y + 4, 4, height - 8, 5 / 9f, 4 / 9f, 4 / 9f, 1 / 9f);
		// center
		drawTexture(poseStack, x + 4, y + 4, width - 8, height - 8, 4 / 9f, 4 / 9f, 1 / 9f, 1 / 9f);
	}

	public static void drawButtonBackground(
			PoseStack poseStack,
			float x,
			float y,
			float width,
			float height,
			float xOffset,
			float yOffset
	)
	{
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		bindTexture(BUTTON_BACKGROUND);
		// corners
		drawTexture(poseStack, x, y, 2, 2, xOffset, yOffset, 2 / 10f, 2 / 15f);
		drawTexture(poseStack, x + width - 2, y, 2, 2, xOffset + 3 / 10f, yOffset, 2 / 10f, 2 / 15f);
		drawTexture(
				poseStack,
				x + width - 2,
				y + height - 2,
				2,
				2,
				xOffset + 3 / 10f,
				yOffset + 3 / 15f,
				2 / 10f,
				2 / 15f
		);
		drawTexture(poseStack, x, y + height - 2, 2, 2, xOffset, yOffset + 3 / 15f, 2 / 10f, 2 / 15f);
		// edges
		drawTexture(poseStack, x + 2, y, width - 4, 2, xOffset + 2 / 10f, yOffset, 1 / 10f, 2 / 15f);
		drawTexture(
				poseStack,
				x + 2,
				y + height - 2,
				width - 4,
				2,
				xOffset + 2 / 10f,
				yOffset + 3 / 15f,
				1 / 10f,
				2 / 15f
		);
		drawTexture(poseStack, x, y + 2, 2, height - 4, xOffset, yOffset + 2 / 15f, 2 / 10f, 1 / 15f);
		drawTexture(
				poseStack,
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
				poseStack,
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

	private static void drawRect(PoseStack poseStack, float x, float y, float width, float height, float z, float u, float v, float maxU, float maxV)
	{
		Matrix4f matrix = poseStack.last().pose();
		BufferBuilder buffer = Tesselator.getInstance().getBuilder();

		buffer.vertex(matrix, x, y + height, z).uv(u, maxV).endVertex();
		buffer.vertex(matrix, x + width, y + height, z).uv(maxU, maxV).endVertex();
		buffer.vertex(matrix, x + width, y, z).uv(maxU, v).endVertex();
		buffer.vertex(matrix, x, y, z).uv(u, v).endVertex();
	}

	public static void fillAreaWithSprite(PoseStack poseStack, @Nonnull TextureAtlasSprite atlasSprite, int x, int y, int width, int height)
	{
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

		float zLevel = 0;

		int iconWidth = atlasSprite.getWidth();
		int iconHeight = atlasSprite.getHeight();

		// number of rows & cols of full size icons
		int fullCols = width / iconWidth;
		int fullRows = height / iconHeight;

		float minU = atlasSprite.getU0();
		float maxU = atlasSprite.getU1();
		float minV = atlasSprite.getV0();
		float maxV = atlasSprite.getV1();

		int excessWidth = width % iconWidth;
		int excessHeight = height % iconHeight;

		// interpolated max u/v for the excess row / col
		float partialMaxU = minU + (maxU - minU) * ((float) excessWidth / iconWidth);
		float partialMaxV = minV + (maxV - minV) * ((float) excessHeight / iconHeight);

		int xNow;
		int yNow;
		for (int row = 0; row < fullRows; row++)
		{
			yNow = y + row * iconHeight;
			for (int col = 0; col < fullCols; col++)
			{
				// main part, only full icons
				xNow = x + col * iconWidth;
				drawRect(poseStack, xNow, yNow, iconWidth, iconHeight, zLevel, minU, minV, maxU, maxV);
			}
			if (excessWidth != 0)
			{
				// last not full width column in every row at the end
				xNow = x + fullCols * iconWidth;
				drawRect(poseStack, xNow, yNow, excessWidth, iconHeight, zLevel, minU, minV, partialMaxU, maxV);
			}
		}
		if (excessHeight != 0)
		{
			// last not full height row
			for (int col = 0; col < fullCols; col++)
			{
				xNow = x + col * iconWidth;
				yNow = y + fullRows * iconHeight;
				drawRect(poseStack, xNow, yNow, iconWidth, excessHeight, zLevel, minU, minV, maxU, partialMaxV);
			}
			if (excessWidth != 0)
			{
				// missing quad in the bottom right corner of neither full height nor full width
				xNow = x + fullCols * iconWidth;
				yNow = y + fullRows * iconHeight;
				drawRect(poseStack, xNow, yNow, excessWidth, excessHeight, zLevel, minU, minV, partialMaxU, partialMaxV);
			}
		}

		tessellator.end();
	}

	public static void drawFluidStack(PoseStack poseStack, @Nullable FluidStack fluidStack, int tankCapacity, int x, int y, int width, int fullHeight)
	{

		if (fluidStack != null)
		{
			Fluid fluid = fluidStack.getFluid();
			TextureAtlasSprite fluidAtlasSprite = ClientUtils.getMinecraft().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(fluid.getAttributes().getStillTexture(fluidStack));
			int fluidHeight = Mth.ceil(((fluid.getAttributes().isGaseous(fluidStack))?1:(fluidStack.getAmount() / (double) tankCapacity)) * fullHeight);
			Color color = new Color(fluid.getAttributes().getColor(fluidStack));
			RenderSystem.setShaderColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, ((((!fluid.getAttributes().isGaseous(fluidStack))?1:(fluidStack.getAmount() / (float) tankCapacity)) * color.getAlpha()) / 255f));
			RenderSystem.enableBlend();
			bindTexture(TextureAtlas.LOCATION_BLOCKS);
			fillAreaWithSprite(poseStack, fluidAtlasSprite, x, y + fullHeight - fluidHeight, width, fluidHeight);
			RenderSystem.disableBlend();
			RenderSystem.setShaderColor(1, 1, 1, 1);
		}
	}

	public static void drawTexture(PoseStack poseStack, float x, float y, float width, float height)
	{
		drawTexture(poseStack, x, y, width, height, 0, 0, 1, 1);
	}

	public static void drawTexture(
			PoseStack poseStack,
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
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		Matrix4f matrix = poseStack.last().pose();
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		buffer.vertex(matrix, x, y + height, 0).uv(tx, ty + theight).endVertex();
		buffer.vertex(matrix, x + width, y + height, 0).uv(tx + twidth, ty + theight).endVertex();
		buffer.vertex(matrix, x + width, y, 0).uv(tx + twidth, ty).endVertex();
		buffer.vertex(matrix, x, y, 0).uv(tx, ty).endVertex();
		tessellator.end();
	}

	public static void fillRect(PoseStack poseStack, float x, float y, float width, float height, int color)
	{
		float alpha = (float) (color >> 24 & 255) / 255.0F;
		float red = (float) (color >> 16 & 255) / 255.0F;
		float green = (float) (color >> 8 & 255) / 255.0F;
		float blue = (float) (color & 255) / 255.0F;
		fillRect(poseStack, x, y, width, height, red, green, blue, alpha);
	}

	public static void fillRect(
			PoseStack poseStack,
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

		Matrix4f matrix = poseStack.last().pose();
		Tesselator tessellator = Tesselator.getInstance();
		BufferBuilder buffer = tessellator.getBuilder();
		buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
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
		RenderSystem.setShaderTexture(0, location);
	}
}
