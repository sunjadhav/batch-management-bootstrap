/* temporary data */
USE batch_management;

INSERT INTO `batch_management`.`user` VALUES(1,'admin', 'admin');

INSERT INTO `batch_management`.`batch` 
(`id`, `batch_id`, `name`, `timing`, `duration`, `started_at`, `is_active`)
 SELECT '1', 'B1', 'JAN20_8AM',
'8AM-10AM', '2 Months', '1579173855', 'Yes'  AS tmp
WHERE NOT EXISTS (
   SELECT `id` FROM `batch_management`.`batch` WHERE `id` = 1
) LIMIT 1;

INSERT INTO `batch_management`.`student` 
(`id`, `batch_id`, `name`, `surname`, `phone_number`, `address`, 
`college`, `education`, `paid_amount`, `pending_amount`) 
SELECT '1', '1', 'Sunil', 'Jadhav', '8411020827', 'Pune', 'PCCOE','BE', '4000', '3000'  AS tmp
WHERE NOT EXISTS (
   SELECT id FROM `batch_management`.`student` WHERE id = '1'
) LIMIT 1;
