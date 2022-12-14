package com.chaincat.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chaincat.demo.common.data.PageResult;
import com.chaincat.demo.common.exception.BusinessException;
import com.chaincat.demo.common.security.AccessContext;
import com.chaincat.demo.dao.WalletDao;
import com.chaincat.demo.dao.WalletStatementDao;
import com.chaincat.demo.entity.Wallet;
import com.chaincat.demo.entity.WalletStatement;
import com.chaincat.demo.enums.BalanceChangeType;
import com.chaincat.demo.enums.error.WalletError;
import com.chaincat.demo.service.IWalletService;
import com.chaincat.demo.vo.WalletStatementVo;
import com.chaincat.demo.vo.WalletVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 钱包业务实现
 * @author Chain
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements IWalletService {

    private final WalletDao walletDao;

    private final WalletStatementDao walletStatementDao;

    @Override
    public WalletVo getBalance() {
        Wallet wallet = getWallet();
        return new WalletVo(wallet.getBalance().doubleValue());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void changeBalance(Double amount, BalanceChangeType type) {
        /*
         * 获取钱包
         */
        Wallet wallet = getWallet();
        if (Objects.isNull(wallet)) {
            throw new BusinessException(WalletError.WALLET_NOT_FOUND);
        }
        /*
         * 消费时判断余额充足
         */
        BigDecimal amountB = BigDecimal.valueOf(amount);
        if (BalanceChangeType.CONSUME.equals(type) && wallet.getBalance().compareTo(amountB) < 0) {
            throw new BusinessException(WalletError.BALANCE_NOT_ENOUGH);
        }
        /*
         * 设置余额，并更新
         */
        setBalance(wallet, amountB, type);
        int result = walletDao.updateById(wallet);
        /*
         * 添加乐观锁后，更新时会自动变更版本号，如果版本号不等，则更新失败
         * 这里重试更新
         */
        while (result == 0) {
            wallet = getWallet();
            setBalance(wallet, amountB, type);
            result = walletDao.updateById(wallet);
        }
        /*
         * 更新成功添加钱包金额变更明细
         */
        addWalletStatement(wallet.getId(), amount, type);
    }

    @Override
    public void bill(PageResult<WalletStatementVo> pageResult) {
        Wallet wallet = getWallet();
        if (Objects.isNull(wallet)) {
            throw new BusinessException(WalletError.WALLET_NOT_FOUND);
        }
        Page<WalletStatement> param = Page.of(pageResult.getCurrent(), pageResult.getSize());
        Page<WalletStatement> result = walletStatementDao.selectPage(param, new LambdaQueryWrapper<WalletStatement>()
                .eq(WalletStatement::getWalletId, wallet.getId())
                .orderByDesc(WalletStatement::getCreateTime));
        pageResult.setTotal(result.getTotal());
        pageResult.setRecords(result.getRecords().stream().map(e ->
                new WalletStatementVo(e.getCreateTime(), e.getAmount().doubleValue(), BalanceChangeType.get(e.getType()))
        ).collect(Collectors.toList()));
    }

    /**
     * 获取用户钱包
     * @return 钱包
     */
    private Wallet getWallet() {
        Long userId = AccessContext.get();
        return walletDao.selectOne(new LambdaQueryWrapper<Wallet>()
                .eq(Wallet::getUserId, userId));
    }

    /**
     * 添加钱包金额变更明细
     * @param walletId 钱包ID
     * @param amount 金额
     * @param type 类型
     */
    private void addWalletStatement(Long walletId, Double amount, BalanceChangeType type) {
        WalletStatement walletStatement = new WalletStatement();
        walletStatement.setWalletId(walletId);
        walletStatement.setAmount(BigDecimal.valueOf(amount));
        walletStatement.setType(type.getType());
        int result = walletStatementDao.insert(walletStatement);
        log.info("添加钱包金额变更明细：{}，{}", walletStatement, result);
    }

    /**
     * 设置余额
     * @param wallet 钱包
     * @param amount 金额
     * @param type 类型
     */
    private void setBalance(Wallet wallet, BigDecimal amount, BalanceChangeType type) {
        switch (type) {
            case CONSUME:
                // 消费减余额
                wallet.setBalance(wallet.getBalance().subtract(amount));
                break;
            case REFUND:
                // 退款加余额
                wallet.setBalance(wallet.getBalance().add(amount));
                break;
            default:
                // 其他类型报错
                throw new IllegalArgumentException("余额变更类型错误");
        }
    }
}