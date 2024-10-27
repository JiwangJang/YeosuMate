-- users 테이블 생성 구문
CREATE TABLE users(
	user_index int AUTO_INCREMENT PRIMARY KEY,
    -- 이거는 소셜로그인 아이디 길이에 따라 변동가능
    user_id varchar(30) NOT NULL UNIQUE,
    nickname varchar(10) NOT NULL,
    phone_number char(13) NOT NULL,
    password char(60) NOT NULL,
    profile_image varchar(100) NOT NULL,
    like_count int DEFAULT(0),
    is_block bool DEFAULT(false),
    is_alert bool DEFAULT(false),
    alert_reason text DEFAULT(NULL),
    alert_count int DEFAULT(0),
    age char(2) DEFAULT("00"),
    use_person int DEFAULT(1),
    is_local bool DEFAULT(false),
    kind_point int DEFAULT(0),
    living_start_date char(10),
    role_id varchar(5) NOT NULL,
	FOREIGN KEY (role_id) REFERENCES roles(role_id) ON UPDATE CASCADE ON DELETE CASCADE
);

-- 권한 테이블 생성
CREATE TABLE roles(
	role_id int AUTO_INCREMENT PRIMARY KEY,
    role varchar(5) NOT NULL,
    discription text NOT NULL
);

-- 권한생성
INSERT INTO roles(role, discription) VALUE("ADMIN", "관리자권한");
INSERT INTO roles(role, discription) VALUE("USER", "사용자권한");