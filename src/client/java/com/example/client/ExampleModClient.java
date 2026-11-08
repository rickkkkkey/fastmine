package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Intercept ".fastmine <speed>" and ".nofall" before they are sent to the server.
		// Returning false cancels the chat message.
		ClientSendMessageEvents.ALLOW_CHAT.register(message -> !FastMineState.handleChatMessage(message));

		ClientTickEvents.END_CLIENT_TICK.register(NoFall::tick);
	}
}
