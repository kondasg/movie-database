CREATE TABLE `directors` (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
    `birthday` date,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB;
