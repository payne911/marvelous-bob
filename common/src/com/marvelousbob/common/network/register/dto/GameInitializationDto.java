package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public final class GameInitializationDto implements Dto {

    /* Level */
    public NewGameWorldDto newGameWorldDto;

    /* New Player */
    public UUID currentPlayerId;
    public float initPosX, initPosY; // todo probably remove if we send GameWorld
    public int colorIndex; // todo probably remove if we send GameWorld

    /* Game State */
    public ArrayList<PlayerDto> otherPlayers; // todo probably remove if we send GameWorld
    public ArrayList<NewEnemyDto> enemies; // todo probably remove if we send GameWorld
}
