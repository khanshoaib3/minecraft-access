package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.utils.NamedFormatter;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.IllegalFormatException;
import java.util.Map;
import java.util.Objects;

@Mixin(I18n.class)
public class I18nMixin {
    @Unique
    private static Language minecraft_access$enLanguage;

    /**
     * Use NamedFormat.format() instead of String.format() (in original logic)
     * when translation key has "{}"
     */
    @SuppressWarnings("unchecked")
    @Inject(at = @At("HEAD"), method = "translate", cancellable = true)
    private static void useNamedFormatter(String key, Object[] args, CallbackInfoReturnable<String> cir) {
        if (args.length == 1 && args[0] instanceof Map) {
            String pattern = I18NAccessor.getLanguage().get(key);
            Map<String, Object> values = (Map<String, Object>) args[0];
            String result = NamedFormatter.format(pattern, values);

            // fallback to english
            if (result.startsWith("minecraft_access")) {
                result = minecraft_access$translateEn(key, args);
            }

            cir.setReturnValue(result);
            cir.cancel();
        }
    }

    /**
     * Add a fallback mechanism to I18n in case it fails on unsupported languages.
     */
    @Inject(at = @At("RETURN"), method = "translate", cancellable = true)
    private static void fallbackFailedI18NToEnglish(String key, Object[] args, CallbackInfoReturnable<String> cir) {
        // if result still is config key, That's because I18n failed, fallback to english
        if (cir.getReturnValue().startsWith("minecraft_access")) {
            cir.setReturnValue(minecraft_access$translateEn(key, args));
            cir.cancel();
        }
    }

    /**
     * Copied from original code
     */
    @Unique
    private static String minecraft_access$translateEn(String key, Object... args) {
        if (minecraft_access$enLanguage == null) {
            minecraft_access$loadEnLanguage();
        }
        String string = Objects.requireNonNull(minecraft_access$enLanguage).get(key);
        try {
            return String.format(string, args);
        } catch (IllegalFormatException illegalFormatException) {
            return "Format error: " + string;
        }
    }

    @Unique
    private static void minecraft_access$loadEnLanguage() {
        minecraft_access$enLanguage = Language.getInstance();
    }
}
