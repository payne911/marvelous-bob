package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class MoveAction implements Timestamped {

    private long timestamp;
    private int id;
    private float deltaX, deltaY;
}
