# Choose ubuntu version
FROM mcr.microsoft.com/azure-sql-edge

# Create app directory
WORKDIR /usr/src/app

# Copy initialization scripts
COPY entrypoint.sh /usr/src/app
COPY run-initialization.sh /usr/src/app
COPY create-database.sql /usr/src/app
COPY wait-for-it.sh /usr/src/app
             
# Set environment variables, not have to write them with the docker run command
# Note: make sure that your password matches what is in the run-initialization script 
ENV ACCEPT_EULA 1
ENV MSSQL_SA_PASSWORD PasswordO1.
ENV MSSQL_PID Developer

# Expose port 1433 in case accessing from other container
# Expose port externally from docker-compose.yml
EXPOSE 1433

# Run Microsoft SQL Server and initialization script (at the same time)
CMD /bin/bash ./entrypoint.sh
