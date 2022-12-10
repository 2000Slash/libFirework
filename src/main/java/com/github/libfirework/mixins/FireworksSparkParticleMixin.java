package com.github.libfirework.mixins;

import com.github.libfirework.types.CustomRocketType;
import com.github.libfirework.LibFirework;
import com.github.libfirework.types.MinecraftRocketTypes;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3d;
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
        FireworksSparkParticle.FireworkParticle thiz = (FireworksSparkParticle.FireworkParticle)(Object)this;
        Particle thisParticle = thiz;

        FireworkSparkParticleAccessor accessor = (FireworkSparkParticleAccessor) thiz;
        ParticleAccessor particleAccessor = (ParticleAccessor) thisParticle;

        int j = accessor.getAge() / 2;
        NbtCompound nbtCompound2 = accessor.getExplosions().getCompound(j);
        int rocketId = nbtCompound2.getByte("Type");
        Optional<CustomRocketType> rocketTypeOptional = LibFirework.rocketTypeById(rocketId);
        CustomRocketType rocketType = rocketTypeOptional.orElse(MinecraftRocketTypes.SmallRocketType);
        boolean trail = nbtCompound2.getBoolean("Trail");
        boolean flicker = nbtCompound2.getBoolean("Flicker");
        int[] colors = nbtCompound2.getIntArray("Colors");
        int[] fadeColors = nbtCompound2.getIntArray("FadeColors");
        if (colors.length == 0) {
            colors = new int[]{DyeColor.BLACK.getFireworkColor()};
        }

        if (rocketId == 0 || rocketId == 1) {
            return;
        } else {
            var coords = new Vec3d(particleAccessor.getX(), particleAccessor.getY(), particleAccessor.getZ());
            var velocity = new Vec3d(particleAccessor.getVelocityX(), particleAccessor.getVelocityY(), particleAccessor.getVelocityZ());
            rocketType.explode(velocity, coords, colors, fadeColors, trail, flicker, particleManager);
            callBackInfo.cancel();
        }
    }
}
