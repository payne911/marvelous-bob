package com.marvelousbob.common.register;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MoveAction extends Timestamped {

    private int id;
    private float deltaX, deltaY;
}
