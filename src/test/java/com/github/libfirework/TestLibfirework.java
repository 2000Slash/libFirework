package com.github.libfirework;

import com.github.libfirework.types.simple.CustomRocketTypeBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

public class TestLibfirework implements ModInitializer {

    @Override
    public void onInitialize() {
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "triangle"), Items.NETHERITE_INGOT).drawLines(new double[][]{{0.0, 1.0}, {1.0, 0.0}, {-1.0, 0.0}, {0.0, 1.0}}, 50).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "ball"), Items.SNOWBALL).fillBall(5, 1).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "burst"), Items.FIRE_CHARGE).burst(100).build());
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder(new Identifier("libfirework", "amogus"), Items.REDSTONE).explodeSvg(TestLibfirework.class.getClassLoader().getResourceAsStream("amogus.svg"), "amogus.svg", 5.f, 1/500.f, new float[]{-0.8f, 1.f}).build());
    }
}
