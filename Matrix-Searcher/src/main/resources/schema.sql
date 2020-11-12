CREATE TABLE `player_scores` (
	`id` INT NOT NULL AUTO_INCREMENT Primary Key,
	`name` VARCHAR(30), 
	`length` INT, 
	`score` INT DEFAULT 0,
	`squareMatrixString` VARCHAR(400),
	`continuingGame` boolean DEFAULT false
);

INSERT INTO `player_scores` (name, length, score, squareMatrixString) VALUES 
('Mark Zarak', 20, 999, 'pmcppabzpwuvhqekmrrgleqnruczidyqkocogewgegpmcwuveqnrfchtovkh'),
('Spider-Man', 7, 80, 'eczidyqkocogewgegfcmtovkpmcppabzvvvh'),
('Iron Man', 15, 740, 'rczidyqkocogewgegfcmtovkhpmcppabzpwuvhqekmrrgleqhhpabzp'),
('Thor was here', 12, 325, 'tkfbkdsidghomjdgzomhezugbtkfbkdsidgvvvvhezugb'),
('Aquaman!!', 5, 10, 'zhnqflmyzbrgufzibetnhfdsn');
