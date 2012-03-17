-- ============================================================
--	Clean-up
-- ============================================================
DROP DATABASE IF EXISTS `crock`;
CREATE DATABASE `crock` DEFAULT CHARACTER SET utf8;

USE crock;

DROP TABLE IF EXISTS `parameter`		CASCADE;
DROP TABLE IF EXISTS `member`			CASCADE;
DROP TABLE IF EXISTS `party`			CASCADE;
DROP TABLE IF EXISTS `entrymemberparty`	CASCADE;

-- ============================================================
--	USER CREATION
-- ============================================================
DROP USER `crock`@localhost;
GRANT INSERT, SELECT, UPDATE, DELETE ON `crock`.* TO `crock`@localhost IDENTIFIED BY 'burgerking';


-- ============================================================
--	Table: PARAMETER
-- ============================================================
CREATE TABLE `crock`.`parameter` (
	`name`	VARCHAR(45) NOT NULL	COMMENT 'Name of the parameter.',
	`value`	VARCHAR(45) NOT NULL	COMMENT 'Value of the parameter.',
	`type`	VARCHAR(45)				COMMENT 'Type of the parameter.',

	PRIMARY KEY (`name`),
	UNIQUE INDEX `parameter_name_unique`	(`name` ASC)
) COMMENT = 'Table for the parameters of the application.';

-- ============================================================
--	Table: MEMBER
-- ============================================================
CREATE TABLE `crock`.`member` (
	`idMember`	INT(10) UNSIGNED NOT NULL AUTO_INCREMENT	COMMENT 'The id of the member.',
	`name`		VARCHAR(60) NOT NULL						COMMENT 'The name of the member',
	`gender`	CHAR NOT NULL								COMMENT 'The gender of the member "F" for female, "M" for male.',
	`entries`	INT(6) NOT NULL								COMMENT 'The number of entries of the member.',
	`credit`	DECIMAL(6, 2)								COMMENT 'The credit of the member.',
	`status`	VARCHAR(20)									COMMENT 'The status of the member.',

	PRIMARY KEY (`idMember`),
	UNIQUE INDEX `member_idMember_unique`	(`idMember` ASC)
) COMMENT = 'Table for the members of the association.';

-- ============================================================
--	Table: PARTY
-- ============================================================
CREATE TABLE `crock`.`party`(
	`idParty`			INT(10) UNSIGNED NOT NULL AUTO_INCREMENT	COMMENT 'The id of the party.',
	`date`				DATE NOT NULL								COMMENT 'The date of the party.',
	`entriesTotal`		INT(4) UNSIGNED NOT NULL					COMMENT 'The total number of entries of the party.',
	`entriesFirstPart`	INT(4) UNSIGNED								COMMENT 'The number of entries during the first part of the party.',
	`entriesSecondPart`	INT(4) UNSIGNED								COMMENT 'The number of entries during the second part of the party.',
	`entriesNewMembers`	INT(4) UNSIGNED								COMMENT 'The number of new members for the party.',
	`entriesFree`		INT(4) UNSIGNED								COMMENT 'The number of member which entered for free.',
	`entriesMale`		INT(4) UNSIGNED								COMMENT 'The number of male members.',
	`entriesFemale`		INT(4) UNSIGNED								COMMENT 'The number of female members.',
	`revenue`			DECIMAL(6, 2) UNSIGNED NOT NULL				COMMENT 'The revenue of the party.',
	`profit`			DECIMAL(6, 2) NOT NULL						COMMENT 'The profit of the party.',
	
	PRIMARY KEY (`idParty`),
	UNIQUE INDEX `party_idParty_unique`		(`idParty` ASC),
	UNIQUE INDEX `party_date_unique`		(`date` ASC)
) COMMENT = 'Table for the party';

-- ============================================================
--	Table: ENTRYMEMBERPARTY
-- ============================================================
CREATE TABLE `crock`.`entryMemberParty` (
	`idEntryMemberParty`	INT(20) UNSIGNED NOT NULL AUTO_INCREMENT	COMMENT '',
	`idMember`				INT(10) UNSIGNED NOT NULL					COMMENT '',
	`idParty`				INT(10) UNSIGNED NOT NULL					COMMENT '',
	
	PRIMARY KEY (`idEntryMemberParty`),
	UNIQUE INDEX `entryMemberParty_idEntryMemberParty_unique`	(`idEntryMemberParty` ASC),
	
	CONSTRAINT `entryMemberParty_FK_member` FOREIGN KEY (`idMember`) REFERENCES `crock`.`member` (`idMember`) ON DELETE CASCADE ON UPDATE NO ACTION,
	CONSTRAINT `entryMemberParty_FK_party` FOREIGN KEY (`idParty`) REFERENCES `crock`.`party` (`idParty`) ON DELETE CASCADE ON UPDATE NO ACTION
) COMMENT = 'Table which links the members to the parties.';

-- ============================================================
--	Data: PARAMETER
-- ============================================================
INSERT INTO `crock`.`parameter` (`name`, `value`, `type`) VALUES ('lookAndFeel', 'Nimbus', 'String');
INSERT INTO `crock`.`parameter` (`name`, `value`, `type`) VALUES ('timeLimit', '22:00', 'String');
INSERT INTO `crock`.`parameter` (`name`, `value`, `type`) VALUES ('entryPriceTotal', '4.0', 'String');
INSERT INTO `crock`.`parameter` (`name`, `value`, `type`) VALUES ('entryPriceFirstPart', '3.0', 'Double');
INSERT INTO `crock`.`parameter` (`name`, `value`, `type`) VALUES ('entryPriceSecondPart', '3.0', 'Double');

commit;