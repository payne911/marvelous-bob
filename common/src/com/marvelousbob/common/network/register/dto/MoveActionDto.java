package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;

@Data
public final class MoveActionDto implements Timestamped, Dto {
    private long timestamp;
    private UUID playerId;
    private float destX, destY;

}
