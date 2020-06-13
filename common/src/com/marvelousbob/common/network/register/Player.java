package com.marvelousbob.common.network.register;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Player extends Timestamped {

    private float speed = 20;
    private float size = 40;
    private float currX, currY, destX, destY;
}
