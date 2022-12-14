package com.chaincat.demo.service;

import com.chaincat.demo.common.data.PageResult;
import com.chaincat.demo.enums.BalanceChangeType;
import com.chaincat.demo.vo.WalletStatementVo;
import com.chaincat.demo.vo.WalletVo;

/**
 * 钱包业务接口
 * @author Chain
 */
public interface IWalletService {

    /**
     * 获取余额
     * @return 余额
     */
    WalletVo getBalance();

    /**
     * 变更余额
     * @param amount 变更金额
     * @param type 变更类型
     */
    void changeBalance(Double amount, BalanceChangeType type);

    /**
     * 获取账单
     * @param pageResult 分页参数
     */
    void bill(PageResult<WalletStatementVo> pageResult);
}
