package com.marvelousbob.common.register;

import lombok.Data;

@Data
public abstract class Timestamped {

    protected long timestamp;

    public void stampNow() {
        timestamp = System.currentTimeMillis();
    }
}
