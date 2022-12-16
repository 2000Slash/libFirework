package com.github.libfirework.types.simple;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.Vec3d;

public interface SimpleFireworkEffect {

    public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager);

    public void setMainParticle(ParticleEffect particleEffect);
}
