SELECT * FROM TestTable tt JOIN TestTable tt2 ON tt.id = tt2.id GROUP BY tt.id ORDER BY tt.id ASC;
