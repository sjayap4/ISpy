DROP TABLE IF EXISTS iSpy.LoginInfo;

CREATE TABLE iSpy.LoginInfo(
	username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passedPark VARCHAR(255),
    passedNH VARCHAR(255),
    passedZoo VARCHAR(255),
    parkLevel integer,
    nborhoodLevel integer,
    zooLevel integer,
    points integer
);

