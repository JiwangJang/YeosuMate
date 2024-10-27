CREATE SCHEMA yeosu_mate;

use yeosu_mate;

-- 권한 테이블 생성
CREATE TABLE roles(
	roleId int AUTO_INCREMENT PRIMARY KEY,
    role varchar(5) NOT NULL,
    discription text NOT NULL
);

-- 권한생성
INSERT INTO roles(role, discription) VALUE("ADMIN", "관리자권한");
INSERT INTO roles(role, discription) VALUE("USER", "사용자권한");

-- users 테이블 생성 구문
CREATE TABLE users(
	userIndex int AUTO_INCREMENT PRIMARY KEY,
    -- 이거는 소셜로그인 아이디 길이에 따라 변동가능
    userId varchar(30) NOT NULL UNIQUE,
    nickname varchar(10) NOT NULL,
    phoneNumber char(13) NOT NULL,
    password char(60) NOT NULL,
    profileImage varchar(100) NOT NULL,
    likeCount int DEFAULT(0),
    isBlock bool DEFAULT(false),
    isAlert bool DEFAULT(false),
    alertReason text DEFAULT(NULL),
    alertCount int DEFAULT(0),
    age char(2) DEFAULT("00"),
    usePerson int DEFAULT(1),
    isLocal bool DEFAULT(false),
    kindPoint int DEFAULT(0),
    livingStartDate char(10),
    roleId int NOT NULL,
	FOREIGN KEY (roleId) REFERENCES roles(roleId) ON UPDATE CASCADE ON DELETE CASCADE
);