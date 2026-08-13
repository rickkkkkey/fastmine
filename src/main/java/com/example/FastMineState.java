package com.example;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FastMineState {
    public static float speedMultiplier = 1.2f;

    public static boolean handleChatMessage(String message) {
        if (message.startsWith(".fastmine ")) {
            String[] args = message.split(" ");
            if (args.length > 1) {
                try {
                    float newSpeed = Float.parseFloat(args[1]);
                    if (newSpeed < 0.1f) newSpeed = 0.1f;
                    if (newSpeed > 10.0f) newSpeed = 10.0f;
                    
                    speedMultiplier = newSpeed;
                    
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.sendSystemMessage(
                            Component.literal("§a[FastMine] Speed multiplier set to: §f" + speedMultiplier + "x")
                        );
                    }
                } catch (NumberFormatException e) {
                    if (Minecraft.getInstance().player != null) {
                        Minecraft.getInstance().player.sendSystemMessage(
                            Component.literal("§c[FastMine] Invalid speed! Example usage: .fastmine 1.2")
                        );
                    }
                }
            }
            return true;
        }
        return false;
    }
}
