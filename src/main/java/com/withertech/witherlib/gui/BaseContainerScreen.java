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

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.withertech.witherlib.WitherLib;
import com.withertech.witherlib.gui.widget.IHoverTextWidget;
import com.withertech.witherlib.gui.widget.ITickableWidget;
import com.withertech.witherlib.gui.widget.TextFieldWidget;
import com.withertech.witherlib.gui.widget.Widget;
import com.withertech.witherlib.util.ClientUtils;
import com.withertech.witherlib.util.ScreenUtils;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * Created 1/20/2021 by SuperMartijn642
 */
public abstract class BaseContainerScreen<T extends BaseContainer> extends AbstractContainerScreen<T>
{

	private static final ResourceLocation SLOT_TEXTURE = WitherLib.INSTANCE.MOD.modLocation(
			"textures/gui/slot.png");
	/**
	 * Have this because it replaced my variable name with an srg name for some reason
	 **/
//	@Deprecated
//	protected final T field_147002_h;
	protected final T container;
	private final List<Widget> widgets = new LinkedList<>();
	private final List<ITickableWidget> tickableWidgets = new LinkedList<>();
	private boolean drawSlots = true;

	/**
	 * @param screenContainer container the screen will be attached to
	 * @param title           title to be read by the narrator and to be displayed in the gui
	 */
	public BaseContainerScreen(T screenContainer, Component title)
	{
		super(screenContainer, screenContainer.player.getInventory(), title);
		this.container = screenContainer;
//		this.field_147002_h = screenContainer;
	}

	/**
	 * @return the width of the screen
	 */
	protected abstract int sizeX();

	/**
	 * @return the height of the screen
	 */
	protected abstract int sizeY();

	/**
	 * @return the left edge of the screen
	 */
	protected int left()
	{
		return (this.width - this.sizeX()) / 2;
	}

	/**
	 * @return the top edge of the screen
	 */
	protected int top()
	{
		return (this.height - this.sizeY()) / 2;
	}

	@Override
	public int getXSize()
	{
		return this.sizeX();
	}

	@Override
	public int getYSize()
	{
		return this.sizeY();
	}

	@Override
	public int getGuiLeft()
	{
		return this.left();
	}

	@Override
	public int getGuiTop()
	{
		return this.top();
	}

	/**
	 * Sets whether slots should be drawn by the {@link BaseContainerScreen}.
	 *
	 * @param drawSlots whether slots should be drawn
	 */
	protected void setDrawSlots(boolean drawSlots)
	{
		this.drawSlots = drawSlots;
	}

	@Override
	public void init()
	{
		this.imageWidth = this.sizeX();
		this.imageHeight = this.sizeY();
		super.init();

		this.widgets.clear();
		this.tickableWidgets.clear();
		this.addWidgets();
	}

	/**
	 * Adds widgets to the screen via {@link #addWidget(Widget)}.
	 */
	protected abstract void addWidgets();

	/**
	 * Add the given {@code widget} to the screen.
	 *
	 * @param widget widget to be added
	 * @return the given {@code widget}
	 */
	protected <W extends Widget> W addWidget(W widget)
	{
		this.widgets.add(widget);
		if (widget instanceof ITickableWidget)
		{
			this.tickableWidgets.add((ITickableWidget) widget);
		}
		return widget;
	}

	/**
	 * Removes the given {@code widget} from the screen.
	 *
	 * @param widget widget to be removed
	 * @return the given {@code widget}
	 */
	protected <W extends Widget> W removeWidget(W widget)
	{
		this.widgets.remove(widget);
		if (widget instanceof ITickableWidget)
		{
			this.tickableWidgets.remove(widget);
		}
		return widget;
	}

	@Override
	public void containerTick()
	{
		for (Widget widget : this.widgets)
		{
			if (widget instanceof ITickableWidget)
			{
				((ITickableWidget) widget).tick();
			}
		}
	}

	@Override
	public void render(@Nonnull PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(poseStack);

		poseStack.translate(this.left(), this.top(), 0);
		this.renderBackground(poseStack, mouseX - this.left(), mouseY - this.top());

		if (this.drawSlots)
		{
			for (Slot slot : this.menu.slots)
			{
				ScreenUtils.bindTexture(SLOT_TEXTURE);
				ScreenUtils.drawTexture(poseStack, slot.x - 1, slot.y - 1, 18, 18);
			}
		}
		poseStack.translate(-this.left(), -this.top(), 0);

		super.render(poseStack, mouseX, mouseY, partialTicks);
		// apparently some OpenGl settings are messed up after this


//		RenderSystem.enableAlphaTest();
//		GlStateManager._disableLighting();

		poseStack.translate(this.left(), this.top(), 0);
		for (Widget widget : this.widgets)
		{
			widget.blitOffset = this.getBlitOffset();
			widget.wasHovered = widget.hovered;
			widget.hovered = mouseX - this.left() > widget.x && mouseX - this.left() < widget.x + widget.width &&
					mouseY - this.top() > widget.y && mouseY - this.top() < widget.y + widget.height;
			widget.render(poseStack, mouseX - this.left(), mouseY - this.top(), partialTicks);
			widget.narrate();
		}

		this.renderForeground(poseStack, mouseX - this.left(), mouseY - this.top());

		for (Widget widget : this.widgets)
		{
			if (widget instanceof IHoverTextWidget && widget.isHovered())
			{
				List<Component> text = ((IHoverTextWidget) widget).getHoverText();
				if (text != null)
				{
					this.renderComponentTooltip(poseStack, text, mouseX - this.left(), mouseY - this.top());
				}
			}
		}
		poseStack.translate(-this.left(), -this.top(), 0);
		super.renderTooltip(poseStack, mouseX, mouseY);
		this.renderTooltips(poseStack, mouseX, mouseY);
	}

	@Override
	protected void renderBg(@Nonnull PoseStack poseStack, float partialTicks, int x, int y)
	{
	}

	@Override
	protected void renderLabels(@Nonnull PoseStack poseStack, int x, int y)
	{
	}

	/**
	 * Renders the screen's background. This will be called first in the render chain.
	 */
	protected void renderBackground(PoseStack poseStack, int mouseX, int mouseY)
	{
		this.drawScreenBackground(poseStack);
	}

	/**
	 * Renders the screen's foreground.
	 * Widgets are drawn after this.
	 */
	protected void renderForeground(PoseStack poseStack, int mouseX, int mouseY)
	{
		ScreenUtils.drawString(poseStack, this.font, this.title, 8, 7, 4210752);
	}

	/**
	 * Renders tooltips for the given {@code mouseX} and {@code mouseY}.
	 * This will be called last in the render chain.
	 */
	protected void renderTooltips(PoseStack poseStack, int mouseX, int mouseY)
	{
	}

	/**
	 * Draws the default screen background.
	 * Same as {@link ScreenUtils#drawScreenBackground(PoseStack, float, float, float, float)}.
	 */
	protected void drawScreenBackground(PoseStack poseStack, float x, float y, float width, float height)
	{
		ScreenUtils.drawScreenBackground(poseStack, x, y, width, height);
	}

	/**
	 * Draws the default screen background with width {@link #sizeX()} and height {@link #sizeY()}.
	 */
	protected void drawScreenBackground(PoseStack poseStack)
	{
		ScreenUtils.drawScreenBackground(poseStack, 0, 0, this.sizeX(), this.sizeY());
	}

	@Override
	public boolean isPauseScreen()
	{
		return false;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		mouseX -= this.left();
		mouseY -= this.top();

		this.onMousePress((int) mouseX, (int) mouseY, button);

		for (Widget widget : this.widgets)
		{
			widget.mouseClicked((int) mouseX, (int) mouseY, button);
		}

		mouseX += this.left();
		mouseY += this.top();

		return super.mouseClicked(mouseX, mouseY, button);
	}

	/**
	 * Called whenever a mouse button is pressed down.
	 */
	protected void onMousePress(int mouseX, int mouseY, int button)
	{
	}

	@Override
	public boolean mouseReleased(double mouseX, double mouseY, int button)
	{
		mouseX -= this.left();
		mouseY -= this.top();

		this.onMouseRelease((int) mouseX, (int) mouseY, button);

		for (Widget widget : this.widgets)
		{
			widget.mouseReleased((int) mouseX, (int) mouseY, button);
		}

		mouseX += this.left();
		mouseY += this.top();

		return super.mouseReleased(mouseX, mouseY, button);
	}

	/**
	 * Called whenever a mouse button is released.
	 */
	protected void onMouseRelease(int mouseX, int mouseY, int button)
	{
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double delta)
	{
		mouseX -= this.left();
		mouseY -= this.top();

		this.onMouseScroll((int) mouseX, (int) mouseY, delta);

		for (Widget widget : this.widgets)
		{
			widget.mouseScrolled((int) mouseX, (int) mouseY, delta);
		}

		mouseX += this.left();
		mouseY += this.top();

		return super.mouseScrolled(mouseX, mouseY, delta);
	}

	/**
	 * Called whenever the user performs a scroll action.
	 */
	protected void onMouseScroll(int mouseX, int mouseY, double scroll)
	{
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if (this.keyPressed(keyCode))
		{
			return true;
		}

		InputConstants.Key key = InputConstants.getKey(keyCode, scanCode);
		if (ClientUtils.getMinecraft().options.keyInventory.isActiveAndMatches(key))
		{
			this.onClose();
			return true;
		}

		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	/**
	 * Called whenever a key is pressed down.
	 */
	public boolean keyPressed(int keyCode)
	{
		boolean handled = false;

		for (Widget widget : this.widgets)
		{
			if (widget instanceof TextFieldWidget && ((TextFieldWidget) widget).canWrite())
			{
				handled = true;
			}
			widget.keyPressed(keyCode);
		}

		return handled;
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers)
	{
		return this.keyReleased(keyCode);
	}

	/**
	 * Called whenever a key is released.
	 */
	public boolean keyReleased(int keyCode)
	{
		for (Widget widget : this.widgets)
		{
			widget.keyReleased(keyCode);
		}

		return false;
	}

	@Override
	public boolean charTyped(char codePoint, int modifiers)
	{
		return this.charTyped(codePoint);
	}

	/**
	 * Called whenever a character key is released with the given character {@code c}.
	 */
	public boolean charTyped(char c)
	{
		for (Widget widget : this.widgets)
		{
			widget.charTyped(c);
		}

		return false;
	}
}
