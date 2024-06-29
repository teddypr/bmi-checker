DROP TABLE IF EXISTS BMIs;

CREATE TABLE  BMIs(
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(255) NOT NULL,
 height VARCHAR(255) NOT NULL,
 weight VARCHAR(255) NOT NULL,
 birthday VARCHAR(255) NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO BMIs (name, height, weight, birthday) VALUES ('田中', '171.5', '60.2', '2000/01/01');
INSERT INTO BMIs (name, height, weight, birthday) VALUES ('木村', '167.8', '64.5', '2002/02/02');
