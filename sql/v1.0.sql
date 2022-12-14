CREATE TABLE `wallet`
(
    `id`          BIGINT         NOT NULL,
    `create_time` TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    `balance`     DECIMAL(10, 2) NOT NULL DEFAULT 0 COMMENT '余额',
    `user_id`     BIGINT         NOT NULL COMMENT '用户ID',
    `version`     INT            NOT NULL DEFAULT 0 COMMENT '版本号',

    PRIMARY KEY (`id`),
    KEY (`user_id`)
) COMMENT = '钱包';

INSERT INTO `wallet` (`id`, `balance`, `user_id`)
VALUES (1601233248933933057, 100000, 123456);

CREATE TABLE `wallet_statement`
(
    `id`          BIGINT         NOT NULL AUTO_INCREMENT,
    `create_time` TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    `wallet_id`   BIGINT         NOT NULL COMMENT '钱包ID',
    `amount`      DECIMAL(10, 2) NOT NULL COMMENT '金额',
    `type`        TINYINT        NOT NULL COMMENT '变动类型：1-消费，0-退款',

    PRIMARY KEY (`id`),
    KEY (`wallet_id`)
) COMMENT = '钱包金额变动明细';