package com.chaincat.demo.vo;

import com.chaincat.demo.enums.BalanceChangeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 钱包金额变动明细VO
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletStatementVo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    private Double amount;

    private BalanceChangeType type;
}
