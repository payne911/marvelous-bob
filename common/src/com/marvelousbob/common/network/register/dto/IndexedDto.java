package com.marvelousbob.common.network.register.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class IndexedDto<T extends Dto> implements Dto {
    private T dto; // todo: this gives us a recursive (and useless) getter... do we really need it?
    private long index;
}
