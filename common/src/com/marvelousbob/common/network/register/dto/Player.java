package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.UUID;

@Data
public class Player implements Identifiable, Timestamped {
    private UUID id;
    private boolean isSelf;
    private long timestamp;
    private float speed = 20;
    private float size = 40;
    private float currX, currY, destX, destY;

    public Player(UUID id) {
        this.id = id;
        timestamp = System.currentTimeMillis();
    }
}
