package com.example.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

/**
 * Cancels fall damage by telling the server we are on the ground while falling.
 * Toggle with ".nofall" in chat.
 */
public final class NoFall {
    // Vanilla fall damage starts after 3 blocks; start spoofing a bit earlier.
    private static final double FALL_THRESHOLD = 2.0;

    private NoFall() {
    }

    public static void tick(Minecraft mc) {
        LocalPlayer player = mc.player;
        if (!FastMineState.noFall || player == null || player.connection == null) {
            return;
        }
        if (player.isCreative() || player.isSpectator() || player.isFallFlying() || player.onGround()) {
            return;
        }
        if (player.fallDistance > FALL_THRESHOLD) {
            player.connection.send(new ServerboundMovePlayerPacket.StatusOnly(true, player.horizontalCollision));
        }
    }
}
