UPDATE MOCK_DATA tt SET tt.`email` = 'TEST', tt.`first_name` = 'TEST_KEY' WHERE tt.`id` > 2;
DELETE FROM MOCK_DATA WHERE id < 0;
INSERT INTO MOCK_DATA (first_name, last_name, email, country, ip_address) VALUES ('TEST_KEY', 'Miller', 'TEST', 'Anguilla', '138.203.18.130');
SELECT SUM(id) FROM MOCK_DATA WHERE id BETWEEN 1000 AND 10001;
