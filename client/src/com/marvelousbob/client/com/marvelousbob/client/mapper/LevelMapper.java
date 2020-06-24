package com.marvelousbob.client.com.marvelousbob.client.mapper;

import com.marvelousbob.client.entities.Level;
import com.marvelousbob.client.entities.Wall;
import com.marvelousbob.common.network.register.dto.LevelInitializationDto;
import com.marvelousbob.common.network.register.dto.WallDto;

public class LevelMapper {
    public Level toLevel(LevelInitializationDto levelInitializationDto) {
        return null;
    }

    public Wall toWall(WallDto wallDto) {
        Wall wall = new Wall(wallDto.getX1(), wallDto.getY1(), wallDto.getWidth(), wallDto.getHeight());
        return wall;
    }
}
