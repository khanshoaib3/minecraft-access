package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimatedResultButton.class)
public class AnimatedResultButtonMixin {
    @Unique
    boolean minecraft_access$vibratingFlag = false;

    @Unique
    String minecraft_access$previousItemName = "";

    //    @Inject(at = @At("HEAD"), method = "appendNarrations", cancellable = true) // Pre 1.19.3
    @Inject(at = @At("HEAD"), method = "appendClickableNarrations", cancellable = true) // From 1.19.3
    private void appendNarrations(NarrationMessageBuilder builder, CallbackInfo callbackInfo) {
        ItemStack itemStack = ((AnimatedResultButtonAccessor) this).callGetResults().get(((AnimatedResultButtonAccessor) this).getCurrentResultIndex()).getOutput(DynamicRegistryManager.EMPTY);
        String itemName = itemStack.getName().getString();

        if (!itemName.equalsIgnoreCase(minecraft_access$previousItemName)) {
            String craftable = ((AnimatedResultButtonAccessor) this).getResultCollection().hasCraftableRecipes() ? "craftable" : "not_craftable";
            craftable = I18n.translate("minecraft_access.other." + craftable);
            String toSpeak = "%s %d %s".formatted(craftable, itemStack.getCount(), itemName);
            // Let the item speaking not interrupt the "recipe book group selected" speaking
            MainClass.speakWithNarrator(toSpeak, false);
            minecraft_access$previousItemName = itemName;
        }

        minecraft_access$shakeTheMouse();
        callbackInfo.cancel();
    }

    /**
     * It seems the "appendNarrations" will be invoked after every mouse moving.
     * Keep moving the mouse to trigger this method to read different items in same animated button.
     * It's not a solution that gets to the root of the problem, but I think it's simpler and more stable.
     * This method doesn't affect slot moving inside recipe book group.
     */
    @Unique
    private void minecraft_access$shakeTheMouse() {
        // the width and height of one animated button are both 25.
        int offset = this.minecraft_access$vibratingFlag ? 12 : 13;
        int x = ((ClickableWidgetAccessor) this).callGetX() + offset;
        int y = ((ClickableWidgetAccessor) this).callGetY() + offset;
        MouseUtils.Coordinates p = MouseUtils.calcRealPositionOfWidget(x, y);
        MouseUtils.move(p.x(), p.y());
        this.minecraft_access$vibratingFlag = !this.minecraft_access$vibratingFlag;
    }
}
