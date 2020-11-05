-- Выбрать все города из Московской области.

USE geodata;
SELECT _cities.title
FROM   _cities, _regions
WHERE  _regions.title = 'Московская область' AND _cities.region_id = _regions.id; 