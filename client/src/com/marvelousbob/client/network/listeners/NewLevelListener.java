package com.marvelousbob.client.network.listeners;

import com.esotericsoftware.kryonet.Connection;
import com.marvelousbob.client.MarvelousBob;
import com.marvelousbob.common.mapper.LevelMapper;
import com.marvelousbob.common.model.entities.Level;
import com.marvelousbob.common.network.listeners.AbstractListener;
import com.marvelousbob.common.network.register.dto.NewLevelDto;

public class NewLevelListener extends AbstractListener<NewLevelDto> {

    private final LevelMapper mapper;
    private final MarvelousBob marvelousBob;

    public NewLevelListener(MarvelousBob marvelousBob) {
        super(NewLevelDto.class);
        this.mapper = new LevelMapper();
        this.marvelousBob = marvelousBob;
    }

    @Override
    public void accept(Connection connection, NewLevelDto levelDto) {
        Level level = mapper.toLevel(levelDto);
        marvelousBob.getGameScreen().getController().getGameWorld().setLevel(level);
        // todo: create a new GameStateUpdater?
    }
}
