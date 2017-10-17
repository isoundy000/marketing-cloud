CREATE TABLE sys_tag_view (
 `id` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
 `view_name` VARCHAR(30) DEFAULT null,
 `view_desc` VARCHAR(300) DEFAULT null,
  `status` int(11) unsigned zerofill NOT NULL,
  `tag_name` varchar(30) DEFAULT NULL
)