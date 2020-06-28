package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.utils.UUID;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewLevelDto implements Dto {

    public ArrayList<WallDto> walls;
    public ConcurrentHashMap<UUID, PlayersBaseDto> bases;
    public ConcurrentHashMap<UUID, SpawnPointDto> spawnPointDtos;
}
