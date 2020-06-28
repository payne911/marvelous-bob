package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.entities.GameWorld;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class NewGameWorldDto implements Dto {
    public GameWorld newGameWorld;
}
