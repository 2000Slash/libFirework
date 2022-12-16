package com.github.libfirework.types.simple;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.ICustomRocketType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class CustomRocketTypeBuilder {

    private static class SimpleRocketType extends ICustomRocketType {
        private final List<SimpleFireworkEffect> simpleFireworkEffects;

        private SimpleRocketType(CustomRocketTypeBuilder builder) {
            super(builder.getIdentifier(), builder.getItems());
            this.simpleFireworkEffects = builder.getDrawActions();
        }

        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
            for (var drawAction : simpleFireworkEffects) {
                drawAction.explode(velocity, coords, colors, fadeColors, trail, flicker, particleManager);
            }
        }

    }


    private Identifier identifier;
    private List<Item> items;
    private final List<SimpleFireworkEffect> simpleFireworkEffects = new LinkedList<>();
    private ParticleEffect mainParticleEffect = ParticleTypes.FIREWORK;


    public CustomRocketTypeBuilder(Identifier identifier, Item... items) {
        this.identifier = identifier;
        this.items = List.of(items);
    }

    public CustomRocketTypeBuilder setItems(List<Item> items) {
        this.items = items;
        return this;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }

    public CustomRocketTypeBuilder addItem(Item item) {
        items.add(item);
        return this;
    }

    public CustomRocketTypeBuilder addItems(List<Item> items) {
        this.items.addAll(items);
        return this;
    }

    public CustomRocketTypeBuilder applyDrawAction(SimpleFireworkEffect simpleFireworkEffect) {
        this.simpleFireworkEffects.add(simpleFireworkEffect);
        return this;
    }

    /**
     * Create multiple lines of particles with numberOfIntersects particles between each line
     * @param coords The coords in the shape [ [Coord1X, Coord1Y], [Coord2X, Coord2Y]  ]
     * @param numberOfIntersects The number of particles in each line
     */
    public CustomRocketTypeBuilder drawLines(double[][] coords, int numberOfIntersects) {
        var drawAction = new SimpleFireworkEffects.LinesSimpleFireworkEffect(coords, numberOfIntersects);
        return applyDrawAction(drawAction);
    }

    /**
     * Create a ball of particles with the radius randius. You can further change the length with spread.
     * The particles first all spawn at the rocket explosion. Then they all move radius*spread units away from the explosion
     * @param radius The radius the particles move to
     * @param spread The multiplier of the radius
     */
    public CustomRocketTypeBuilder fillBall(int radius, double spread) {
        var drawAction = new SimpleFireworkEffects.BallSimpleFireworkEffect(radius, spread);
        return applyDrawAction(drawAction);
    }

    /**
     * Create a burst of particles. They will fly in the same direction as the rocket
     * @param numberOfExplosions The amount of particles
     */
    public CustomRocketTypeBuilder burst(int numberOfExplosions) {
        var drawAction = new SimpleFireworkEffects.BurstSimpleFireworkEffect(numberOfExplosions);
        return applyDrawAction(drawAction);
    }

    /**
     * Draws a svg path. The input stream should be an svg file with a path as the only child of the root.
     * @param inputStream The svg file
     * @param name The name of the file
     * @param pointDistance The distance between two points. Higher value => less particles. This should be experimented with for each svg
     * @param scale The scale of the svg. This should be experimented with for each svg
     * @param origin The origin offset of the svg. This should be experimented with for each svg. Origin is in the form [x_offset, y_offset]
     */
    public CustomRocketTypeBuilder explodeSvg(InputStream inputStream, String name, float pointDistance, double scale, float[] origin) {
        var drawAction = new SimpleFireworkEffects.SVGSimpleFireworkEffect(inputStream, name, pointDistance, scale, origin);
        return applyDrawAction(drawAction);
    }

    public CustomRocketTypeBuilder setMainParticle(ParticleEffect particleEffect) {
        this.mainParticleEffect = particleEffect;
        return this;
    }

    public List<Item> getItems() {
        return items;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public CustomRocketType build() {
        simpleFireworkEffects.forEach(simpleFireworkEffects -> simpleFireworkEffects.setMainParticle(mainParticleEffect));
        return new SimpleRocketType(this);
    }

    public List<SimpleFireworkEffect> getDrawActions() {
        return simpleFireworkEffects;
    }
}