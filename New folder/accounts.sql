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






SELECT
    CASE
        WHEN a.username IS NOT NULL THEN a.username
        ELSE a.email
    END AS "username / email",
    COUNT(i.item_id) AS items,
    SUM(it.weight) AS "total weight"
FROM
    accounts a
JOIN
    account_items ai ON a.id = ai.account_id
JOIN
    items it ON ai.item_id = it.id
GROUP BY
    a.id
HAVING
    SUM(it.weight) > 20
ORDER BY
    "total weight" DESC,
    "username / email" ASC;
