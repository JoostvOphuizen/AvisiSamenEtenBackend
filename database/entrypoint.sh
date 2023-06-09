#!/bin/bash

# Start the SQL Server service
/opt/mssql/bin/sqlservr &
ECHO test
# Wait for the SQL Server service to start
sleep 120s
ECHO test na de sleep
# Run the DDL script
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Database!2023 -d master -i /tmp/DDL_SAMENETEN.sql

# Run the insert script
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P Database!2023 -d master -i /tmp/INSERT_SAMENETEN.sql

# Keep the container running
tail -f /dev/null
