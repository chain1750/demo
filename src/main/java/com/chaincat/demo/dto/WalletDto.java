package com.chaincat.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 钱包DTO
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletDto {

    @NotNull(message = "金额不能为空")
    private Double amount;
}
