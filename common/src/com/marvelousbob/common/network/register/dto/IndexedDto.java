package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class IndexedDto<T extends Dto> implements Dto {
    T dto;
    long index;
}
