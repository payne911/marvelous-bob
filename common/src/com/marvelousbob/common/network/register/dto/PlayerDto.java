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
    private UUID id;
    private long timestamp;
    private float speed = 20;
    private float size = 40;
    private float currX, currY, destX, destY;

    public PlayerDto(UUID id) {
        this.id = id;
        timestamp = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerDto playerDto = (PlayerDto) o;
        return id.equals(playerDto.id);
    }

    @Override
    public int hashCode() {
        return id.getId().hashCode();
    }
}
