package com.marvelousbob.common.network.register.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class PlayerConnectionDto implements Dto {

   public PlayerType playerType;
}
