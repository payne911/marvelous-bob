package com.marvelousbob.common.network.register.dto;

import lombok.Data;

@Data
public class PlayerDisconnectionDto implements Dto {
    private UUID playerId;
}
