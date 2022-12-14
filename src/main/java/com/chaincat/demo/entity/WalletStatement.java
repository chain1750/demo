package com.chaincat.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 钱包金额变动明细
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletStatement {

    @TableId(type = IdType.AUTO)
    private Long id;

    private LocalDateTime createTime;

    private Long walletId;

    private BigDecimal amount;

    private Integer type;
}
