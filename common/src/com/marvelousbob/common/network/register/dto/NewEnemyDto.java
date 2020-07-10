package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.entities.dynamic.enemies.Enemy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewEnemyDto implements Dto {

    public Enemy enemy;
}
