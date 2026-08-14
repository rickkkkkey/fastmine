package com.example.client;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class FastMineState {
    public static float speedMultiplier = 1.0f;

    public static boolean handleChatMessage(String message) {
        if (message.startsWith(".fastmine ")) {
            try {
                String valStr = message.substring(".fastmine ".length()).trim();
                float val = Float.parseFloat(valStr);
                
                if (val < 1.0f) {
                    val = 1.0f;
                }
                
                speedMultiplier = val;
                
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.sendSystemMessage(
                        Component.literal("§a[FastMine] Speed set to: " + speedMultiplier + "x")
                    );
                }
            } catch (NumberFormatException e) {
                if (Minecraft.getInstance().player != null) {
                    Minecraft.getInstance().player.sendSystemMessage(
                        Component.literal("§c[FastMine] Invalid number format! Example: .fastmine 2.5")
                    );
                }
            }
            return true;
        }
        return false;
    }
}
