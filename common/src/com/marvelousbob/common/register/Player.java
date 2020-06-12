package com.marvelousbob.common.register;

import lombok.Data;

@Data
public class Player {

    private float speed = 20;
    private float currX, currY, destX, destY;
}
