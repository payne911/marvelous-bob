package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class SpawnPointDto implements Dto {
    public float x, y, size;
    public int colorBits;
}
