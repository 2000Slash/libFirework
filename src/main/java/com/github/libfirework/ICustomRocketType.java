package com.github.libfirework;

import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class ICustomRocketType implements CustomRocketType {
    private String name;
    private int id;
    private List<Item> items;

    protected final Random random = Random.create();


    public ICustomRocketType(String name, int id, Item... items) {
        this.name = name;
        this.id = id;
        this.items = List.of(items);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Item> getRecipeItem() {
        return items;
    }

    // Minecrafts default Rocket Types are still hardcoded
    @Override
    public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
        LibFirework.LOGGER.info("Called empty method");
    }
}
