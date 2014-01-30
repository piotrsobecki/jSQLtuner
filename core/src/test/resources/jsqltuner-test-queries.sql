SELECT * FROM TestTable tt JOIN TestTable tt2 ON tt.id = tt2.id GROUP BY tt.id ORDER BY tt.id ASC;
UPDATE TestTable tt SET tt.`value` = 'TEST', tt.`key` = 'TEST_KEY' WHERE tt.`id` > 2;
DELETE FROM TestTable WHERE id < 0;
INSERT INTO TestTable (`key`, `value`) VALUES ('A', 'B');
SELECT * * FROM TestTable;
