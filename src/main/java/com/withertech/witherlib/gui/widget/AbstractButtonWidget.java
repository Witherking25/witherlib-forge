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
     * @param onPress the action which will called when the user clicks the
     *                widget
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
