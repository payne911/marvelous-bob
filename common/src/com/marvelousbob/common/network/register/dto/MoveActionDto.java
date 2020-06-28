package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.PlayerBroadcastable;
import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;

@Data
public final class MoveActionDto implements PlayerBroadcastable, Timestamped, Dto {

    public UUID sourcePlayerUuid;
    public long timestamp;
    public float destX, destY;
}
