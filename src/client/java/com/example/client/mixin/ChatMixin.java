package com.example.client.mixin;

import com.example.client.FastMineState;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ClientPacketListener.class, remap = false)
public class ChatMixin {

    @Inject(method = "sendChat", at = @At("HEAD"), cancellable = true, require = 0)
    private void onSendChat(String message, CallbackInfo ci) {
        if (FastMineState.handleChatMessage(message)) {
            ci.cancel();
        }
    }

    @Inject(method = "sendCommand", at = @At("HEAD"), cancellable = true, require = 0)
    private void onSendCommand(String command, CallbackInfo ci) {
        if (FastMineState.handleChatMessage("." + command)) {
            ci.cancel();
        }
    }
}
