package com.github.libfirework.types.simple;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

class DrawLinesAction extends BaseDrawAction {
    private Random random = Random.create();
    private double[][] coords;
    private int numberOfIntersects;

    public DrawLinesAction(double[][] coords, int numberOfIntersects) {
        this.coords = coords;
        this.numberOfIntersects = numberOfIntersects;
    }

    @Override
    public void draw(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
        // Random rotation for each launch
        float rotation = random.nextFloat() * (float)Math.PI;

        var lastX = coords[0][0];
        var lastY = coords[0][1];
        for (int i = 1; i < coords.length; i++) {
            var currentX = coords[i][0];
            var currentY = coords[i][1];
            for (int j = 0; j <= numberOfIntersects; j++) {
                double particleX = MathHelper.lerp(j/(float)numberOfIntersects, lastX, currentX);
                double particleY = MathHelper.lerp(j/(float)numberOfIntersects, lastY, currentY);
                double particleZ = particleX * Math.sin(rotation);
                particleX *= Math.cos(rotation);
                addExplosionParticle(x, y, z, particleX, particleY, particleZ, colors, fadeColors, trail, flicker, particleManager);
            }
            lastX = currentX;
            lastY = currentY;
        }
    }
}
