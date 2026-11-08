package com.example.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;

public class ExampleModClient implements ClientModInitializer {
	private static KeyMapping guiKey;

	@Override
	public void onInitializeClient() {
		// Intercept ".fastmine <speed>" and ".nofall" before they are sent to the server.
		// Returning false cancels the chat message.
		ClientSendMessageEvents.ALLOW_CHAT.register(message -> !FastMineState.handleChatMessage(message));

		ClientTickEvents.END_CLIENT_TICK.register(NoFall::tick);

		// Keybind to open the settings GUI (default Page Down, changeable in Controls).
		guiKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
			"key.fastmine.gui",
			InputConstants.Type.KEYSYM,
			InputConstants.KEY_PAGEDOWN,
			KeyMapping.Category.MISC
		));

		// ".gui" sets a flag; open the screen on the next tick so it isn't
		// immediately closed by the chat screen that sent the command.
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (guiKey.consumeClick()) {
				if (client.screen == null) {
					client.setScreen(new FastMineScreen());
				}
			}
			if (FastMineState.openGui) {
				FastMineState.openGui = false;
				client.setScreen(new FastMineScreen());
			}
		});
	}
}
