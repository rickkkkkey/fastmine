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

@Mixin(MultiPlayerGameMode.class)
public class FastBreakMixin {

    @Shadow private int destroyDelay;
    @Shadow private float destroyProgress;

    @Inject(method = "continueDestroyBlock", at = @At("HEAD"))
    private void onContinueDestroyBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        this.destroyDelay = 0;

        if (FastMineState.speedMultiplier > 1.0f && this.destroyProgress > 0.0f) {
            this.destroyProgress += (0.05f * (FastMineState.speedMultiplier - 1.0f));
            if (this.destroyProgress > 1.0f) {
                this.destroyProgress = 1.0f;
            }
        }
    }
}
