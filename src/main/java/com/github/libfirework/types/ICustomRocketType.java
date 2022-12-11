package com.github.libfirework.types;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.List;

/**
 * A default implementation of CustomRocketType
 */
public abstract class ICustomRocketType implements CustomRocketType {
    private Identifier identifier;
    private List<Item> items;

    protected final Random random = Random.create();


    public ICustomRocketType(Identifier identifier, Item... items) {
        this(identifier, List.of(items));
    }

    public ICustomRocketType(Identifier identifier, List<Item> items) {
        this.identifier = identifier;
        this.items = items;
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }

    @Override
    public List<Item> getRecipeItem() {
        return items;
    }
}
