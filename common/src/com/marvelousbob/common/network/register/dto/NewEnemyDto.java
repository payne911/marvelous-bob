package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;

@Data
public final class NewEnemyDto implements Dto, Identifiable {

    public UUID uuid;
    public UUID SpawnPoint;
    public EnemyType enemyType;
    public float posX, posY;
}
