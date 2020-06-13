package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MoveAction extends Timestamped {

    private int id;
    private float deltaX, deltaY;
}
