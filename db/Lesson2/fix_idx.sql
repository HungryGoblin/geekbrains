ALTER TABLE `geodata`.`_countries` 
ADD INDEX `idx_title` (`title` ASC);
INSERT INTO `geodata`.`_countries` (`title`) VALUES ('NOT_DEFINED');

ALTER TABLE `geodata`.`_regions` 
ADD INDEX `title` (`title` ASC),
ADD INDEX `fk_country_id_idx` (`country_id` ASC);
;
ALTER TABLE `geodata`.`_regions` 
ADD CONSTRAINT `fk_country_id`
  FOREIGN KEY (`country_id`)
  REFERENCES `geodata`.`_countries` (`id`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE `geodata`.`_cities` 
ADD INDEX `idx_title` (`title` ASC);
;

