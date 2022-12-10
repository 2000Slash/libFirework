package com.github.libfirework;

import com.github.libfirework.types.simple.CustomRocketTypeBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;

public class TestLibfirework implements ModInitializer {

    @Override
    public void onInitialize() {
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder("triangle", Items.NETHERITE_INGOT).drawLines(new double[][]{{0.0, 1.0}, {1.0, 0.0}, {-1.0, 0.0}, {0.0, 1.0}}, 50).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder("cool_ball", Items.SNOWBALL).fillBall(5, 1).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder("cool_burst", Items.FIRE_CHARGE).burst().build());
    }
}
