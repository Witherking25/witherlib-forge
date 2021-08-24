package com.withertech.witherlib.gui;

import com.withertech.witherlib.util.ClientUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;

import java.util.function.Supplier;

/**
 * Created 1/26/2021 by SuperMartijn642
 */
public abstract class ItemBaseScreen extends ObjectBaseScreen<ItemStack>
{

    private final Supplier<ItemStack> stackSupplier;

    private ItemBaseScreen(ITextComponent title, Supplier<ItemStack> itemStackSupplier)
    {
        super(title);
        this.stackSupplier = itemStackSupplier;
    }

    protected ItemBaseScreen(ITextComponent title, int playerSlot)
    {
        this(title, () -> ClientUtils.getPlayer().inventory.getItem(playerSlot));
    }

    protected ItemBaseScreen(ITextComponent title, Hand hand)
    {
        this(title, () -> ClientUtils.getPlayer().getItemInHand(hand));
    }

    @Override
    protected ItemStack getObject()
    {
        return this.stackSupplier.get();
    }
}
