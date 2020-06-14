package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class Ping implements Timestamped, Dto {

    private long timestamp;
}
