package com.example.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Simple settings screen built only from vanilla buttons, so it does not depend
 * on custom rendering code. Open it with ".gui" in chat.
 */
public class FastMineScreen extends Screen {
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING = 24;
    private static final float SPEED_STEP = 0.5f;

    private Button speedButton;
    private Button noFallButton;

    public FastMineScreen() {
        super(Component.literal("FastMine"));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = this.height / 2 - 60;

        // Header (disabled button used as a label)
        Button header = Button.builder(Component.literal("§lFastMine Settings"), b -> { })
            .bounds(centerX - BUTTON_WIDTH / 2, y, BUTTON_WIDTH, BUTTON_HEIGHT)
            .build();
        header.active = false;
        this.addRenderableWidget(header);
        y += SPACING + 6;

        // Speed: [-] Speed: 1.2x [+]
        int small = 20;
        int mid = BUTTON_WIDTH - 2 * small - 8;
        int left = centerX - BUTTON_WIDTH / 2;

        this.addRenderableWidget(Button.builder(Component.literal("-"), b -> changeSpeed(-SPEED_STEP))
            .bounds(left, y, small, BUTTON_HEIGHT).build());

        speedButton = Button.builder(speedLabel(), b -> { })
            .bounds(left + small + 4, y, mid, BUTTON_HEIGHT).build();
        speedButton.active = false;
        this.addRenderableWidget(speedButton);

        this.addRenderableWidget(Button.builder(Component.literal("+"), b -> changeSpeed(SPEED_STEP))
            .bounds(left + small + 4 + mid + 4, y, small, BUTTON_HEIGHT).build());
        y += SPACING;

        // NoFall toggle
        noFallButton = Button.builder(noFallLabel(), b -> {
                FastMineState.noFall = !FastMineState.noFall;
                noFallButton.setMessage(noFallLabel());
            })
            .bounds(left, y, BUTTON_WIDTH, BUTTON_HEIGHT).build();
        this.addRenderableWidget(noFallButton);
        y += SPACING + 6;

        // Close
        this.addRenderableWidget(Button.builder(Component.literal("Close"), b -> this.onClose())
            .bounds(left, y, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    private void changeSpeed(float delta) {
        float next = FastMineState.speedMultiplier + delta;
        next = Math.round(next * 10.0f) / 10.0f; // avoid float drift like 1.7000001
        FastMineState.speedMultiplier = Math.max(0.1f, Math.min(10.0f, next));
        speedButton.setMessage(speedLabel());
    }

    private Component speedLabel() {
        return Component.literal("Mine Speed: " + FastMineState.speedMultiplier + "x");
    }

    private Component noFallLabel() {
        return Component.literal("NoFall: " + (FastMineState.noFall ? "§aON" : "§cOFF"));
    }
}
