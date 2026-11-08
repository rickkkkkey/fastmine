package com.example.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.entity.Entity;

/**
 * Makes the server treat hits as critical hits by sending a few tiny "mid-air"
 * movement packets right before the attack, so the server sees the player as
 * falling. Toggle with ".crit" in chat.
 *
 * Limits: the server still requires a charged attack (>90% cooldown), and
 * vanilla does not give critical hits to a sprinting player, so stop sprinting
 * when you swing.
 */
public final class Criticals {
    private Criticals() {
    }

    public static void onAttack(LocalPlayer player, Entity target) {
        if (!FastMineState.criticals || player.connection == null) {
            return;
        }
        // Only needed when standing on the ground; skip cases where crits can't happen anyway.
        if (!player.onGround() || player.isInWater() || player.isInLava() || player.onClimbable()
            || player.isPassenger() || player.isFallFlying() || player.getAbilities().flying) {
            return;
        }

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        send(player, x, y + 0.0625, z, false);
        send(player, x, y, z, false);
        send(player, x, y + 1.1E-5, z, false);
        send(player, x, y, z, false);
    }

    private static void send(LocalPlayer player, double x, double y, double z, boolean onGround) {
        player.connection.send(new ServerboundMovePlayerPacket.Pos(x, y, z, onGround, player.horizontalCollision));
    }
}
