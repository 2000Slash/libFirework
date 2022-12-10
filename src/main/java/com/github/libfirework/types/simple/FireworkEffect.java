package com.github.libfirework.types.simple;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.Vec3d;

public interface FireworkEffect {

    public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager);
}
