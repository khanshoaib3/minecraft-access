package com.github.khanshoaib3.minecraft_access.forge.mixin;

import com.github.khanshoaib3.minecraft_access.features.SpeakHeldItem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Since forge 1.20.1-47.2.17, {@link ForgeGui} overrides the "render" method of {@link InGameHud},
 * and inside it there is no call to {@link InGameHud#renderHeldItemTooltip(DrawContext)},
 * which is the entry point of our held-item-speaking (speak hotbar) feature.
 * So we need to make another entry point for {@link ForgeGui}, resulting in this class.
 */
@Mixin(ForgeGui.class)
public class ForgeGuiMixin {
    @Unique
    private SpeakHeldItem minecraft_access$feature;

    /**
     * This method is continually invoked by the InGameHud.render(),
     * so we use previousContent to check if the content has changed and need to be narrated.
     */
    @Inject(at = @At("RETURN"), method = "render")
    public void renderHeldItemTooltipMixin(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (this.minecraft_access$feature == null) {
            this.minecraft_access$feature = new SpeakHeldItem();
        }

        ItemStack currentStack = ((InGameHudAccessor) this).getCurrentStack();
        int heldItemTooltipFade = ((InGameHudAccessor) this).getHeldItemTooltipFade();
        this.minecraft_access$feature.speakHeldItem(currentStack, heldItemTooltipFade);
    }
}
