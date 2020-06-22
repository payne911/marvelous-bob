package com.marvelousbob.common.network.register.dto;

public final class IndexedMoveActionDto extends IndexedDto<MoveActionDto> {
    public IndexedMoveActionDto() {
    }

    public IndexedMoveActionDto(MoveActionDto dto, long index) {
        super(dto, index);
    }
}
