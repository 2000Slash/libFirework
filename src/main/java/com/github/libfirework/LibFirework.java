package com.github.libfirework;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.MinecraftRocketTypes;
import com.github.libfirework.types.simple.CustomRocketTypeBuilder;
import com.google.common.collect.Maps;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Pair;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LibFirework implements ModInitializer {
    public static Logger LOGGER = LoggerFactory.getLogger("libfirework");
    private static int nextId = 5;
    private static final Map<Integer, CustomRocketType> rocketTypes = new HashMap<>();

    @Override
    public void onInitialize() {
        rocketTypes.put(0, MinecraftRocketTypes.SmallRocketType);
        rocketTypes.put(1, MinecraftRocketTypes.LargeRocketType);
        rocketTypes.put(2, MinecraftRocketTypes.StarRocketType);
        rocketTypes.put(3, MinecraftRocketTypes.CreeperRocketType);
        rocketTypes.put(4, MinecraftRocketTypes.BurstRocketType);

        registerCustomRocketType(new CustomRocketTypeBuilder("amogus", Items.NETHERITE_INGOT).drawLines(new double[][]{{0.0, 1.0}, {1.0, 0.0}, {-1.0, 0.0}, {0.0, 1.0}}, 50).build());
    }

    public static Map<Integer, CustomRocketType> getRocketTypes() {
        return rocketTypes;
    }

    public void registerCustomRocketType(CustomRocketType customRocketType) {
        rocketTypes.put(nextId, customRocketType);
        nextId++;
    }

    public static Ingredient getIngredients() {
        return Ingredient.ofItems(rocketTypes.values().stream().map(CustomRocketType::getRecipeItem).flatMap(Collection::stream).toArray(ItemConvertible[]::new));
    }

    public static Optional<CustomRocketType> rocketTypeById(int id) {
        return Optional.ofNullable(rocketTypes.get(id));
    }

    public static Map<Item, Pair<CustomRocketType, Integer>> getRecipeMap() {
        return Util.make(Maps.newHashMap(), (hashMap) -> {
            rocketTypes.forEach((integer, customRocketType) -> customRocketType.getRecipeItem().forEach(item -> hashMap.put(item, new Pair<>(customRocketType, integer))));
        });
    }
}
