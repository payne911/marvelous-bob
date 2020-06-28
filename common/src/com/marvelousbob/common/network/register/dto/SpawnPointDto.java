package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.graphics.Color;
import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpawnPointDto implements Dto {

    public float x, y, size;
    public float hp, maxHp;
    public UUID uuid;
    public Color colorBits;
}
