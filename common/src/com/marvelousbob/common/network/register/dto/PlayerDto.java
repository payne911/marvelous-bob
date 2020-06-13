package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto implements Identifiable, Timestamped {
    private UUID id;
    private long timestamp;
    private float speed = 20;
    private float size = 40;
    private float currX, currY, destX, destY;

    public PlayerDto(UUID id) {
        this.id = id;
        timestamp = System.currentTimeMillis();
    }
}
