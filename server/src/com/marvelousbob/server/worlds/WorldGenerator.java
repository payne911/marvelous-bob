package com.marvelousbob.server.worlds;

import com.marvelousbob.common.network.register.dto.LevelInitializationDto;

public interface WorldGenerator {

    LevelInitializationDto getWorld();

}
