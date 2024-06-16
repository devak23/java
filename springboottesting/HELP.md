### Source
Udemy: Springboot testing - Ramesh Fadtare
www.udemy.com/course/testing-spring-boot-application-with-junit-and-mockito

### live template for test
```
@DisplayName("$CURSOR$")
@Test
public void given$OBJECT$_when$PRECONDITION$_then$POSTCONDITION$() {
    // given - define precondition for test
    $OBJECT_TO_TEST$
    
    // when - perform the desiredAction
    $DEFINE_PRECONDITION$
    
    // then - verify the output
    $DEFINE_POSTCONDITON$
}
```

MySQL DB configuration
```

MariaDB [(none)]> create database ems;
Query OK, 1 row affected (0.004 sec)

MariaDB [(none)]> create user 'ems'@'localhost' identified by 'ems'
    -> ;
Query OK, 0 rows affected (0.007 sec)

MariaDB [(none)]> use ems;
Database changed

MariaDB [ems]> grant all privileges on ems to 'ems'@'localhost';
Query OK, 0 rows affected (0.007 sec)

MariaDB [ems]> grant select, alter, update, insert, delete, drop, create on ems.* to 'ems'@'localhost';
Query OK, 0 rows affected (0.006 sec)

MariaDB [ems]> flush privileges;
Query OK, 0 rows affected (0.001 sec)
```