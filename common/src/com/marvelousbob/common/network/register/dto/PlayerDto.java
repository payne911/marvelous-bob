package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.model.MarvelousBobException;
import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public final class PlayerDto implements Identifiable, Timestamped, Dto {

    private int colorIndex;
    private UUID uuid;
    private long timestamp; // todo: is that useful?
    private float speed = 20;
    private float size = 40;
    private float currX, destX, currY, destY;

    public PlayerDto(UUID uuid) {
        this.uuid = uuid;
        stampNow();
    }

    /**
     * Copies data from the input into the object which calls the function.
     */
    public void updateFromDto(PlayerDto otherPlayerDto) {
        if (!isSameColor(otherPlayerDto)) { // todo: what about different UUID case?
            throw new MarvelousBobException(
                    "Cannot update a PlayerDto using another PlayerDto which does not have the same color.");
        }

        speed = otherPlayerDto.speed;
        size = otherPlayerDto.size;
        currX = otherPlayerDto.currX;
        destX = otherPlayerDto.destX;
        currY = otherPlayerDto.currY;
        destY = otherPlayerDto.destY;

        stampNow();
    }

    /**
     * @param otherPlayerDto input compared to
     * @return if all the values are the same, <b>excluding timestamp</b>. The <b>{@link
     * #colorIndex} is not</b> considered because the {@link UUID} in enough for that purpose.
     */
    public boolean hasAllMatchingFieldsExceptTimeAndColorIndex(PlayerDto otherPlayerDto) {
        return isSamePosition(otherPlayerDto)
                && hasSameDestination(otherPlayerDto)
                && isSameSpeed(otherPlayerDto)
                && isSameSize(otherPlayerDto)
                && isSameUuid(otherPlayerDto);
    }

    private boolean isSameUuid(PlayerDto otherPlayerDto) {
        return uuid.equals(otherPlayerDto.getUuid());
    }

    public boolean isSameColorButDifferentPlayer(PlayerDto other) {
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

    public boolean hasSameDestination(PlayerDto otherPlayerDto) {
        return destX == otherPlayerDto.destX && destY == otherPlayerDto.destY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PlayerDto playerDto = (PlayerDto) o;
        return uuid.equals(playerDto.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}
