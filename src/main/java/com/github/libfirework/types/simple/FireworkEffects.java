package com.github.libfirework.types.simple;

import com.github.libfirework.LibFirework;
import com.github.libfirework.mixins.ParticleAccessor;
import com.kitfox.svg.Path;
import com.kitfox.svg.SVGCache;
import com.kitfox.svg.SVGDiagram;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.awt.*;
import java.awt.geom.PathIterator;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class FireworkEffects {

    private FireworkEffects() { }

    public abstract static class BaseFireworkEffect implements com.github.libfirework.types.simple.FireworkEffect {
        protected final Random random = Random.create();

        protected void addExplosionParticle(Vec3d coords, double particleX, double particleY, double particleZ, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            FireworksSparkParticle.Explosion explosion = (FireworksSparkParticle.Explosion)particleManager.addParticle(ParticleTypes.FIREWORK, coords.x, coords.y, coords.z, particleX, particleY, particleZ);
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

    public static class LinesFireworkEffect extends BaseFireworkEffect {
        private final Random random = Random.create();
        private final double[][] coords;
        private final int numberOfExplosions;

        public LinesFireworkEffect(double[][] coords, int numberOfExplosions) {
            this.coords = coords;
            this.numberOfExplosions = numberOfExplosions;
        }

        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            // Random rotation for each launch
            float rotation = random.nextFloat() * (float)Math.PI;

            var lastX = this.coords[0][0];
            var lastY = this.coords[0][1];
            for (int i = 1; i < this.coords.length; i++) {
                var currentX = this.coords[i][0];
                var currentY = this.coords[i][1];
                for (int j = 0; j <= numberOfExplosions; j++) {
                    double particleX = MathHelper.lerp(j/(float) numberOfExplosions, lastX, currentX);
                    double particleY = MathHelper.lerp(j/(float) numberOfExplosions, lastY, currentY);
                    double particleZ = particleX * Math.sin(rotation);
                    particleX *= Math.cos(rotation);
                    addExplosionParticle(coords, particleX, particleY, particleZ, colors, fadeColors, trail, flicker, particleManager);
                }
                lastX = currentX;
                lastY = currentY;
            }
        }
    }

    public static class BallFireworkEffect extends BaseFireworkEffect {
        private final int radius;
        private final double spread;

        public BallFireworkEffect(int radius, double spread) {
            this.radius = radius;
            this.spread = spread;
        }

        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            for (int offsetX = -radius; offsetX <= radius; ++offsetX) {
                for (int offsetY = -radius; offsetY <= radius; ++offsetY) {
                    for (int offsetZ = -radius; offsetZ <= radius; ++offsetZ) {
                        double rOffsetX = offsetX + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double rOffsetY = offsetY + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double rOffsetZ = offsetZ + (this.random.nextDouble() - this.random.nextDouble()) * 0.5;
                        double length = Math.sqrt(rOffsetX * rOffsetX + rOffsetY * rOffsetY + rOffsetZ * rOffsetZ) / spread + this.random.nextGaussian() * 0.05;
                        this.addExplosionParticle(coords, rOffsetX / length, rOffsetY / length, rOffsetZ / length, colors, fadeColors, trail, flicker, particleManager);
                        if (offsetX == -radius || offsetX == radius || offsetY == -radius || offsetY == radius) continue;
                        offsetZ += radius * 2 - 1;
                    }
                }
            }
        }
    }

    public static class BurstFireworkEffect extends BaseFireworkEffect {
        private final int numberOfExplosions;

        public BurstFireworkEffect(int numberOfExplosions) {
            this.numberOfExplosions = numberOfExplosions;
        }

        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
                  double offsetX = this.random.nextGaussian() * 0.05;
            double offsetY = this.random.nextGaussian() * 0.05;
            for (int i = 0; i < numberOfExplosions; ++i) {
                double x = velocity.x * 0.5 + this.random.nextGaussian() * 0.15 + offsetX;
                double z = velocity.z * 0.5 + this.random.nextGaussian() * 0.15 + offsetY;
                double y = velocity.y * 0.5 + this.random.nextDouble() * 0.5;
                this.addExplosionParticle(coords, x, y, z, colors, fadeColors, trail, flicker, particleManager);
            }
        }
    }

    public static class SVGFireworkEffect extends BaseFireworkEffect {
        private List<Double[]> points;
        private float[] origin;

        public SVGFireworkEffect(InputStream inputStream, String name, float pointDistance, double scale, float[] origin) {
            this.origin = origin;
            try {
                points = new ArrayList<>();
                URI uri = SVGCache.getSVGUniverse().loadSVG(inputStream, name);
                SVGDiagram diagram = SVGCache.getSVGUniverse().getDiagram(uri);
                for (var child : diagram.getRoot().getChildren(null)) {
                    Path childPath = (Path) child;
                    Shape shape = childPath.getShape();
                    PathIterator pathIterator = shape.getPathIterator(null, pointDistance);
                    double[] coords = new double[2];
                    while (!pathIterator.isDone()) {
                        pathIterator.currentSegment(coords);
                        points.add(new Double[]{coords[0]*scale, -coords[1]*scale});
                        pathIterator.next();
                    }
                }
            } catch (IOException e) {
                LibFirework.LOGGER.error("Could not load inputStream");
            }
        }

        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            // Random rotation for each launch
            float rotation = random.nextFloat() * (float)Math.PI;

            for (var point: points) {
                double particleX = point[0]+origin[0];
                double particleY = point[1]+origin[1];
                double particleZ = particleX * Math.sin(rotation);
                particleX *= Math.cos(rotation);
                addExplosionParticle(coords, particleX, particleY, particleZ, colors, fadeColors, trail, flicker, particleManager);
            }
        }
    }

}
