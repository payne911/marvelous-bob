package com.marvelousbob.common.network.register.dto;

public final class IndexedGameStateDto extends IndexedDto<GameStateDto> {
    public IndexedGameStateDto() {
    }

    public IndexedGameStateDto(GameStateDto dto, long index) {
        super(dto, index);
    }
}
