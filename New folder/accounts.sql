SELECT

   username AS 'username-account',

   email AS 'email-account',

   COUNT(item_id) AS 'items',

   SUM(item_weight) AS 'total_weight'

FROM

   user_accounts

JOIN

   inventory ON user_accounts.user_id = inventory.user_id

GROUP BY

   user_accounts.user_id, username, email

HAVING

   SUM(item_weight) > 20

ORDER BY

   total_weight DESC, username ASC;