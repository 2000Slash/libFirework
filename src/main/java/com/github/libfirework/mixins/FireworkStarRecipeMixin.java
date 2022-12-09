package com.github.libfirework.mixins;

import com.github.libfirework.CustomRocketType;
import com.github.libfirework.LibFirework;
import com.github.libfirework.MinecraftRocketTypes;
import com.google.common.collect.Lists;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.FireworkStarRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(FireworkStarRecipe.class)
public class FireworkStarRecipeMixin {
    @Accessor(value="TYPE_MODIFIER_MAP")
    static public Map<Item, FireworkRocketItem.Type> getTypeModifierMap() {
        throw new AssertionError();
    }

    @Shadow
    @Final
    @Mutable
    private static Ingredient TRAIL_MODIFIER;

    @Shadow
    @Final
    @Mutable
    private static Ingredient GUNPOWDER;

    @Shadow
    @Final
    @Mutable
    private static Ingredient FLICKER_MODIFIER;

    @Inject(method = "craft", at = @At(value="HEAD"), cancellable = true)
    void craft(CraftingInventory craftingInventory, CallbackInfoReturnable<ItemStack> ci) {
        ItemStack itemStack = new ItemStack(Items.FIREWORK_STAR);
        NbtCompound nbtCompound = itemStack.getOrCreateSubNbt("Explosion");
        CustomRocketType type = MinecraftRocketTypes.SmallRocketType;
        List<Integer> list = Lists.newArrayList();

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack2 = craftingInventory.getStack(i);
            if (!itemStack2.isEmpty()) {
                if (LibFirework.getIngredients().test(itemStack2)) {
                    type = (CustomRocketType) LibFirework.getRecipeMap().get(itemStack2.getItem());
                } else if (FLICKER_MODIFIER.test(itemStack2)) {
                    nbtCompound.putBoolean("Flicker", true);
                } else if (TRAIL_MODIFIER.test(itemStack2)) {
                    nbtCompound.putBoolean("Trail", true);
                } else if (itemStack2.getItem() instanceof DyeItem) {
                    list.add(((DyeItem)itemStack2.getItem()).getColor().getFireworkColor());
                }
            }
        }

        nbtCompound.putIntArray("Colors", list);
        nbtCompound.putByte("Type", (byte)type.getId());
        ci.setReturnValue(itemStack);
    }

    @Inject(method = "matches", at = @At(value="HEAD"), cancellable = true)
    void injectedMatches(CraftingInventory craftingInventory, World world, CallbackInfoReturnable<Boolean> ci) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;

        for(int i = 0; i < craftingInventory.size(); ++i) {
            ItemStack itemStack = craftingInventory.getStack(i);
            if (!itemStack.isEmpty()) {
                if (LibFirework.getIngredients().test(itemStack)) {
                    if (bl3) {
                        ci.setReturnValue(false);
                        return;
                    }

                    bl3 = true;
                } else if (FLICKER_MODIFIER.test(itemStack)) {
                    if (bl5) {
                        ci.setReturnValue(false);
                        return;
                    }

                    bl5 = true;
                } else if (TRAIL_MODIFIER.test(itemStack)) {
                    if (bl4) {
                        ci.setReturnValue(false);
                        return;
                    }

                    bl4 = true;
                } else if (GUNPOWDER.test(itemStack)) {
                    if (bl) {
                        ci.setReturnValue(false);
                        return;
                    }

                    bl = true;
                } else {
                    if (!(itemStack.getItem() instanceof DyeItem)) {
                        ci.setReturnValue(false);
                        return;
                    }

                    bl2 = true;
                }
            }
        }

        ci.setReturnValue(bl && bl2);
        return;
    }
}
