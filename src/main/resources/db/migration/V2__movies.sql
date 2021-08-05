CREATE TABLE `movies` (
    `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
    `director_id` bigint(20) UNSIGNED NOT NULL,
    `title` varchar(255) COLLATE utf8_hungarian_ci NOT NULL,
    `year` smallint(4) UNSIGNED NOT NULL,
    `length` smallint(4) UNSIGNED NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`director_id`) REFERENCES `directors`(`id`)
) ENGINE=InnoDB;

CREATE TABLE `movies_ratings` (
    `movie_id` bigint(20) UNSIGNED NOT NULL,
    `rating` smallint(2) UNSIGNED NOT NULL,
    FOREIGN KEY (`movie_id`) REFERENCES `movies`(`id`)
) ENGINE=InnoDB;
