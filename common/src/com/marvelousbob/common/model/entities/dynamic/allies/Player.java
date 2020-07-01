package com.marvelousbob.common.model.entities.dynamic.allies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.entities.Drawable;
import com.marvelousbob.common.model.entities.Movable;
import com.marvelousbob.common.network.register.dto.Dto;
import com.marvelousbob.common.network.register.dto.PlayerDto;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
@NoArgsConstructor
public abstract class Player implements Identifiable, Drawable, Movable, Dto {

    @Deprecated
    private PlayerDto playerDto;
    @Deprecated
    protected int colorIndex;

    protected float hp, maxHp; // todo: health bar
    protected float speed;
    protected float size;
    protected Color color;
    protected Vector2 currCenterPos;
    protected Vector2 destination;

    /**
     * {@code zero} degrees point to the right. todo: confirm this is right
     */
    protected float pointAtAngle;

    @EqualsAndHashCode.Include
    private UUID uuid;

    public Player(
            float hp,
            float maxHp,
            float pointAtAngle,
            Color color,
            float speed,
            float size,
            Vector2 currCenterPos,
            UUID uuid) {
        this.hp = hp;
        this.maxHp = maxHp;
        this.pointAtAngle = pointAtAngle;
        this.color = color;
        this.speed = speed;
        this.size = size;
        this.currCenterPos = currCenterPos;
        this.destination = currCenterPos;
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
        this.currCenterPos = new Vector2(input.currX, input.currY);
        this.destination = new Vector2(input.destX, input.destY);
        this.hp = input.hp;
        this.maxHp = input.maxHp;
        this.pointAtAngle = input.pointAtAngle;
    }

    // ===================================
    // Utility methods

    @Override
    public float getCurrCenterX() {
        return currCenterPos.x;
    }

    @Override
    public float getCurrCenterY() {
        return currCenterPos.y;
    }

    @Override
    public void setCurrCenterX(float x) {
        currCenterPos.x = x;
    }

    @Override
    public void setCurrCenterY(float y) {
        currCenterPos.y = y;
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

    public float getHalfSize() {
        return this.size / 2;
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
        return getCurrCenterX() == otherPlayer.getCurrCenterX()
                && getCurrCenterY() == otherPlayer.getCurrCenterY();
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
