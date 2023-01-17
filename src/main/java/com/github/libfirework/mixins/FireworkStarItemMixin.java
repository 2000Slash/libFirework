package com.github.libfirework.mixins;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.LibFirework;
import com.github.libfirework.types.MinecraftRocketTypes;
import net.minecraft.item.FireworkStarItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(FireworkStarItem.class)
public class FireworkStarItemMixin {
    @Inject(method = "appendFireworkTooltip", at = @At("HEAD"), cancellable = true)
    private static void appendFireworkTooltipMixin(NbtCompound nbtCompound, List<Text> list, CallbackInfo ci) {
        Optional<CustomRocketType> rocketTypeOptional = LibFirework.rocketTypeById(nbtCompound.getByte("Type"));
        CustomRocketType rocketType = rocketTypeOptional.orElse(MinecraftRocketTypes.StarRocketType);
        list.add(Text.translatable("item." + rocketType.getIdentifier().getNamespace() + ".firework_star.shape." + rocketType.getIdentifier().getPath()).formatted(Formatting.GRAY));
        int[] is = nbtCompound.getIntArray("Colors");
        if (is.length > 0) {
            list.add(appendColors(Text.empty().formatted(Formatting.GRAY), is));
        }

        int[] js = nbtCompound.getIntArray("FadeColors");
        if (js.length > 0) {
            list.add(appendColors(Text.translatable("item.minecraft.firework_star.fade_to").append(" ").formatted(Formatting.GRAY), js));
        }

        if (nbtCompound.getBoolean("Trail")) {
            list.add(Text.translatable("item.minecraft.firework_star.trail").formatted(Formatting.GRAY));
        }

        if (nbtCompound.getBoolean("Flicker")) {
            list.add(Text.translatable("item.minecraft.firework_star.flicker").formatted(Formatting.GRAY));
        }
        ci.cancel();
    }

    private static Text getColorText(int i) {
        DyeColor dyeColor = DyeColor.byFireworkColor(i);
        return dyeColor == null ? Text.translatable("item.minecraft.firework_star.custom_color") : Text.translatable("item.minecraft.firework_star." + dyeColor.getName());
    }

    private static Text appendColors(MutableText mutableText, int[] is) {
        for(int i = 0; i < is.length; ++i) {
            if (i > 0) {
                mutableText.append(", ");
            }

            mutableText.append(getColorText(is[i]));
        }

        return mutableText;
    }
}
