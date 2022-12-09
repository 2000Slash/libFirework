package com.github.libfirework.mixins;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.LibFirework;
import com.github.libfirework.types.MinecraftRocketTypes;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(FireworksSparkParticle.FireworkParticle.class)
public class FireworksSparkParticleMixin {

    @Shadow @Final private ParticleManager particleManager;

    @Inject(at = @At("HEAD"), method = "explodeBall", cancellable = true)
    private void injectExplodeBall(double d, int i, int[] is, int[] js, boolean bl, boolean bl2, CallbackInfo callBackInfo) {
        LibFirework.LOGGER.info("Inject entry");
        FireworksSparkParticle.FireworkParticle thiz = (FireworksSparkParticle.FireworkParticle)(Object)this;
        Particle thisParticle = thiz;

        FireworkSparkParticleAccessor accessor = (FireworkSparkParticleAccessor) thiz;
        ParticleAccessor particleAccessor = (ParticleAccessor) thisParticle;

        int j = accessor.getAge() / 2;
        NbtCompound nbtCompound2 = accessor.getExplosions().getCompound(j);
        Optional<CustomRocketType> rocketTypeOptional = LibFirework.rocketTypeById(nbtCompound2.getByte("Type"));
        CustomRocketType rocketType = rocketTypeOptional.orElse(MinecraftRocketTypes.SmallRocketType);
        boolean trail = nbtCompound2.getBoolean("Trail");
        boolean flicker = nbtCompound2.getBoolean("Flicker");
        int[] colors = nbtCompound2.getIntArray("Colors");
        int[] fadeColors = nbtCompound2.getIntArray("FadeColors");
        if (colors.length == 0) {
            colors = new int[]{DyeColor.BLACK.getFireworkColor()};
        }
        LibFirework.LOGGER.info("Found rocket type " + rocketType.getId() + " " + rocketType);

        if (rocketType.getId() == 0 || rocketType.getId() == 1) {
            return;
        } else {
            rocketType.explode(particleAccessor.getX(), particleAccessor.getY(), particleAccessor.getZ(), colors, fadeColors, trail, flicker, particleManager);
            callBackInfo.cancel();
        }
    }
}
