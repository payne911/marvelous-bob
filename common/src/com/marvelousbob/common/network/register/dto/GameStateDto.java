package com.marvelousbob.common.network.register.dto;

import java.util.ArrayList;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
public final class GameStateDto implements Dto {

    public ArrayList<EnemyCollisionDto> enemyCollisions;
    public ArrayList<PlayerUpdateDto> playerUpdates;
    public ArrayList<EnemyDto> newEnemies;
    public float baseHealth;
    public long index;
}