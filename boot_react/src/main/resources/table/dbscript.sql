DROP TABLE REFRESH_TOKENS CASCADE CONSTRAINTS;

create table REFRESH_TOKENS(
    ID char(36) not null,
    USERID varchar2(50) not null,
    TOKEN_VALUE varchar2(255) not null,
    ISSUED_AT timestamp default SYSTIMESTAMP NOT NULL ,
    EXPIRES_IN number not null,
    EXPIRATION_DATE timestamp not null,
    USER_AGENT LONG,
    STATUS varchar2(50),
    CONSTRAINT PK_RTOKENS PRIMARY KEY (ID),
    CONSTRAINT FK_RTOKENS_USERS FOREIGN KEY (USERID) REFERENCES MEMBER(USERID) ON DELETE CASCADE
);
COMMIT;

COMMENT ON COLUMN REFRESH_TOKENS.ID IS '토큰식별ID';
COMMENT ON COLUMN REFRESH_TOKENS.USERID IS '토큰사용자아이디';
COMMENT ON COLUMN REFRESH_TOKENS.TOKEN_VALUE IS '토큰값';
COMMENT ON COLUMN REFRESH_TOKENS.ISSUED_AT IS '토큰생성시간';
COMMENT ON COLUMN REFRESH_TOKENS.EXPIRES_IN IS '토큰만료시간(초)';
COMMENT ON COLUMN REFRESH_TOKENS.EXPIRATION_DATE IS '토큰만료날짜시간';
COMMENT ON COLUMN REFRESH_TOKENS.USER_AGENT IS '토큰발급에이전트';
COMMENT ON COLUMN REFRESH_TOKENS.STATUS IS '토큰상태';