package com.github.libfirework;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class MinecraftRocketTypes {
    public static final CustomRocketType SmallRocketType;
    public static final CustomRocketType LargeRocketType;
    public static final CustomRocketType StarRocketType;
    public static final CustomRocketType CreeperRocketType;
    public static final CustomRocketType BurstRocketType;

    static {
        SmallRocketType = new ICustomRocketType("small_ball", 0);
        LargeRocketType = new ICustomRocketType("large_ball", 1, Items.FIRE_CHARGE);
        StarRocketType = new ICustomRocketType("star", 2, Items.GOLD_NUGGET);
        CreeperRocketType = new ICustomRocketType("creeper", 3, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD, Items.PIGLIN_HEAD);
        BurstRocketType = new ICustomRocketType("burst", 4, Items.FEATHER);
    }
}
