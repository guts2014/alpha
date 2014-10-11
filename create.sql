CREATE TABLE Beacon (
    id VARCHAR(64) PRIMARY KEY, 
    name VARCHAR(64) NOT NULL
);

CREATE TABLE User (
    id INT PRIMARY KEY, 
    name VARCHAR(32) NOT NULL
);

CREATE TABLE Message (
    id INT PRIMARY KEY, 
	text TEXT NOT NULL,
	time TIMESTAMP NOT NULL
);

CREATE TABLE BeaconUsers (
    beaconid VARCHAR(64) REFERENCES Beacon(id), 
	userid INT REFERENCES User(id)
);

CREATE TABLE UserMessages (
    userid INT REFERENCES User(id), 
    msgid INT REFERENCES Message(id), 
    PRIMARY KEY(userid, msgid)
);