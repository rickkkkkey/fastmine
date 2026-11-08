package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;

public class ExampleModClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// Intercept ".fastmine <speed>" before it is sent to the server.
		// Returning false cancels the chat message.
		ClientSendMessageEvents.ALLOW_CHAT.register(message -> !FastMineState.handleChatMessage(message));
	}
}
