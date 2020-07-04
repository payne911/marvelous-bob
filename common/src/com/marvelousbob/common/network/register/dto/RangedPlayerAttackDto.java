package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.math.Vector2;
import com.marvelousbob.common.network.register.Timestamped;
import com.marvelousbob.common.utils.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public final class RangedPlayerAttackDto implements Dto, Timestamped {

    public Vector2 initPos, clickedPos;
    public float bulletSpeed, bulletRadius;
    public UUID uuid;
    public long timestamp;

    public RangedPlayerAttackDto(
            Vector2 initPos, Vector2 clickedPos,
            float bulletSpeed, float bulletRadius,
            UUID uuid) {
        this.initPos = initPos;
        this.clickedPos = clickedPos;
        this.bulletRadius = bulletRadius;
        this.bulletSpeed = bulletSpeed;
        this.uuid = uuid;
        stampNow();
    }
}
