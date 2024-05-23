# MariaDB setup

### STARTING THE DATABASE
We need to first install mariadb via the pacmac or any add/remove program that's available. After installation, when 
you start Maria DB for the first time, you will execute the following command:
```
sudo systemctl start mariadb
```
with the following problem:

```
~  sudo systemctl start mariadb
[sudo] password for abhay: 
Job for mariadb.service failed because the control process exited with error code.
See "systemctl status mariadb.service" and "journalctl -xeu mariadb.service" for details.
```
**Please dont do this!!!!**

When mariadb was installed, there was a message given:

```
...
Loading packages files...
Checking file conflicts...
Checking available disk space...
Installing mariadb (11.3.2-1)...
:: You need to initialize the MariaDB data directory prior to starting
   the service. This can be done with mariadb-install-db command, e.g.:
     # mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
Running post-transaction hooks...
Creating system user accounts...
Reloading system manager configuration...
Creating temporary files...
Arming ConditionNeedsUpdate...
Transaction successfully finished.
```

You need to run this command as root
```
[pluto abhay]# mariadb-install-db --user=mysql --basedir=/usr --datadir=/var/lib/mysql
Installing MariaDB/MySQL system tables in '/var/lib/mysql' ...
OK

To start mariadbd at boot time you have to copy
support-files/mariadb.service to the right place for your system


Two all-privilege accounts were created.
One is root@localhost, it has no password, but you need to
be system 'root' user to connect. Use, for example, sudo mysql
The second is mysql@localhost, it has no password either, but
you need to be the system 'mysql' user to connect.
After connecting you can set the password, if you would need to be
able to connect as any of these users with a password and without sudo

See the MariaDB Knowledgebase at https://mariadb.com/kb

You can start the MariaDB daemon with:
cd '/usr' ; /usr/bin/mariadbd-safe --datadir='/var/lib/mysql'

You can test the MariaDB daemon with mariadb-test-run.pl
cd '/usr/mariadb-test' ; perl mariadb-test-run.pl

Please report any problems at https://mariadb.org/jira

The latest information about MariaDB is available at https://mariadb.org/.

Consider joining MariaDB's strong and vibrant community:
https://mariadb.org/get-involved/
```

So run the command to start mariadb
```
cd '/usr' ; sudo /usr/bin/mariadbd-safe --datadir='/var/lib/mysql'
```

Once you do that then you have basically started MariaDB.

### CONNECTING TO THE DATABASE
Of course the password is blank. But MariaDB uses unix socket plugin to authenticate plugin for root. So you need to 
login as root.

```
~ sudo mariadb -u root 
[sudo] password for abhay: 
Welcome to the MariaDB monitor.  Commands end with ; or \g.
Your MariaDB connection id is 11
Server version: 11.3.2-MariaDB Arch Linux

Copyright (c) 2000, 2018, Oracle, MariaDB Corporation Ab and others.

Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
```
That basically gave you the root shell on MariaDb.


### CREATING DATABASE
You will use the command `create database` to create the database like so:

```
MariaDB [(none)]> CREATE DATABASE TEST;
Query OK, 1 row affected (0.001 sec)

MariaDB [(none)]> use TEST;
Database changed
```

### CREATING USER
After that you will create a user and grant all the required permissions for the database

```
MariaDB [TEST]> create user 'testuser'@'localhost' identified by 'test';
Query OK, 0 rows affected (0.016 sec)

MariaDB [TEST]> grant all privileges on TEST to 'testuser'@'localhost';
Query OK, 0 rows affected (0.003 sec)

MariaDB [(none)]> GRANT SELECT,UPDATE,INSERT,DELETE,DROP,CREATE ON TEST.* TO 'testuser'@'localhost';
Query OK, 0 rows affected (0.016 sec)

MariaDB [TEST]> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.001 sec)

```

Now you can connect to the database via any given client using the username as testuser and password as test