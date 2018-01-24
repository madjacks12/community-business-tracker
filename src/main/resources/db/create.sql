
CREATE TABLE IF NOT EXISTS businesses (
 id int PRIMARY KEY auto_increment,
 businessName VARCHAR,
 address VARCHAR,
 phone VARCHAR,
 email VARCHAR
);

CREATE TABLE IF NOT EXISTS charities (
 id int PRIMARY KEY auto_increment,
 charityName VARCHAR
);

CREATE TABLE IF NOT EXISTS businessType (
 id int PRIMARY KEY auto_increment,
 businessType VARCHAR
);

CREATE TABLE IF NOT EXISTS businesses_charities (
 id int PRIMARY KEY auto_increment,
 businessid INTEGER,
 charityid INTEGER
);

CREATE TABLE IF NOT EXISTS businesses_businessTypes (
 id int PRIMARY KEY auto_increment,
 businessid INTEGER,
 businessTypeid INTEGER
);

