package com.marvelousbob.server.worlds;

import static com.badlogic.gdx.graphics.Color.PINK;

import com.marvelousbob.common.network.constants.GameConstant;
import com.marvelousbob.common.network.register.dto.NewGameWorldDto;
import com.marvelousbob.common.network.register.dto.PlayersBaseDto;
import com.marvelousbob.common.network.register.dto.SpawnPointDto;
import com.marvelousbob.common.network.register.dto.WallDto;
import java.util.ArrayList;

public class StaticSimpleWorldGenerator implements WorldGenerator {

    @Override
    public NewGameWorldDto getWorld() {
        ArrayList<PlayersBaseDto> bases = new ArrayList<>();
        bases.add(new PlayersBaseDto(GameConstant.sizeX / 2f, GameConstant.sizeY / 2f, 50, 50));

        ArrayList<WallDto> walls = new ArrayList<>();
        walls.add(new WallDto(30, 50, 2, 60));
        walls.add(new WallDto(70, 150, 2, 100));
        walls.add(new WallDto(170, 360, 30, 2));

        ArrayList<SpawnPointDto> enemySpawnPoints = new ArrayList<>();
        enemySpawnPoints.add(new SpawnPointDto(70, 70, 15, PINK));
        enemySpawnPoints.add(new SpawnPointDto(270, 270, 15, PINK));

        return new NewGameWorldDto(walls, bases, enemySpawnPoints);
    }
}
