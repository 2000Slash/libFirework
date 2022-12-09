package com.github.libfirework;

import com.github.libfirework.mixins.ParticleAccessor;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

public class ParticleUtils {
    private static final Random random = Random.create();



    /**
     * Die shape wird drei mal gezeigt und auf der Y achse gedreht. Der Faktor hängt von creeper ab. Für jede linie in shape werden 4 partikel gespawnt. Es wird auf der Y achse gespiegelt
     * @param x
     * @param y
     * @param z
     * @param size
     * @param shape
     * @param colours
     * @param fadeColours
     * @param trail
     * @param twinkle
     * @param creeper
     * @param particleManager
     */
    public static void drawShape(double x, double y, double z, double size, double[][] shape, int[] colours, int[] fadeColours, boolean trail, boolean twinkle, boolean creeper, ParticleManager particleManager) {
        double motionX = shape[0][0];
        double motionY = shape[0][1];
        addExplosionParticle(x, y, z, motionX * size, motionY * size, 0.0, colours, fadeColours, trail, twinkle, particleManager);

        // Random rotation offset
        float rotationOffset = random.nextFloat() * (float)Math.PI;

        // If creeper = true -> Rotation factor is very small
        double rotationFactor = creeper ? 0.034 : 0.34;
        for (int i = 0; i < 3; ++i) {
            double rotation = rotationOffset + (i * Math.PI) * rotationFactor;
            double currentMotionX = motionX;
            double currentMotionY = motionY;
            for (int l = 1; l < shape.length; ++l) {
                double nextMotionX = shape[l][0];
                double nextMotionY = shape[l][1];
                for (double o = 0.25; o <= 1.0; o += 0.25) {
                    double particleX = MathHelper.lerp(o, currentMotionX, nextMotionX) * size;
                    double particleY = MathHelper.lerp(o, currentMotionY, nextMotionY) * size;
                    double particleZ = particleX * Math.sin(rotation);
                    particleX *= Math.cos(rotation);
                    for (double s = -1.0; s <= 1.0; s += 2.0) {
                        addExplosionParticle(x, y, z, particleX * s, particleY, particleZ * s, colours, fadeColours, trail, twinkle, particleManager);
                    }
                }
                currentMotionX = nextMotionX;
                currentMotionY = nextMotionY;
            }
        }
    }

    public static void addExplosionParticle(double d, double e, double f, double g, double h, double i, int[] is, int[] js, boolean bl, boolean bl2, ParticleManager particleManager) {
        FireworksSparkParticle.Explosion explosion = (FireworksSparkParticle.Explosion)particleManager.addParticle(ParticleTypes.FIREWORK, d, e, f, g, h, i);
        ParticleAccessor accessor = (ParticleAccessor) explosion;
        explosion.setTrail(bl);
        explosion.setFlicker(bl2);
        accessor.invokeSetAlpha(0.99F);
        int j = random.nextInt(is.length);
        explosion.setColor(is[j]);
        if (js.length > 0) {
            explosion.setTargetColor(Util.getRandom(js, random));
        }
    }

    public static void explodeBall(double e, double f, double g, double d, int i, int[] is, int[] js, boolean bl, boolean bl2, ParticleManager particleManager) {
        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                for(int l = -i; l <= i; ++l) {
                    double h = (double)k + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double m = (double)j + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double n = (double)l + (random.nextDouble() - random.nextDouble()) * 0.5;
                    double o = Math.sqrt(h * h + m * m + n * n) / d + random.nextGaussian() * 0.05;
                    addExplosionParticle(e, f, g, h / o, m / o, n / o, is, js, bl, bl2, particleManager);
                    if (j != -i && j != i && k != -i && k != i) {
                        l += i * 2 - 1;
                    }
                }
            }
        }
    }
}
