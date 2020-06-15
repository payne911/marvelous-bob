package com.marvelousbob.common.network.register.dto;

import com.badlogic.gdx.math.MathUtils;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public final class UUID implements Dto {

    private static final Random random = MathUtils.random;
    private String stringId;

    private static int counter = 0;

    public static UUID randomUUID() {
//        byte[] bytes = new byte[8];
//        random.nextBytes(bytes);
//        return new UUID(new String(bytes));
        return new UUID(Integer.toString(counter++));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUID uuid = (UUID) o;
        return Integer.parseInt(stringId) == Integer.parseInt(uuid.stringId);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(Integer.parseInt(stringId));
    }
}
