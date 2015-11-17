--name: get-last-id
-- get the last used ID
SELECT id FROM crossmix ORDER BY `id` DESC LIMIT 1