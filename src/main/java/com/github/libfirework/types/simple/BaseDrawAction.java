package com.github.libfirework.types.simple;

import com.github.libfirework.mixins.ParticleAccessor;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

public abstract class BaseDrawAction implements DrawAction {
    private final Random random = Random.create();

    protected void addExplosionParticle(double x, double y, double z, double particleX, double particleY, double particleZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
        FireworksSparkParticle.Explosion explosion = (FireworksSparkParticle.Explosion)particleManager.addParticle(ParticleTypes.FIREWORK, x, y, z, particleX, particleY, particleZ);
        ParticleAccessor accessor = (ParticleAccessor) explosion;
        explosion.setTrail(trail);
        explosion.setFlicker(flicker);
        accessor.invokeSetAlpha(0.99F);
        int j = random.nextInt(colors.length);
        explosion.setColor(colors[j]);
        if (fadeColors.length > 0) {
            explosion.setTargetColor(Util.getRandom(fadeColors, random));
        }
    }
}
