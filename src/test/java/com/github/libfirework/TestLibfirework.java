package com.github.libfirework;

import com.github.libfirework.types.simple.CustomRocketTypeBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;

public class TestLibfirework implements ModInitializer {

    @Override
    public void onInitialize() {
        LibFirework.registerCustomRocketType(new CustomRocketTypeBuilder("amogus", Items.NETHERITE_INGOT).drawLines(new double[][]{{0.0, 1.0}, {1.0, 0.0}, {-1.0, 0.0}, {0.0, 1.0}}, 50).build());
    }
}
