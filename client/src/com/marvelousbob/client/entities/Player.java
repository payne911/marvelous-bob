package com.marvelousbob.client.entities;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Container of the visual representation of a {@link PlayerDto}.
 */
@Slf4j
public abstract class Player implements Identifiable, Drawable {

    @Getter
    @Deprecated
    private PlayerDto playerDto;

    protected int colorIndex;
    protected long timestamp; // todo: is that useful?
    protected float speed = 20;
    protected float size = 40;
    protected Vector2 currentPos;
    protected Vector2 destination;

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
}
