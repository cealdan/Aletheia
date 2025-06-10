CREATE TABLE UserData (
    username VARCHAR(255) NOT NULL PRIMARY KEY, 
	password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
    dateofbirth VARCHAR(255) NOT NULL,
    permission Int NOT NULL 
);

