package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.model.Identifiable;
import com.marvelousbob.common.utils.UUID;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayersBaseDto implements Dto, Identifiable {

    public UUID uuid;
    public List<UUID> enemiesToRemove;
    public float x, y, width, height, hp, maxHp;
}
