package com.marvelousbob.server.worlds;

import com.marvelousbob.common.network.register.dto.NewGameWorldDto;

public interface WorldGenerator {

    NewGameWorldDto getWorld();

}
