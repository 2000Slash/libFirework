package com.github.libfirework.types;

import net.minecraft.item.Item;
import net.minecraft.util.math.random.Random;

import java.util.List;

/**
 * A default implementation of CustomRocketType
 */
public abstract class ICustomRocketType implements CustomRocketType {
    private String name;
    private List<Item> items;

    protected final Random random = Random.create();


    public ICustomRocketType(String name, Item... items) {
        this(name, List.of(items));
    }

    public ICustomRocketType(String name, List<Item> items) {
        this.name = name;
        this.items = items;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Item> getRecipeItem() {
        return items;
    }
}
