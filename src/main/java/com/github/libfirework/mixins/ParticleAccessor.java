package com.github.libfirework.mixins;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Particle.class)
public interface ParticleAccessor {

    @Accessor(value="x") double getX();
    @Accessor(value="y") double getY();
    @Accessor(value="z") double getZ();
    @Accessor(value="velocityX") double getVelocityX();
    @Accessor(value="velocityY") double getVelocityY();
    @Accessor(value="velocityZ") double getVelocityZ();

    @Invoker(value = "setAlpha")
    void invokeSetAlpha(float f);
}
