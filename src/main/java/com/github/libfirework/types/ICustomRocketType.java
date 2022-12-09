package com.github.libfirework.types;

import com.github.libfirework.ParticleUtils;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

import java.util.List;

/**
 * A default implementation of CustomRocketType without any explosions
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

    protected void drawLine(double x, double y, double z, double[] from, double[] to, int[] colours, int[] fadeColours, boolean trail, boolean twinkle, ParticleManager particleManager) {
        for (int i = 0; i <= 4; i++) {
            double particleX = MathHelper.lerp(i/4.0, from[0], to[0]);
            double particleY = MathHelper.lerp(i/4.0, from[1], to[1]);
            ParticleUtils.addExplosionParticle(x, y, z, particleX, particleY, 0, colours, fadeColours, trail, twinkle, particleManager);
        }
    }
}
