package com.chaincat.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 钱包
 * @author Chain
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private BigDecimal balance;

    private Long userId;

    @Version
    private Integer version;
}
