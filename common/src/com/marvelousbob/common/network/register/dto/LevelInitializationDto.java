package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class LevelInitializationDto implements Dto {
    public ArrayList<WallDto> walls;
    public ArrayList<BaseDto> bases;
    public ArrayList<SpawnPointDto> spawnPointDtos;
}
