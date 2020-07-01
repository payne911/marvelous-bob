package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public final class PlayerDto implements Identifiable, Dto {

    @EqualsAndHashCode.Include
    public UUID uuid;

    public ArrayList<BulletDto> bullets;
    public int colorIndex;
    public float speed = 20;
    public float size = 40;
    public float currX, destX, currY, destY;
    public float hp, maxHp;
    public float mouseAngleRelativeToCenter;

    /**
     * Calls the other constructor with an empty list of bullets.
     */
    public PlayerDto(UUID uuid) {
        this(uuid, new ArrayList<>());
    }

    public PlayerDto(UUID uuid, ArrayList<BulletDto> bullets) {
        this.uuid = uuid;
        this.bullets = bullets;
    }

    /**
     * Copies data from the input into the object which calls the function.
     */
    public void updateFromDto(PlayerDto otherPlayerDto) {
        if (!isSameColorAndUuid(otherPlayerDto)) {
            throw new MarvelousBobException(
                    "Cannot update a PlayerDto using non-matching UUID and colorIndex.");
        }

        speed = otherPlayerDto.speed;
        size = otherPlayerDto.size;
        currX = otherPlayerDto.currX;
        destX = otherPlayerDto.destX;
        currY = otherPlayerDto.currY;
        destY = otherPlayerDto.destY;
        hp = otherPlayerDto.hp;
        maxHp = otherPlayerDto.maxHp;
        mouseAngleRelativeToCenter = otherPlayerDto.mouseAngleRelativeToCenter;
        bullets = otherPlayerDto.bullets; // todo: deep copy?
    }

    /**
     * @param otherPlayerDto input compared to
     * @return if all the values are the same, <b>excluding timestamp</b>.
     */
    public boolean hasAllMatchingFieldsExceptTimestamp(PlayerDto otherPlayerDto) {
        return isSamePosition(otherPlayerDto)
                && isSameUuid(otherPlayerDto)
                && isSameDestination(otherPlayerDto)
                && isSameSpeed(otherPlayerDto)
                && isSameSize(otherPlayerDto)
                && isSameColor(otherPlayerDto);
    }

    private boolean isSameUuid(PlayerDto otherPlayerDto) {
        return uuid.equals(otherPlayerDto.getUuid());
    }

    public boolean isSameColorAndUuid(PlayerDto other) {
        return isSameColor(other) && isSameUuid(other);
    }

    public boolean isSamePosition(PlayerDto otherPlayerDto) {
        return currX == otherPlayerDto.currX && currY == otherPlayerDto.currY;
    }

    public boolean isSameSize(PlayerDto otherPlayerDto) {
        return size == otherPlayerDto.size;
    }

    public boolean isSameSpeed(PlayerDto otherPlayerDto) {
        return speed == otherPlayerDto.speed;
    }

    public boolean isSameColor(PlayerDto otherPlayerDto) {
        return colorIndex == otherPlayerDto.colorIndex;
    }

    public boolean isSameDestination(PlayerDto otherPlayerDto) {
        return destX == otherPlayerDto.destX && destY == otherPlayerDto.destY;
    }
}
