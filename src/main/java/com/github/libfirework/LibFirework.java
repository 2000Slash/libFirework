package com.github.libfirework;

import com.github.libfirework.types.AmogusRocketType;
import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.MinecraftRocketTypes;
import com.google.common.collect.Maps;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LibFirework implements ModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("libfirework");
    private int nextId = 5;
    private static List<CustomRocketType> rocketTypes = new ArrayList<>();

    @Override
    public void onInitialize() {
        rocketTypes.add(MinecraftRocketTypes.SmallRocketType);
        rocketTypes.add(MinecraftRocketTypes.LargeRocketType);
        rocketTypes.add(MinecraftRocketTypes.StarRocketType);
        rocketTypes.add(MinecraftRocketTypes.CreeperRocketType);
        rocketTypes.add(MinecraftRocketTypes.BurstRocketType);
        rocketTypes.add(new AmogusRocketType());
    }

    public static List<CustomRocketType> getRocketTypes() {
        return rocketTypes;
    }

    public static Ingredient getIngredients() {
        return Ingredient.ofItems(rocketTypes.stream().map(customRocketType -> customRocketType.getRecipeItem()).flatMap(Collection::stream).toArray(ItemConvertible[]::new));
    }

    public static Optional<CustomRocketType> rocketTypeById(int id) {
        return rocketTypes.stream().filter(customRocketType -> customRocketType.getId() == id).findFirst();
    }

    public static Map<Item, CustomRocketType> getRecipeMap() {
        return Util.make(Maps.newHashMap(), (hashMap) -> {
            rocketTypes.stream().forEach(customRocketType -> customRocketType.getRecipeItem().stream().forEach(item -> hashMap.put(item, customRocketType)));
        });
    }
}
