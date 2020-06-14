package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.math.MathUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UUID implements Dto {
    private static final Random random = MathUtils.random;
    private String id;

    private static int TEST = 0;

    public static UUID randomUUID() {
//        byte[] bytes = new byte[8];
//        random.nextBytes(bytes);
//        return new UUID(new String(bytes));
        return new UUID(Integer.toString(TEST++));
    }

}
