package com.marvelousbob.common.network.register.dto;

import com.marvelousbob.common.network.register.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Msg implements Timestamped {

    private String msg;
    private long timestamp;

    public Msg(String msg) {
        this.msg = msg;
        this.timestamp = -1;
    }
}
