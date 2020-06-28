package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.utils.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PlayerDisconnectionDto implements Dto {
    private UUID playerUuid;
}
