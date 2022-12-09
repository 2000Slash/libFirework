package com.github.libfirework;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.FireworksSparkParticle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;

@Environment(EnvType.CLIENT)
public class MinecraftRocketParticle extends AnimatedParticle {
    private boolean trail;
    private boolean flicker;
    private final ParticleManager particleManager;
    private float field_3801;
    private float field_3800;
    private float field_3799;
    private boolean field_3802;

    MinecraftRocketParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i, ParticleManager particleManager, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, spriteProvider, 0.1F);
        this.velocityX = g;
        this.velocityY = h;
        this.velocityZ = i;
        this.particleManager = particleManager;
        this.scale *= 0.75F;
        this.maxAge = 48 + this.random.nextInt(12);
        this.setSpriteForAge(spriteProvider);
    }

    public void setTrail(boolean bl) {
        this.trail = bl;
    }

    public void setFlicker(boolean bl) {
        this.flicker = bl;
    }

    @Override
    public void setAlpha(float f) {
        super.setAlpha(f);
    }

    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float f) {
        if (!this.flicker || this.age < this.maxAge / 3 || (this.age + this.maxAge) / 3 % 2 == 0) {
            super.buildGeometry(vertexConsumer, camera, f);
        }

    }

    public void tick() {
        super.tick();
        if (this.trail && this.age < this.maxAge / 2 && (this.age + this.maxAge) % 2 == 0) {
            MinecraftRocketParticle explosion = new MinecraftRocketParticle(this.world, this.x, this.y, this.z, 0.0, 0.0, 0.0, this.particleManager, this.spriteProvider);
            explosion.setAlpha(0.99F);
            explosion.setColor(this.red, this.green, this.blue);
            explosion.age = explosion.maxAge / 2;
            if (this.field_3802) {
                explosion.field_3802 = true;
                explosion.field_3801 = this.field_3801;
                explosion.field_3800 = this.field_3800;
                explosion.field_3799 = this.field_3799;
            }

            explosion.flicker = this.flicker;
            this.particleManager.addParticle(explosion);
        }

    }
}