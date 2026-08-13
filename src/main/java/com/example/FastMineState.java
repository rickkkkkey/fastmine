package com.example.fastbreak;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

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
                    
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.sendMessage(
                            Text.literal("§a[FastMine] Speed multiplier set to: §f" + speedMultiplier + "x"), 
                            false
                        );
                    }
                } catch (NumberFormatException e) {
                    if (MinecraftClient.getInstance().player != null) {
                        MinecraftClient.getInstance().player.sendMessage(
                            Text.literal("§c[FastMine] Invalid speed! Example usage: .fastmine 1.2"), 
                            false
                        );
                    }
                }
            }
            return true;
        }
        return false;
    }
}
