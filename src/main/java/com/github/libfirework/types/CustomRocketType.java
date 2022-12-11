package com.github.libfirework.types;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public interface CustomRocketType {
    public Identifier getIdentifier();

    public List<Item> getRecipeItem();

    /**
     * Explode the custom firework
     * @param colors Colors is an int array in the binary format rrrrrrrrggggggggbbbbbbbb. See {@link net.minecraft.client.particle.AnimatedParticle#setColor(int) setColor} for an example
     * @param fadeColors Fade Colors is an int array in the binary format rrrrrrrrggggggggbbbbbbbb. See {@link net.minecraft.client.particle.AnimatedParticle#setColor(int) setColor} for an example
     * @param trail
     * @param flicker
     * @param particleManager
     */
    public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager);
}
