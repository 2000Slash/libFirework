package com.github.libfirework.types.simple;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.ICustomRocketType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;

import java.util.LinkedList;
import java.util.List;

public class CustomRocketTypeBuilder {

    private static class SimpleRocketType extends ICustomRocketType {
        private final List<DrawAction> drawActions;

        private SimpleRocketType(CustomRocketTypeBuilder builder) {
            super(builder.getName(), builder.getItems());
            this.drawActions = builder.getDrawActions();
        }

        @Override
        public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            for (var drawAction : drawActions) {
                drawAction.draw(x, y, z, colors, fadeColors, trail, flicker, particleManager);
            }
        }

    }


    private String name;
    private List<Item> items;
    private final List<DrawAction> drawActions = new LinkedList<>();


    public CustomRocketTypeBuilder(String name, Item... items) {
        this.name = name;
        this.items = List.of(items);
    }

    public CustomRocketTypeBuilder setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CustomRocketTypeBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    public CustomRocketTypeBuilder addItems(List<Item> items) {
        this.items.addAll(items);
        return this;
    }

    public CustomRocketTypeBuilder applyDrawAction(DrawAction drawAction) {
        this.drawActions.add(drawAction);
        return this;
    }

    public CustomRocketTypeBuilder drawLines(double[][] coords, int numberOfIntersects) {
        var drawAction = new DrawLinesAction(coords, numberOfIntersects);
        return applyDrawAction(drawAction);
    }

    public List<Item> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

    public CustomRocketType build() {
        return new SimpleRocketType(this);
    }

    public List<DrawAction> getDrawActions() {
        return drawActions;
    }
}