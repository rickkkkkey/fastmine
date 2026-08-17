package com.example.client.mixin;

import com.example.client.FastMineState;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MultiPlayerGameMode.class, remap = false)
public class FastBreakMixin {

    @Shadow private int destroyDelay;
    @Shadow private float destroyProgress;

    @Inject(method = "startDestroyBlock", at = @At("HEAD"), require = 0)
    private void onStartDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FastMineState.speedMultiplier > 1.0f) {
            this.destroyDelay = 0;
            applySpeedBoost();
        }
    }

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"), require = 0)
    private void onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        if (FastMineState.speedMultiplier > 1.0f) {
            this.destroyDelay = 0;
            applySpeedBoost();
        }
    }

    private void applySpeedBoost() {
        if (this.destroyProgress > 0.0f && this.destroyProgress < 1.0f) {
            float boost = 0.02f * (FastMineState.speedMultiplier - 1.0f);
            this.destroyProgress += boost;

            if (this.destroyProgress > 1.0f) {
                this.destroyProgress = 1.0f;
            }
        }
    }
}
