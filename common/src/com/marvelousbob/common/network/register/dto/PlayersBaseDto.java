package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersBaseDto implements Dto {

    public float x, y, width, height;
    public float hp, maxHp;
    public UUID uuid;
}
