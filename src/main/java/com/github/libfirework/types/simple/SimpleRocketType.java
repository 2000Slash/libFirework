package com.github.libfirework.types.simple;

import com.github.libfirework.types.ICustomRocketType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;

import java.util.List;

public class SimpleRocketType extends ICustomRocketType {
    private SimpleRocketType(CustomRocketTypeBuilder builder) {
        super(builder.getName(), builder.getItems());
    }

    @Override
    public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {

    }


    public static class CustomRocketTypeBuilder {
        private String name;
        private List<Item> items;


        public CustomRocketTypeBuilder(String name, Item... items) {
            this.name = name;
            this.items = List.of(items);
        }

        public CustomRocketTypeBuilder setItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public CustomRocketTypeBuilder addItem(Item item) {
            items.add(item);
            return this;
        }

        public CustomRocketTypeBuilder addItems(List<Item> items) {
            this.items.addAll(items);
            return this;
        }

        public List<Item> getItems() {
            return items;
        }

        public String getName() {
            return name;
        }
    }

}
