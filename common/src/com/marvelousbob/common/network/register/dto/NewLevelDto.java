package com.marvelousbob.common.network.register.dto;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewLevelDto implements Dto {

    public ArrayList<WallDto> walls;
    public ArrayList<PlayersBaseDto> bases;
    public ArrayList<SpawnPointDto> spawnPointDtos;
}
