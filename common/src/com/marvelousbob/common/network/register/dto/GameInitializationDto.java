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
    public NewLevelDto currentLevel;

    /* New Player */
    public UUID currentPlayerId;
    public float initPosX, initPosY;
    public int colorIndex;

    /* Game State */
    public ArrayList<PlayerDto> otherPlayers;
    public ArrayList<NewEnemyDto> enemies;
}
