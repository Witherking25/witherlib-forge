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

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;

/**
 * Created 1/19/2021 by SuperMartijn642
 */
public abstract class Widget
{

    public int x, y;
    public int width, height;
    public boolean active = true;
    public boolean hovered = false, wasHovered = false;
    public float blitOffset = 0;
    protected long nextNarration = Long.MAX_VALUE;

    public Widget(int x, int y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void narrate()
    {
        if (this.wasHovered != this.hovered)
        {
            this.nextNarration = this.hovered ? Util.getMillis() + (long) 750 : Long.MAX_VALUE;
        }

        if (this.active && this.hovered && Util.getMillis() > this.nextNarration)
        {
            ITextComponent message = this.getNarrationMessage();
            String s = message == null ? "" : message.getString();
            if (!s.isEmpty())
            {
                NarratorChatListener.INSTANCE.sayNow(s);
                this.nextNarration = Long.MAX_VALUE;
            }
        }
    }

    /**
     * @return the message that should be narrated for the current state of the widget
     */
    protected abstract ITextComponent getNarrationMessage();

    /**
     * Sets whether the widget is active, i.e. can be interacted with.
     * The way the widget is rendered can also change base on whether the
     * widget is active.
     */
    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Renders the entire widget.
     */
    public abstract void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks);

    /**
     * @return whether the user is hovering their cursor over the widget
     */
    public boolean isHovered()
    {
        return this.hovered;
    }

    /**
     * Called whenever a mouse button is pressed down.
     */
    public void mouseClicked(int mouseX, int mouseY, int button)
    {
    }

    /**
     * Called whenever a mouse button is released.
     */
    public void mouseReleased(int mouseX, int mouseY, int button)
    {
    }

    /**
     * Called whenever the user performs a scroll action.
     */
    public void mouseScrolled(int mouseX, int mouseY, double scroll)
    {
    }

    /**
     * Called whenever a key is pressed down.
     */
    public void keyPressed(int keyCode)
    {
    }

    /**
     * Called whenever a key is released.
     */
    public void keyReleased(int keyCode)
    {
    }

    /**
     * Called whenever a character key is released with the given character {@code c}.
     */
    public void charTyped(char c)
    {
    }
}
