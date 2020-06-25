package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.entities.GameWorld;
import com.marvelousbob.client.entities.Level;
import com.marvelousbob.client.mapper.LevelMapper;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.LevelInitializationDto;

public class LevelInitializationListener extends AbstractListener<LevelInitializationDto> {

    private final LevelMapper mapper;
    private GameWorld gameWorld;

    public LevelInitializationListener(GameWorld gameWorld) {
        super(LevelInitializationDto.class);
        this.mapper = new LevelMapper();
        this.gameWorld = gameWorld;
    }

    @Override
    public void accept(Connection conncetion, LevelInitializationDto levelDto) {
        Level level = mapper.toLevel(levelDto);
        gameWorld.setLevel(level);
    }
}
