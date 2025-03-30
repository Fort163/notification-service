#!/bin/bash


psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'notification'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE notification"

psql -U postgres -tc "SELECT 1 FROM pg_database WHERE datname = 'notification_test'" | grep -q 1 || psql -U postgres -c "CREATE DATABASE notification_test"
