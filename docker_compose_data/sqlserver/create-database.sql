IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'my_business_federation')
    create database my_business_federation
    GO

IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'my_business_federation_test')
    create database my_business_federation_test
    GO
