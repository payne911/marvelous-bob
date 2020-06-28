package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.entities.GameWorld;
import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GameInitializationDto implements Dto {

    public GameWorld newGameWorld;
    public UUID currentPlayerId;
}
