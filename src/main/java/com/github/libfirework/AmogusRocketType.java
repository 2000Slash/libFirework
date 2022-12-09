package com.github.libfirework;

import com.github.libfirework.mixins.ParticleAccessor;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

public class AmogusRocketType extends ICustomRocketType {

    public AmogusRocketType() {
        super("amogus", 5, Items.NETHERITE_INGOT);
    }

    @Override
    public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
        this.explodeStar(x, y, z, 1.0, new double[][]{{0.0, 0.0}, {1.0, 1.0}}, colors, fadeColors, trail, flicker, true, particleManager);
    }


    private void addExplosionParticle(double d, double e, double f, double g, double h, double i, int[] is, int[] js, boolean bl, boolean bl2, ParticleManager particleManager) {
        FireworksSparkParticle.Explosion explosion = (FireworksSparkParticle.Explosion)particleManager.addParticle(ParticleTypes.FIREWORK, d, e, f, g, h, i);
        ParticleAccessor accessor = (ParticleAccessor) explosion;
        explosion.setTrail(bl);
        explosion.setFlicker(bl2);
        accessor.invokeSetAlpha(0.99F);
        int j = this.random.nextInt(is.length);
        explosion.setColor(is[j]);
        if (js.length > 0) {
            explosion.setTargetColor(Util.getRandom(js, this.random));
        }

    }

    private void explodeBall(double e, double f, double g, double d, int i, int[] is, int[] js, boolean bl, boolean bl2, ParticleManager particleManager) {

        for(int j = -i; j <= i; ++j) {
            for(int k = -i; k <= i; ++k) {
                for(int l = -i; l <= i; ++l) {
                    double h = (double)k + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                    double m = (double)j + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                    double n = (double)l + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                    double o = Math.sqrt(h * h + m * m + n * n) / d + this.random.nextGaussian() * 0.05;
                    this.addExplosionParticle(e, f, g, h / o, m / o, n / o, is, js, bl, bl2, particleManager);
                    if (j != -i && j != i && k != -i && k != i) {
                        l += i * 2 - 1;
                    }
                }
            }
        }

    }

    private void explodeStar(double x, double y, double z, double d, double[][] ds, int[] is, int[] js, boolean bl, boolean bl2, boolean bl3, ParticleManager particleManager) {
        double e = ds[0][0];
        double f = ds[0][1];
        this.addExplosionParticle(x, y, z, e * d, f * d, 0.0, is, js, bl, bl2, particleManager);
        float g = this.random.nextFloat() * 3.1415927F;
        double h = bl3 ? 0.034 : 0.34;

        for(int i = 0; i < 3; ++i) {
            double j = (double)g + (double)((float)i * 3.1415927F) * h;
            double k = e;
            double l = f;

            for(int m = 1; m < ds.length; ++m) {
                double n = ds[m][0];
                double o = ds[m][1];

                for(double p = 0.25; p <= 1.0; p += 0.25) {
                    double q = MathHelper.lerp(p, k, n) * d;
                    double r = MathHelper.lerp(p, l, o) * d;
                    double s = q * Math.sin(j);
                    q *= Math.cos(j);

                    for(double t = -1.0; t <= 1.0; t += 2.0) {
                        this.addExplosionParticle(x, y, z, q * t, r, s * t, is, js, bl, bl2, particleManager);
                    }
                }

                k = n;
                l = o;
            }
        }

    }
}
