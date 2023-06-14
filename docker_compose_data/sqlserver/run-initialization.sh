# Wait to be sure that SQL Server came up
echo "Waiting for Azure SQL Edge to start..."
# /usr/src/app/wait-for-it.sh localhost:1433 -t 80

# Run the setup script to create the DB and the schema in the DB
# Note: make sure that your password matches what is in the Dockerfile
echo "DB is up. Running initialization script.."
# /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P PasswordO1. -d master -i create-database.sql
