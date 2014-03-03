UPDATE TestTable tt SET tt.`value` = 'TEST', tt.`key` = 'TEST_KEY' WHERE tt.`id` > 2;
DELETE FROM TestTable WHERE id < 0;
INSERT INTO TestTable (`key`, `value`) VALUES ('A', 'B');
SELECT SUM(id) FROM MOCK_DATA WHERE id BETWEEN 1000 AND 10001;
