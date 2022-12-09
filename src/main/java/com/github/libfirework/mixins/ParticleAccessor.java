package com.github.libfirework.mixins;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface ParticleAccessor {

    @Accessor(value="x")
    double getX();

    @Accessor(value="y")
    double getY();

    @Accessor(value="z")
    double getZ();

    @Invoker(value = "setAlpha")
    void invokeSetAlpha(float f);
}
