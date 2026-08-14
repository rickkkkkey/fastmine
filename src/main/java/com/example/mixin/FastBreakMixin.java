package com.example.mixin;

import com.example.FastMineState;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class FastBreakMixin {

    @Shadow private int blockBreakingCooldown;
    @Shadow private float currentBreakingProgress;

    @Inject(method = "updateBlockBreakingProgress", at = @At("HEAD"), require = 0)
    private void onUpdateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
        // Removes the block breaking delay
        this.blockBreakingCooldown = 0;

        // Applies speed multiplier
        if (FastMineState.speedMultiplier > 1.0f && this.currentBreakingProgress > 0.0f) {
            this.currentBreakingProgress += (0.05f * (FastMineState.speedMultiplier - 1.0f));
            if (this.currentBreakingProgress > 1.0f) {
                this.currentBreakingProgress = 1.0f;
            }
        }
    }
}
