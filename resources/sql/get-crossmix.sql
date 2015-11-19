--name: get-crossmix
-- get crossmix by uid
SELECT * FROM crossmix WHERE `uuid` = :uuid LIMIT 1