package com.github.libfirework.types;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.ICustomRocketType;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

/**
 * The Minecraft default rocket types
 */
public class MinecraftRocketTypes {
    public static final CustomRocketType SmallRocketType;
    public static final CustomRocketType LargeRocketType;
    public static final CustomRocketType StarRocketType;
    public static final CustomRocketType CreeperRocketType;
    public static final CustomRocketType BurstRocketType;

    private static class EmptyRocketType extends ICustomRocketType {
        public EmptyRocketType(String name, int id, Item... items) {
            super(name, id, items);
        }

        /**
         * Minecrafts default explosions are hardcoded here {@link FireworksSparkParticle.FireworkParticle#tick()}
         */
        @Override
        public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) { }
    }

    static {
        SmallRocketType = new EmptyRocketType("small_ball", 0);
        LargeRocketType = new EmptyRocketType("large_ball", 1, Items.FIRE_CHARGE);
        StarRocketType = new EmptyRocketType("star", 2, Items.GOLD_NUGGET);
        CreeperRocketType = new EmptyRocketType("creeper", 3, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD, Items.PIGLIN_HEAD);
        BurstRocketType = new EmptyRocketType("burst", 4, Items.FEATHER);
    }
}