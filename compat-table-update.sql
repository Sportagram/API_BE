DROP TABLE IF EXISTS `compatibility`;

CREATE TABLE `compatibility` (
   `compatibilityid` varchar(255) NOT NULL,
   `userid` varchar(255) NOT NULL,
   `player_name` varchar(20) NOT NULL,
   `win_cnt` int DEFAULT NULL,
   `draw_cnt` int DEFAULT NULL,
   `loss_cnt` int DEFAULT NULL,
   `match_cnt` int DEFAULT NULL,
   `win_rates` float DEFAULT NULL,
   `draw_rates` float DEFAULT NULL,
   `loss_rates` float DEFAULT NULL,
   `out_count` int DEFAULT NULL,
   `homeruns` int DEFAULT NULL,
   `hits` int DEFAULT NULL,
   `bb` int DEFAULT NULL,
   `k` int DEFAULT NULL,
   `runs` int DEFAULT NULL,
   `er` int DEFAULT NULL,
   `sp_loss` int DEFAULT NULL,
   `sp_wins` int DEFAULT NULL,
   PRIMARY KEY (`compatibilityid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci