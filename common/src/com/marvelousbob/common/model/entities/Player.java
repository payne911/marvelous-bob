package com.marvelousbob.common.model.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@NoArgsConstructor
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

    @EqualsAndHashCode.Include
    @Getter
    private UUID uuid;

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

    @Deprecated
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

    // ===================================
    // Utility methods

    public float getCurrX() {
        return currentPos.x;
    }

    public float getCurrY() {
        return currentPos.y;
    }

    public void setCurrX(float x) {
        currentPos.x = x;
    }

    public void setCurrY(float y) {
        currentPos.y = y;
    }

    public float getDestX() {
        return destination.x;
    }

    public float getDestY() {
        return destination.y;
    }

    public void setDestX(float x) {
        destination.x = x;
    }

    public void setDestY(float y) {
        destination.y = y;
    }

    /**
     * @param otherPlayer input compared to
     * @return if all the values are the same, <b>excluding timestamp</b>.
     */
    public boolean hasAllMatchingFieldsExceptTimestamp(Player otherPlayer) {
        return isSamePosition(otherPlayer)
                && isSameUuid(otherPlayer)
                && isSameDestination(otherPlayer)
                && isSameSpeed(otherPlayer)
                && isSameSize(otherPlayer)
                && isSameColor(otherPlayer);
    }

    private boolean isSameUuid(Player otherPlayer) {
        return uuid.equals(otherPlayer.getUuid());
    }

    public boolean isSameColorAndUuid(Player other) {
        return isSameColor(other) && isSameUuid(other);
    }

    public boolean isSamePosition(Player otherPlayer) {
        return getCurrX() == otherPlayer.getCurrX()
                && getCurrY() == otherPlayer.getCurrY();
    }

    public boolean isSameDestination(Player otherPlayer) {
        return getDestX() == otherPlayer.getDestX()
                && getDestY() == otherPlayer.getDestY();
    }

    public boolean isSameSize(Player otherPlayer) {
        return size == otherPlayer.size;
    }

    public boolean isSameSpeed(Player otherPlayer) {
        return speed == otherPlayer.speed;
    }

    public boolean isSameColor(Player otherPlayer) {
        return colorIndex == otherPlayer.colorIndex;
    }
}
