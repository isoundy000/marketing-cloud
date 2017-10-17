ALTER TABLE original_data_population ADD COLUMN openid VARCHAR(128) NULL COMMENT "openid";

ALTER TABLE `data_party` CHANGE COLUMN FLAG1 acct_type VARCHAR(45) NULL COMMENT "私有账号类型";

UPDATE `keyid_map_block` SET FIELD = 'acct_type' ,field_name = '私有账号类型' WHERE id =15; 