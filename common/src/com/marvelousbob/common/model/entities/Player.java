package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Container of the visual representation of a {@link PlayerDto}.
 */
@Data
@Slf4j
public abstract class Player implements Identifiable, Drawable {

    @Getter
    @Deprecated
    private PlayerDto playerDto;
    @Deprecated
    protected int colorIndex;

    protected float hp, maxHp; // todo: health bar
    protected float pointAtAngle;
    protected float speed;
    protected float size;
    protected Color color;
    protected Vector2 currentPos;
    protected Vector2 destination;

    @Getter
    private final UUID uuid;

    public Player(
            float hp,
            float maxHp,
            float pointAtAngle,
            Color color,
            float speed,
            float size,
            Vector2 currentPos,
            UUID uuid) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.pointAtAngle = pointAtAngle;
        this.color = color;
        this.speed = speed;
        this.size = size;
        this.currentPos = currentPos;
        this.destination = currentPos;
        this.uuid = uuid;
    }

    public Player(PlayerDto playerDto) {
        this.uuid = playerDto.getUuid();
        this.playerDto = playerDto;
        this.speed = 20;
        this.size = 40;
        updateFromDto(playerDto);
    }

    public void updateFromDto(PlayerDto input) {
        this.colorIndex = input.colorIndex;
        this.speed = input.speed;
        this.size = input.size;
        this.currentPos = new Vector2(input.currX, input.currY);
        this.destination = new Vector2(input.destX, input.destY);
        this.hp = input.hp;
        this.maxHp = input.maxHp;
        this.pointAtAngle = input.pointAtAngle;
    }
}
