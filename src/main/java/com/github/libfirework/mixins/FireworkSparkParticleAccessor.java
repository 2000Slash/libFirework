package com.github.libfirework.mixins;

import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.nbt.NbtList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public interface FireworkSparkParticleAccessor {
    @Accessor
    NbtList getExplosions();

    @Accessor
    int getAge();
}
