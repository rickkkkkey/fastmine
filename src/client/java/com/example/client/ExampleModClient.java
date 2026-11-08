package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionResult;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Intercept ".fastmine <speed>" and ".nofall" before they are sent to the server.
		// Returning false cancels the chat message.
		ClientSendMessageEvents.ALLOW_CHAT.register(message -> !FastMineState.handleChatMessage(message));

		ClientTickEvents.END_CLIENT_TICK.register(NoFall::tick);

		// Fires when the local player attacks an entity; Criticals sends its packets first.
		AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (world.isClientSide() && player instanceof LocalPlayer local) {
				Criticals.onAttack(local, entity);
			}
			return InteractionResult.PASS;
		});
	}
}
