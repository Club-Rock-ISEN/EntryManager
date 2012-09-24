-- ============================================================
--	Create schema
-- ============================================================
CREATE SCHEMA crock;

-- ============================================================
--	Table: PARAMETER
-- ============================================================
CREATE TABLE parameter (
	name			VARCHAR(45)	NOT NULL	PRIMARY KEY,
	value			VARCHAR(45)	NOT NULL,
	type			VARCHAR(45),
	componentClass	VARCHAR(45)
);

-- ============================================================
--	Table: MEMBER
-- ============================================================
CREATE TABLE member (
	idMember	INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name		VARCHAR_IGNORECASE(60) NOT NULL,
	gender		CHAR NOT NULL,
	entries		INT(6) NOT NULL,
	nextFree	INT(1) NOT NULL,
	credit		DECIMAL(6, 2),
	status		VARCHAR(20),
);

-- ============================================================
--	Table: PARTY
-- ============================================================
CREATE TABLE party (
	idParty				INT(10) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	date				DATE NOT NULL UNIQUE,
	entriesTotal		INT(4) UNSIGNED NOT NULL,
	entriesFirstPart	INT(4) UNSIGNED,
	entriesSecondPart	INT(4) UNSIGNED,
	entriesNewMembers	INT(4) UNSIGNED,
	entriesFree			INT(4) UNSIGNED,
	entriesMale			INT(4) UNSIGNED,
	entriesFemale		INT(4) UNSIGNED,
	revenue				DECIMAL(6, 2) UNSIGNED NOT NULL,
	profit				DECIMAL(6, 2) NOT NULL,
);

-- ============================================================
--	Table: ENTRYMEMBERPARTY
-- ============================================================
CREATE TABLE entryMemberParty (
	`idEntryMemberParty`	INT(20) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
	`idMember`				INT(10) UNSIGNED NOT NULL,
	`idParty`				INT(10) UNSIGNED NOT NULL,
	
	CONSTRAINT entryMemberParty_FK_member FOREIGN KEY (idMember) REFERENCES member (idMember) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT entryMemberParty_FK_party FOREIGN KEY (idParty) REFERENCES party (idParty) ON DELETE CASCADE ON UPDATE NO ACTION
);

-- ============================================================
--	Data: PARAMETER
-- ============================================================
INSERT INTO parameter (name, value, type, componentClass) VALUES ('lookAndFeel', 'Nimbus', 'String', 'LAFComboBox');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('timeLimit', '22:00', 'String', 'TimeSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('freeEntryFrequency', '6', 'Integer', 'IntegerSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('entryPriceTotal', '4.0', 'Double', 'DoubleSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('entryPriceFirstPart', '3.0', 'Double', 'DoubleSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('entryPriceSecondPart', '3.0', 'Double', 'DoubleSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('minCredit', '-10.0', 'Double', 'DoubleSpinner');
INSERT INTO parameter (name, value, type, componentClass) VALUES ('maxCredit', '250.0', 'Double', 'DoubleSpinner');

-- ============================================================
--	User creation
-- ============================================================
CREATE USER IF NOT EXISTS crock PASSWORD 'burgerking';
GRANT INSERT, SELECT, UPDATE, DELETE ON parameter TO crock;

commit;