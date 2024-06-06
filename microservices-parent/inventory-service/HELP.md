### 
```                                                                              ✔
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 9
Server version: 11.3.2-MariaDB Arch Linux

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.

MariaDB [(none)]> create database mydb;
Query OK, 1 row affected (0.005 sec)

MariaDB [(none)]> use mydb;
Database changed

MariaDB [mydb]> create user 'pluto'@'localhost' identified by 'pluto';
Query OK, 0 rows affected (0.007 sec)

MariaDB [mydb]> grant all privileges on inventory_service to 'pluto'@'localhost';
Query OK, 0 rows affected (0.003 sec)

MariaDB [mydb]> grant select,alter, update,insert,delete,drop,create on inventory_service.* to 'pluto'@'localhost';
Query OK, 0 rows affected (0.006 sec)

MariaDB [mydb]> flush privileges;
Query OK, 0 rows affected (0.001 sec)
```