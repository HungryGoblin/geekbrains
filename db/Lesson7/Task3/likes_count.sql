-- Создайте запрос, который выведет информацию: id пользователя; имя; лайков получено; лайков поставлено; взаимные лайки.

set @user := 1;

-- поставленные лайки
select count(*)
from reaction
inner join entity_type
where 
reaction.user_id = @user and
reaction.entity_type_id = entity_type.id and
entity_type.table = "user"
into @set;

-- полученные лайки
select count(*)
from reaction
inner join entity_type
where 
reaction.entity_id = @user and
reaction.entity_type_id = entity_type.id and
entity_type.table = "user"
into @get;

-- взаимные лайки
-- планировал использовать Intersect, но что-то не компилируется
select count(*) in (
select distinct user_id FROM reaction
where reaction.user_id in (SELECT user_id FROM reaction where reaction.entity_id = @user and reaction.entity_id = 1))
into @mutual;

-- вывод
select user.first_name, user.last_name, @set, @get, @mutual
from user
where user.id = @user;
