-- Сделать запрос, в котором мы выберем все данные о городе – регион, страна.

USE geodata;

SELECT _cities.title, _countries.title, _regions.title 
FROM   _cities, _countries, _regions
WHERE  _countries.id = _cities.country_id 
   AND _regions.id   = _cities.region_id; 
