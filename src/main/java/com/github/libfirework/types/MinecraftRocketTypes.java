package com.github.libfirework.types;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.types.ICustomRocketType;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

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
        public EmptyRocketType(Identifier identifier, Item... items) {
            super(identifier, items);
        }

        /**
         * Minecrafts default explosions are hardcoded here {@link FireworksSparkParticle.FireworkParticle#tick()}
         */
        @Override
        public void explode(Vec3d velocity, Vec3d coords, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) { }
    }

    static {
        SmallRocketType = new EmptyRocketType(new Identifier("small_ball"));
        LargeRocketType = new EmptyRocketType(new Identifier("large_ball"), Items.FIRE_CHARGE);
        StarRocketType = new EmptyRocketType(new Identifier("star"), Items.GOLD_NUGGET);
        CreeperRocketType = new EmptyRocketType(new Identifier("creeper"), Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD, Items.PIGLIN_HEAD);
        BurstRocketType = new EmptyRocketType(new Identifier("burst"), Items.FEATHER);
    }
}
