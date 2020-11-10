CREATE TABLE `player_scores` (
	`id` INT NOT NULL AUTO_INCREMENT Primary Key,
	`name` VARCHAR(30) NOT NULL, 
	`length` INT, 
	`score` INT,
	`squareMatrixString` VARCHAR(10000)
);

INSERT INTO `player_scores` (name, length, score) VALUES 
('markzarak', 99, 999),
('batman', 7, 20),
('superman', 9, 200),
('thor', 7, 85),
('aquaman', 5, 1);
