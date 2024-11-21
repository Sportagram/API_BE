CREATE DATABASE sporta_db;
USE sporta_db;

 CREATE TABLE `team` (
   `teamID` varchar(20) NOT NULL,
   `home_stadium` varchar(255) DEFAULT NULL,
   `team_name` varchar(255) DEFAULT NULL,
   `short_name` varchar(255) DEFAULT NULL,
   PRIMARY KEY (`teamID`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
  CREATE TABLE `users` (
   `userID` varchar(20) NOT NULL,
   `userName` varchar(20) NOT NULL,
   `email` varchar(20) NOT NULL,
   `nick_name` varchar(20) NOT NULL,
   `my_team` varchar(20) NOT NULL,
   PRIMARY KEY (`userID`),
   UNIQUE KEY `nick_name` (`nick_name`),
   KEY `my_team` (`my_team`),
   CONSTRAINT `users_ibfk_1` FOREIGN KEY (`my_team`) REFERENCES `team` (`teamID`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
CREATE TABLE `news` (
   `newsID` int NOT NULL AUTO_INCREMENT,
   `teamID` varchar(20) DEFAULT NULL,
   `title` varchar(30) DEFAULT NULL,
   `url` varchar(100) DEFAULT NULL,
   `newsDate` datetime DEFAULT NULL,
   `teamName` varchar(10) DEFAULT NULL,
   PRIMARY KEY (`newsID`),
   KEY `teamID` (`teamID`),
   CONSTRAINT `news_ibfk_1` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 
 CREATE TABLE `schedules` (
   `scheduleid` varchar(255) NOT NULL,
   `teamID` varchar(20) DEFAULT NULL,
   `oppID` varchar(20) DEFAULT NULL,
   `stadium` varchar(255) NOT NULL,
   `match_date` datetime(6) NOT NULL,
   `match_status` varchar(255) DEFAULT NULL,
   `match_time` time(6) NOT NULL,
   `opp_score` int NOT NULL,
   `team_score` int NOT NULL,
   PRIMARY KEY (`scheduleid`),
   KEY `teamID` (`teamID`),
   KEY `oppID` (`oppID`),
   CONSTRAINT `schedules_ibfk_1` FOREIGN KEY (`teamID`) REFERENCES `team` (`teamID`),
   CONSTRAINT `schedules_ibfk_2` FOREIGN KEY (`oppID`) REFERENCES `team` (`teamID`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
 
 
 -- Diary 테이블 생성
CREATE TABLE Diary (
    diaryID varchar(50) PRIMARY KEY,
    userID varchar(20),
    scheduleID varchar(30),
    match_cnt int,
    comments text,
    game_result enum('승', '무', '패')
) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- Rates 테이블 생성
CREATE TABLE Rates (
    ratesID varchar(30) PRIMARY KEY,
    userID varchar(20),
    wins int,
    loss int,
    draw int,
    match_cnt int,
    win_rates float,
    loss_rates float,
    draw_rates float
) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- Records 테이블 생성
CREATE TABLE Records (
recordID varchar(30) PRIMARY KEY,
scheduleID varchar(30),
away_score text,
home_score text,
away_records text,
home_records text,
game_records text,
away_pitchers text,
home_pitchers text
) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

-- Compatibility 테이블 생성
CREATE TABLE Compatibility (
compatibilityID varchar(50) PRIMARY KEY,
userID varchar(20),
player_name varchar(20),
win_cnt int,
draw_cnt int,
loss_cnt int,
match_cnt int,
win_rates float,
draw_rates float,
loss_rates float
) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

 INSERT INTO Team (teamID, team_name, short_name, home_stadium)
VALUES 
('820130gwangju', 'KIA 타이거즈', 'KIA', '광주'),
('820203daegu', '삼성 라이온즈', '삼성', '대구'),
('820126seoul', 'LG 트윈스', 'LG', '잠실'),
('820115seoul', '두산 베어스', '두산', '잠실'),
('130117suwon', 'KT 위즈', 'KT', '수원'),
('000331incheon', 'SSG 랜더스', 'SSG', '문학'),
('820212busan', '롯데 자이언츠', '롯데', '사직'),
('860308daejeon', '한화 이글스', '한화', '대전'),
('110331changwon', 'NC 다이노스', 'NC', '창원'),
('080324seoul', '키움 히어로즈', '키움', '고척');