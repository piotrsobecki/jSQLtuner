SELECT * FROM TestTable;
UPDATE TestTable tt SET tt.`value`='TEST', tt.`key`='TEST_KEY' WHERE tt.id>2;
SELECT ** FROM TestTable;
