CREATE TABLE `player_scores` (
	`id` INT NOT NULL,
	`name` VARCHAR(30) NOT NULL, 
	`difficulty` INT NOT NULL, 
	`score` INT,
	PRIMARY KEY (`id`)
);

INSERT INTO `player_scores` VALUES 
(1, 'markzarak', 999, 9999),
(2, 'batman', 7, 20),
(3, 'superman', 9, 200),
(4, 'thor', 4, 85),
(5, 'aquaman', 1, 1);
