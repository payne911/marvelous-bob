package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GameInitialization implements Dto {
    private GameStateDto gameStateDto;
    private UUID currentPlayerId;
}
