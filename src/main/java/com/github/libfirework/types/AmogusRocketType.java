package com.github.libfirework.types;

import net.minecraft.client.particle.ParticleManager;
import net.minecraft.item.Items;

public class AmogusRocketType extends ICustomRocketType {

    public AmogusRocketType() {
        super("amogus", 5, Items.NETHERITE_INGOT);
    }

    @Override
    public void explode(double x, double y, double z, int[] colors, int[] fadeColors, boolean trail, boolean flicker, ParticleManager particleManager) {
        this.drawLine(x, y, z, new double[]{0.0, 0.0}, new double[]{1.0, 1.0}, colors, fadeColors, trail, flicker, particleManager);
    }
}
