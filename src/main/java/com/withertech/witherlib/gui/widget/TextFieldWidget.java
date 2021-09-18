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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.withertech.witherlib.util.ScreenUtils;
import com.withertech.witherlib.util.TextComponents;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Created 1/20/2021 by SuperMartijn642
 */
public class TextFieldWidget extends Widget implements ITickableWidget
{

	private final BiConsumer<String, String> changeListener;
	protected int maxLength;
	protected boolean focused;
	protected int lineScrollOffset;
	protected int cursorPosition;
	protected int selectionPos;
	protected boolean drawBackground = true;
	protected int activeTextColor = 14737632, inactiveTextColor = 7368816;
	private String text;
	private String suggestion = "";
	private int cursorBlinkCounter;

	public TextFieldWidget(
			int x,
			int y,
			int width,
			int height,
			String defaultText,
			int maxLength,
			BiConsumer<String, String> changeListener
	)
	{
		super(x, y, width, height);
		this.text = defaultText;
		this.maxLength = maxLength;
		this.changeListener = changeListener;
		this.cursorPosition = this.selectionPos = defaultText.length();
	}

	public TextFieldWidget(
			int x,
			int y,
			int width,
			int height,
			String defaultText,
			int maxLength,
			Consumer<String> changeListener
	)
	{
		this(
				x,
				y,
				width,
				height,
				defaultText,
				maxLength,
				changeListener == null ? null : (a, b) -> changeListener.accept(b)
		);
	}

	public TextFieldWidget(int x, int y, int width, int height, String defaultText, int maxLength)
	{
		this(x, y, width, height, defaultText, maxLength, (BiConsumer<String, String>) null);
	}

	@Override
	protected List<Component> getNarrationMessage()
	{
		return Collections.singletonList(TextComponents.translation("gui.narrate.editBox", this.suggestion, this.text).get());
	}

	@Override
	public void tick()
	{
		this.cursorBlinkCounter++;
	}

	@Override
	public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks)
	{
		if (this.drawBackground)
		{
			this.drawBackground(poseStack);
		}

		int textColor = this.active ? this.activeTextColor : this.inactiveTextColor;
		int relativeCursor = this.cursorPosition - this.lineScrollOffset;
		int relativeSelection = this.selectionPos - this.lineScrollOffset;
		Font fontRenderer = Minecraft.getInstance().font;
		String s = fontRenderer.plainSubstrByWidth(
				this.text.substring(this.lineScrollOffset),
				this.width - 8
		);
		boolean cursorInView = relativeCursor >= 0 && relativeCursor <= s.length();
		boolean shouldBlink = this.focused && this.cursorBlinkCounter / 8 % 2 == 0 && cursorInView;
		int left = this.x + 4;
		int top = this.y + (this.height - 8) / 2;
		int leftOffset = left;

		if (relativeSelection > s.length())
		{
			relativeSelection = s.length();
		}

		if (!s.isEmpty())
		{
			String s1 = cursorInView ? s.substring(0, relativeCursor) : s;
			leftOffset = fontRenderer.draw(poseStack, s1, left, top, textColor) + 1;
		}

		boolean cursorAtEnd = this.cursorPosition < this.text.length();
		int cursorX = leftOffset;

		if (!cursorInView)
		{
			cursorX = relativeCursor > 0 ? left + this.width : left;
		} else if (cursorAtEnd)
		{
			cursorX = leftOffset - 1;
			leftOffset--;
		}

		// draw text
		if (!s.isEmpty() && cursorInView && relativeCursor < s.length())
		{
			fontRenderer.draw(poseStack, s.substring(relativeCursor), leftOffset, top, textColor);
		}

		// draw suggestion
		if (!this.suggestion.isEmpty() && this.text.isEmpty())
		{
			fontRenderer.drawShadow(
					poseStack,
					fontRenderer.plainSubstrByWidth(
							this.suggestion,
							this.width - 8 - fontRenderer.width("...")
					) + "...",
					cursorX,
					top,
					-8355712
			);
		}

		// draw cursor
		if (shouldBlink)
		{
			if (cursorAtEnd)
			{
				ScreenUtils.fillRect(poseStack, cursorX - 0.5f, top - 1, 1, fontRenderer.lineHeight, -3092272);
			} else
			{
				fontRenderer.drawShadow(poseStack, "_", cursorX, top, textColor);
			}
		}

		if (relativeSelection != relativeCursor)
		{
			int l1 = left + fontRenderer.width(s.substring(0, relativeSelection));
			this.drawSelectionBox(poseStack, cursorX, top - 1, l1 - 1, top + 1 + fontRenderer.lineHeight);
		}
	}

	protected void drawBackground(PoseStack poseStack)
	{
		ScreenUtils.fillRect(poseStack, this.x, this.y, this.width, this.height, this.focused ? -1 : -6250336);
		ScreenUtils.fillRect(poseStack, this.x + 1, this.y + 1, this.width - 2, this.height - 2, -16777216);
	}

	protected void drawSelectionBox(PoseStack poseStack, int startX, int startY, int endX, int endY)
	{
		if (startX < endX)
		{
			int i = startX;
			startX = endX;
			endX = i;
		}

		if (startY < endY)
		{
			int j = startY;
			startY = endY;
			endY = j;
		}

		if (endX > this.x + this.width)
		{
			endX = this.x + this.width;
		}

		if (startX > this.x + this.width)
		{
			startX = this.x + this.width;
		}

		Matrix4f matrix = poseStack.last().pose();
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferbuilder = tesselator.getBuilder();
		RenderSystem.setShaderColor(0.0F, 0.0F, 255.0F, 255.0F);
		RenderSystem.disableTexture();
		RenderSystem.enableColorLogicOp();
		RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
		bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
		bufferbuilder.vertex(matrix, startX, endY, 0).endVertex();
		bufferbuilder.vertex(matrix, endX, endY, 0).endVertex();
		bufferbuilder.vertex(matrix, endX, startY, 0).endVertex();
		bufferbuilder.vertex(matrix, startX, startY, 0).endVertex();
		tesselator.end();
		RenderSystem.disableColorLogicOp();
		RenderSystem.enableTexture();
	}

	public void clear()
	{
		this.setText("");
	}

	public String getText()
	{
		return this.text;
	}

	public void setText(String text)
	{
		String oldText = this.text;

		this.setTextSuppressed(text);

		if (!oldText.equals(this.text))
		{
			this.onTextChanged(oldText, text);
		}
	}

	/**
	 * Sets {@code text} without calling {@link TextFieldWidget#onTextChanged(String, String)}
	 */
	public void setTextSuppressed(String text)
	{
		if (text == null)
		{
			text = "";
		} else if (text.length() > this.maxLength)
		{
			text = SharedConstants.filterText(text.substring(0, this.maxLength));
		}

		this.lineScrollOffset = 0;
		this.cursorPosition = 0;
		this.selectionPos = 0;
		this.text = text;
	}

	protected void addTextAtCursor(String text)
	{
		String oldText = this.text;

		text = SharedConstants.filterText(text);
		if (text.length() + this.text.length() - this.getSelectedText().length() > this.maxLength)
		{
			text = text.substring(0, this.maxLength - this.text.length() + this.getSelectedText().length() + 1);
		}

		int min = Math.min(this.cursorPosition, this.selectionPos);
		int max = Math.max(this.cursorPosition, this.selectionPos);
		this.text = this.text.substring(0, min) + text + this.text.substring(max);
		this.cursorPosition = min + text.length();
		this.selectionPos = this.cursorPosition;
		this.moveLineOffsetToCursor();

		if (!oldText.equals(this.text))
		{
			this.cursorBlinkCounter = 1;
			this.onTextChanged(oldText, this.text);
		}
	}

	protected void removeAtCursor(boolean left)
	{
		if (this.text.isEmpty())
		{
			return;
		}

		String oldText = text;
		if (this.cursorPosition != this.selectionPos)
		{
			this.text = this.text.substring(
					0,
					Math.min(this.cursorPosition, this.selectionPos)
			) + this.text.substring(Math.max(this.cursorPosition, this.selectionPos));
			this.cursorPosition = this.selectionPos = Math.min(this.cursorPosition, this.selectionPos);
		} else if (left && this.cursorPosition > 0)
		{
			this.text = this.text.substring(0, this.cursorPosition - 1) + this.text.substring(this.cursorPosition);
			this.cursorPosition -= 1;
			this.selectionPos -= 1;
		} else if (!left && this.cursorPosition < this.text.length())
		{
			this.text = this.text.substring(0, this.cursorPosition) + this.text.substring(this.cursorPosition + 1);
		}

		this.moveLineOffsetToCursor();

		this.cursorBlinkCounter = 1;

		this.onTextChanged(oldText, this.text);
	}

	protected void moveLineOffsetToCursor()
	{
		Font fontRenderer = Minecraft.getInstance().font;
		int availableWidth = this.width - 8 - (
				this.cursorPosition == this.text.length() ? fontRenderer.width("_") : 0
		);
		int min = Math.min(
				this.cursorPosition + 1,
				this.text.length()
		) - fontRenderer.plainSubstrByWidth(new StringBuilder(this.text.substring(
				0,
				Math.min(
						this.text.length(),
						this.cursorPosition + 2
				)
		)).reverse().toString(), availableWidth).length();
		int max = Math.max(
				this.cursorPosition - 1,
				0
		) + fontRenderer.plainSubstrByWidth(
				this.text.substring(Math.max(this.cursorPosition - 1, 0)),
				availableWidth
		).length();
		max = max - fontRenderer.plainSubstrByWidth(
				new StringBuilder(this.text.substring(0, max)).reverse().toString(),
				availableWidth
		).length();
		this.lineScrollOffset = Math.min(Math.max(this.lineScrollOffset, min), max);
	}

	public String getSelectedText()
	{
		if (this.cursorPosition == this.selectionPos)
		{
			return "";
		}

		return this.text.substring(
				Math.min(this.cursorPosition, this.selectionPos),
				Math.max(this.cursorPosition, this.selectionPos)
		);
	}

	protected void onTextChanged(String oldText, String newText)
	{
		if (this.changeListener != null)
		{
			this.changeListener.accept(oldText, newText);
		}
	}

	public String getSuggestion()
	{
		return this.suggestion;
	}

	public void setSuggestion(String suggestion)
	{
		this.suggestion = suggestion == null ? "" : suggestion;
	}

	public void setTextColors(int activeTextColor, int inactiveTextColor)
	{
		this.activeTextColor = activeTextColor;
		this.inactiveTextColor = inactiveTextColor;
	}

	public void setDrawBackground(boolean drawBackground)
	{
		this.drawBackground = drawBackground;
	}

	public boolean isFocused()
	{
		return this.focused;
	}

	public void setFocused(boolean focused)
	{
		this.focused = focused;
	}

	@Override
	public void keyPressed(int keyCode)
	{
		if (!this.canWrite())
		{
			return;
		}

		boolean shift = Screen.hasShiftDown();
		if (keyCode == 256)
		{
			this.setFocused(false);
		} else if (Screen.isSelectAll(keyCode))
		{
			this.lineScrollOffset = 0;
			this.cursorPosition = this.text.length();
			this.selectionPos = 0;
		} else if (Screen.isCopy(keyCode))
		{
			Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelectedText());
		} else if (Screen.isPaste(keyCode))
		{
			this.addTextAtCursor(Minecraft.getInstance().keyboardHandler.getClipboard());
		} else if (Screen.isCut(keyCode))
		{
			Minecraft.getInstance().keyboardHandler.setClipboard(this.getSelectedText());
			this.addTextAtCursor("");
		} else
		{
			switch (keyCode)
			{
				case 259: // backspace
					this.removeAtCursor(true);
					break;
				case 260: // insert
				case 264: // ?
				case 265: // ?
				case 266: // page up
				case 267: // page down
				default:
					break;
				case 261: // delete
					this.removeAtCursor(false);
					break;
				case 262: // right
					if (!shift && this.cursorPosition != this.selectionPos)
					{
						this.cursorPosition = this.selectionPos = Math.max(this.cursorPosition, this.selectionPos);
					} else if (this.cursorPosition < this.text.length())
					{
						this.cursorPosition = this.cursorPosition + 1;
						if (!shift)
						{
							this.selectionPos = this.cursorPosition;
						}
					}
					this.moveLineOffsetToCursor();
					break;
				case 263: // left
					if (!shift && this.cursorPosition != this.selectionPos)
					{
						this.cursorPosition = this.selectionPos = Math.min(this.cursorPosition, this.selectionPos);
					} else if (this.cursorPosition > 0)
					{
						this.cursorPosition = this.cursorPosition - 1;
						if (!shift)
						{
							this.selectionPos = this.cursorPosition;
						}
					}
					this.moveLineOffsetToCursor();
					break;
				case 268: // home
					this.cursorPosition = this.selectionPos = 0;
					this.moveLineOffsetToCursor();
					break;
				case 269: // end
					this.cursorPosition = this.selectionPos = this.text.length();
					this.moveLineOffsetToCursor();
					break;
			}
		}
	}

	@Override
	public void charTyped(char c)
	{
		if (!this.canWrite())
		{
			return;
		}

		if (SharedConstants.isAllowedChatCharacter(c))
		{
			this.addTextAtCursor(Character.toString(c));
		}
	}

	public boolean canWrite()
	{
		return this.active && this.focused;
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int button)
	{
		this.focused =
				this.active && mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;

		if (this.focused)
		{
			if (button == 1)
			{
				this.clear();
			} else
			{
				int offset = Mth.floor(mouseX) - this.x - 4;

				Font font = Minecraft.getInstance().font;
				String s = font.plainSubstrByWidth(
						this.text.substring(this.lineScrollOffset),
						Math.min(offset, this.width - 8)
				);
				this.cursorPosition = s.length() + this.lineScrollOffset;
				if (!Screen.hasShiftDown())
				{
					this.selectionPos = this.cursorPosition;
				}
			}
		}
	}
}
