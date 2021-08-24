package com.withertech.witherlib.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.withertech.witherlib.gui.ScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

/**
 * Created 10/15/2020 by SuperMartijn642
 */
public class ButtonWidget extends AbstractButtonWidget
{

    private ITextComponent text;

    /**
     * @param text    the text to be displayed on the button
     * @param onPress the action which will called when the user clicks the
     *                widget
     */
    public ButtonWidget(int x, int y, int width, int height, ITextComponent text, Runnable onPress)
    {
        super(x, y, width, height, onPress);
        this.text = text;
    }

    /**
     * Sets the text which is displayed on the button.
     */
    public void setText(ITextComponent text)
    {
        this.text = text;
    }

    @Override
    protected ITextComponent getNarrationMessage()
    {
        return this.text;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        ScreenUtils.drawButtonBackground(matrixStack, this.x, this.y, this.width, this.height, (this.active ? this.isHovered() ? 5 : 0 : 10) / 15f);
        ScreenUtils.drawCenteredStringWithShadow(matrixStack, Minecraft.getInstance().font, this.text, this.x + this.width / 2f, this.y + this.height / 2f - 5, this.active ? 0xFFFFFFFF : Integer.MAX_VALUE);
    }
}
