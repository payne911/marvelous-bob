package com.marvelousbob.common.model;

import com.marvelousbob.common.network.register.dto.Dto;

public interface DtoUpdateable extends Dto {

    void updateFromDto(Dto dto);
}
