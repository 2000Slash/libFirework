package com.github.libfirework.types.simple;

import com.github.libfirework.mixins.ParticleAccessor;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;

public interface DrawAction {

    public void draw(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager);
}
