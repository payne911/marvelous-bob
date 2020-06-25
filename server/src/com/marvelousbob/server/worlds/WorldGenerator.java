package com.marvelousbob.server.worlds;

import com.marvelousbob.common.network.register.dto.NewLevelDto;

public interface WorldGenerator {

    NewLevelDto getWorld();

}
