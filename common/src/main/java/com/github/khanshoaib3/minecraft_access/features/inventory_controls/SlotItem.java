package com.github.khanshoaib3.minecraft_access.features.inventory_controls;

import com.github.khanshoaib3.minecraft_access.mixin.LoomScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.MerchantScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.StonecutterScreenAccessor;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.StonecuttingRecipe;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.List;

public class SlotItem {
    public SlotItem upSlotItem = null;
    public SlotItem rightSlotItem = null;
    public SlotItem downSlotItem = null;
    public SlotItem leftSlotItem = null;

    public int x;
    public int y;

    public Slot slot = null;

    private int recipeOrTradeIndex;

    private int row;
    private int column;

    private String text = null;

    public SlotItem(Slot slot) {
        this.slot = slot;
        this.x = slot.x + 9;
        this.y = slot.y + 9;
    }

    public SlotItem(int x, int y, int recipeOrTradeIndex) {
        this.x = x;
        this.y = y;
        this.recipeOrTradeIndex = recipeOrTradeIndex;
    }

    public SlotItem(int x, int y, int row, int column) {
        this.x = x;
        this.y = y;
        this.row = row;
        this.column = column;
    }

    public SlotItem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SlotItem(int x, int y, String text) {
        this.x = x;
        this.y = y;
        this.text = text;
    }

    public String getNarratableText() {
        if (MinecraftClient.getInstance().currentScreen instanceof LoomScreen loomScreen) {
            List<RegistryEntry<BannerPattern>> list = loomScreen.getScreenHandler().getBannerPatterns();
            if (list.size() == 0) return "";

            int p = row + ((LoomScreenAccessor) loomScreen).getVisibleTopRow();
            int q = p * 4 + column;

            return list.get(q).value().getId();
        }

        if (MinecraftClient.getInstance().currentScreen instanceof StonecutterScreen stonecutterScreen) {
            List<StonecuttingRecipe> list = stonecutterScreen.getScreenHandler().getAvailableRecipes();
            if (list.size() == 0) return "";

            int scrollOffset = ((StonecutterScreenAccessor) stonecutterScreen).getScrollOffset();
            ItemStack item = list.get(recipeOrTradeIndex + scrollOffset).getOutput();
            List<Text> toolTip = MinecraftClient.getInstance().currentScreen.getTooltipFromItem(item);
            StringBuilder toolTipString = new StringBuilder();
            for (Text text : toolTip) {
                toolTipString.append(text.getString()).append("\n");
            }

            return "%s %s".formatted(item.getCount(), toolTipString);
        }

        if (MinecraftClient.getInstance().currentScreen instanceof MerchantScreen merchantScreen) {
            TradeOfferList tradeOfferList = merchantScreen.getScreenHandler().getRecipes();
            if (tradeOfferList.isEmpty()) return I18n.translate("minecraft_access.inventory_controls.Unknown");
            TradeOffer tradeOffer = tradeOfferList.get(recipeOrTradeIndex + ((MerchantScreenAccessor) merchantScreen).getIndexStartOffset());

            ItemStack firstBuyItem = tradeOffer.getOriginalFirstBuyItem();
            ItemStack secondBuyItem = tradeOffer.getSecondBuyItem();
            ItemStack sellItem = tradeOffer.getSellItem();

            String firstBuyItemString = firstBuyItem.getCount() + tradeOffer.getSpecialPrice() + firstBuyItem.getName().getString();
            String secondBuyItemString = "";
            if (!secondBuyItem.isEmpty())
                secondBuyItemString = secondBuyItem.getCount() + secondBuyItem.getName().getString();
            String sellItemString = sellItem.getCount() + sellItem.getName().getString();

            String tradeText;
            if(secondBuyItem.isEmpty()) tradeText = I18n.translate("minecraft_access.inventory_controls.trade_text_format", firstBuyItemString, sellItemString);
            else tradeText = I18n.translate("minecraft_access.inventory_controls.trade_text_format_with_second_item", firstBuyItemString, secondBuyItemString, sellItemString);

            return tradeText;
        }

        if (text != null) {
            return text;
        }

        return "";
    }
}
