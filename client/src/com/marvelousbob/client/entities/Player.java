package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import space.earlygrey.shapedrawer.ShapeDrawer;

/**
 * Container of the visual representation of a {@link PlayerDto}. Call {@link
 * #updateFromDto(PlayerDto)} to update it accordingly. todo: introduce "PlayerContainer" in
 * `GameScreen` and call `updateFromDto` accordingly
 */
@Slf4j
public abstract class Player implements Identifiable, Drawable {

    @Getter
    @Deprecated
    private PlayerDto playerDto;
    private int colorIndex;
    private long timestamp; // todo: is that useful?
    private float speed = 20;
    private float size = 40;
    private Vector2 currentPos;
    private Vector2 destination;

    @Getter
    private final UUID uuid;

    public Player(PlayerDto playerDto) {
        this.uuid = playerDto.getUuid();
        this.playerDto = playerDto;
        updateFromDto(playerDto);
    }

    public void updateFromDto(PlayerDto input) {
        this.colorIndex = input.getColorIndex();
        this.timestamp = input.getTimestamp();
        this.speed = input.getSpeed();
        this.size = input.getSize();
        this.currentPos = new Vector2(input.getCurrX(), input.getCurrY());
    }

    @Override
    public void drawMe(ShapeDrawer shapeDrawer) {
        shapeDrawer.setColor(GameConstant.playerColors.get(playerDto.getColorIndex()));
    }
}
