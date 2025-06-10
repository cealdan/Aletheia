CREATE TABLE UserPageData (
    username VARCHAR(255) NOT NULL PRIMARY KEY, 
	customname VARCHAR(255),
	biography VARCHAR(255),
	userposts JSONB,
	savedposts JSONB,
	followings JSONB,
	followers JSONB,
	assistantmodel JSONB,
	profile_pic_url VARCHAR(255)
);