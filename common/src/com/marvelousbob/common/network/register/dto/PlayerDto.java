package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class PlayerDto implements Identifiable, Timestamped, Dto {

    private int colorIndex;
    private UUID uuid;
    private long timestamp;
    private float speed = 20;
    private float size = 40;
    private float currX, destX, currY, destY;

    public PlayerDto(UUID uuid) {
        this.uuid = uuid;
        stampNow();
    }

    /**
     * @param otherPlayerDto input compared to
     * @return if all the values are the same, <b>excluding timestamp</b>. The <b>{@link
     * #colorIndex} is not</b> considered because the {@link UUID} in enough for that purpose.
     */
    public boolean isSameStateSamePlayerWithoutTime(PlayerDto otherPlayerDto) {
        return isSamePosition(otherPlayerDto)
                && hasSameDestination(otherPlayerDto)
                && speed == otherPlayerDto.speed
                && size == otherPlayerDto.size
                && uuid.getStringId().equals(otherPlayerDto.getUuid().getStringId());
    }

    public boolean isSamePosition(PlayerDto otherPlayerDto) {
        return currX == otherPlayerDto.currX && currY == otherPlayerDto.currY;
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
        return uuid.getStringId().hashCode();
    }
}
