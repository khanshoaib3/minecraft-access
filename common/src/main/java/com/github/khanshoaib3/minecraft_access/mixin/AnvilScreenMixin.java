package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(AnvilScreen.class)
public class AnvilScreenMixin {

    /**
     * The "drawForeground" method is triggered when enchant cost changes.
     */
    @Inject(at = @At("HEAD"), method = "drawForeground")
    protected void speakCost(int mouseX, int mouseY, float delta) {
        AnvilScreenHandler handler = ((AnvilScreenHandler) ((HandledScreenAccessor) this).getHandler());
        int cost = handler.getLevelCost();

        // Copied from AnvilScreen.drawForeground() 1.20.6
        if (cost > 0) {
            Text text;
            if (cost >= 40 && !WorldUtils.getClientPlayer().getAbilities().creativeMode) {
                text = ((AnvilScreenAccessor) this).getTooExpensiveText();
            } else if (!(handler).getSlot(2).hasStack()) {
                text = null;
            } else {
                text = Text.translatable("container.repair.cost", cost);
            }

            if (text != null) {
                MainClass.speakWithNarrator(text.getLiteralString(), true);
            }
        }
    }
}
