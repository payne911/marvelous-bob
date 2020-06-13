package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UUID {
    private static final Random random = new Random();
    private String id;

    public static UUID randomUUID() {
        byte[] bytes = new byte[8];
        random.nextBytes(bytes);
        return new UUID(new String(bytes));
    }

}
