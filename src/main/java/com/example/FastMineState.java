package com.example;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

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
                
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(
                        Text.literal("§a[FastMine] Speed set to: " + speedMultiplier + "x"),
                        false
                    );
                }
            } catch (NumberFormatException e) {
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(
                        Text.literal("§c[FastMine] Invalid number format! Example: .fastmine 2.5"),
                        false
                    );
                }
            }
            return true;
        }
        return false;
    }
}
