package com.chaincat.demo.controller;

import com.chaincat.demo.common.data.PageResult;
import com.chaincat.demo.common.data.Result;
import com.chaincat.demo.dto.WalletDto;
import com.chaincat.demo.enums.BalanceChangeType;
import com.chaincat.demo.service.IWalletService;
import com.chaincat.demo.vo.WalletStatementVo;
import com.chaincat.demo.vo.WalletVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 钱包接口
 * @author Chain
 */
@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final IWalletService walletService;

    @GetMapping("/getBalance")
    public Result<WalletVo> getBalance() {
        return Result.ok(walletService.getBalance());
    }

    @PostMapping("/consume")
    public Result<Void> consume(@Valid @RequestBody WalletDto walletDto) {
        walletService.changeBalance(walletDto.getAmount(), BalanceChangeType.CONSUME);
        return Result.ok();
    }

    @PostMapping("/refund")
    public Result<Void> refund(@Valid @RequestBody WalletDto walletDto) {
        walletService.changeBalance(walletDto.getAmount(), BalanceChangeType.REFUND);
        return Result.ok();
    }

    @GetMapping("/bill")
    public Result<PageResult<WalletStatementVo>> bill(@RequestParam(defaultValue = "1") Long current,
                                                      @RequestParam(defaultValue = "10") Long size) {
        PageResult<WalletStatementVo> pageResult = new PageResult<>(current, size);
        walletService.bill(pageResult);
        return Result.ok(pageResult);
    }
}
