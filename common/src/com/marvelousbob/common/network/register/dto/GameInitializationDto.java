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

    public UUID currentPlayerId;
    public NewLevelDto firstLevel;
    public float initPosX, initPosY;
    public int colorIndex;
    public ArrayList<PlayerDto> otherPlayers;
    public ArrayList<EnemyDto> enemies;
    public float baseHealth;

}
