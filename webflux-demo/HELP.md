# Notes

### Testing:
```
curl --location --request POST 'localhost:9191/router/customers' --data '{"id" : 100, "name": "Abhay"}' -H "Content-Type: application/json"
```


### MONGO / MARIA DB 
1. For INSTALLATION, please follow the respective OS's add/remove programs
2. On Linux, start maria db via
```
cd '/usr' ; sudo /usr/bin/mariadbd-safe --datadir='/var/lib/mysql'
```
3. Login into maria db via root:
```
sudo mariadb -u root
```
4. Create database and provide grants as shown below
```
~> sudo mariadb -u root                                        1 ✘ 
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 3
Server version: 11.4.3-MariaDB Arch Linux

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> CREATE DATABASE CUSTOMERS;
Query OK, 1 row affected (0.001 sec)

MariaDB [(none)]> USE CUSTOMERS;
Database changed
MariaDB [CUSTOMERS]> create user 'testcustomer'@'localhost' identified by 'test'
    -> ;
Query OK, 0 rows affected (0.016 sec)

MariaDB [CUSTOMERS]> grant all privileges on CUSTOMERS to 'testcustomer'@'localhost';
Query OK, 0 rows affected (0.006 sec)

MariaDB [CUSTOMERS]> GRANT SELECT, UPDATE, INSERT, DELETE, DROP, CREATE, ALTER ON CUSTOMERS.* TO 'testcustomer'@'localhost';
Query OK, 0 rows affected (0.014 sec)

MariaDB [CUSTOMERS]> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.002 sec)

MariaDB [CUSTOMERS]> 
```
