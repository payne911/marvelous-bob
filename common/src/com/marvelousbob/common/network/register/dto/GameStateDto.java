package com.marvelousbob.common.network.register.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public final class GameStateDto implements Dto {

    public ArrayList<EnemyCollisionDto> enemyCollisions;
    public ArrayList<PlayerUpdateDto> playerUpdates;
    public ArrayList<NewEnemyDto> newEnemies;
    public ArrayList<PlayersBaseDto> basesHealth;
    public ArrayList<SpawnPointDto> spawnPointHealth;
    public long index;

}