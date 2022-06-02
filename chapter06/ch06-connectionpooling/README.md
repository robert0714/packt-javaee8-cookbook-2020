# Preparation
* [How to Install Derby Database in Windows 10](https://www.youtube.com/watch?v=sOSa1DCL6rw)

```shell
PS C:\Users\1204003> ij
ij version 10.14
ij> connect 'jdbc:derby:mydb;create=true';
```
You can find the derby.log in user's home directory.

```log
----------------------------------------------------------------
Wed Jun 01 13:56:20 CST 2022:
Booting Derby version The Apache Software Foundation - Apache Derby - 10.14.2.0 - (1828579): instance a816c00e-0181-1dd6-1d7d-00000f300000 
on database directory C:\Users\1204003\mydb with class loader jdk.internal.loader.ClassLoaders$AppClassLoader@6d5380c2 
Loaded from file:/C:/Users/1204003/Downloads/db-derby-10.14.2.0-bin/lib/derby.jar
java.vendor=Microsoft
java.runtime.version=11.0.15+10-LTS
user.dir=C:\Users\1204003
os.name=Windows 10
os.arch=amd64
os.version=10.0
derby.system.home=null
Database Class Loader started - derby.database.classpath=''
----------------------------------------------------------------
```
database directory C:\Users\1204003\mydb 

# Derby Network Server
https://db.apache.org/derby/papers/DerbyTut/ns_intro.html

## Start Network Server

Start the Network server by executing the startNetworkServer.bat (Windows) or startNetworkServer (UNIX) script. This will start the Network Server up on port 1527 and echo a startup message:

```shell
PS C:\Users\1204003> startNetworkServer.bat
Wed Jun 01 14:05:48 CST 2022 : Security manager installed using the Basic server security policy.
Wed Jun 01 14:05:48 CST 2022 : Apache Derby Network Server - 10.14.2.0 - (1828579) started and ready to accept connections on port 1527
```

## An easier way: derbyrun.jar
```shell
 java -jar derbyrun.jar server start
```

## Add JDBC jar into payara
Reference: https://blog.payara.fish/using-mysql-with-payara

```shell
asadmin add-library /path/to/mysql-connector-java-bin.jar
```
In Windows

```shell
Microsoft Windows [Version 10.0.19044.1706]
(c) Microsoft Corporation. All rights reserved.

D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\bin>asadmin add-library D:\DATA\JAVA_program\program\db_tool\jdbc_drivers\derby\*.jar
Remote server does not listen for requests on [localhost:4848]. Is the server up?
No such local command: add-library.  Unable to access the server to execute the command remotely.  Verify the server is available.
Command add-library failed.

D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\bin>asadmin start-domain
Waiting for domain1 to start ...........................
Successfully started the domain : domain1
domain  Location: D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\glassfish\domains\domain1
Log File: D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\glassfish\domains\domain1\logs\server.log
Admin Port: 4848
Command start-domain executed successfully.

D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\bin>asadmin add-library D:\DATA\JAVA_program\program\db_tool\jdbc_drivers\derby\*.jar
Command add-library executed successfully.

D:\DATA\JAVA_program\program\server\payara\payara-5.2022.2\bin>
```

```shell
asadmin stop-domain
```
## Creating JDBC Connection Pools in Payara

Navigate to the left menu ``Resources -> JDBC -> JDBC Connection Pools`` to Click the Button named **New** in ``http://localhost:4848/common/index.jsf``


| key                  | value                                  |
|----------------------|----------------------------------------|
| Pool Name            | derby_net_userDb_userDbPool            |
| Resource Type        | java.sql.Driver                        |
| Datasource Classname | org.apache.derby.jdbc.ClientDataSource |
| Driver Classname     | org.apache.derby.jdbc.ClientDriver     |

## Creating JDBC Resources in Payara

Navigate to the left menu ``Resources -> JDBC -> JDBC JDBC Resources`` to Click the Button named **New** in ``http://localhost:4848/common/index.jsf``

| key                  | value                                  |
|----------------------|----------------------------------------|
| JNDI Name            | app/userDb                             |
| Pool Name            | derby_net_userDb_userDbPool            | 

## Inspect Data

```shell
PS C:\Users\1204003> ij
ij version 10.14
ij>  connect 'jdbc:derby://localhost:1527/userDb;create=true';
ij>  connect 'jdbc:derby://localhost:1527/userDb';
ij(CONNECTION1)> show schemas;
TABLE_SCHEM
------------------------------
APP
NULLID
SQLJ
SYS
SYSCAT
SYSCS_DIAG
SYSCS_UTIL
SYSFUN
SYSIBM
SYSPROC
SYSSTAT
USERDB

ij(CONNECTION1)> show tables in USERDB;
TABLE_SCHEM         |TABLE_NAME                    |REMARKS
------------------------------------------------------------------------
USERDB              |USERTAB                       |

1 row selected
ij(CONNECTION1)> select * from  USERDB.USERTAB;
ID         |EMAIL                                                                                                                           |NAME

---------------------------------------------------------------------------------------------------------------------------------------------------------------------
1          | user1@eldermoraes.com                                                                                                          | User1
2          | user2@eldermoraes.com                                                                                                          | User2
3          | user3@eldermoraes.com                                                                                                          | User3
4          | user4@eldermoraes.com                                                                                                          | User4
5          | user5@eldermoraes.com                                                                                                          | User5
6          | user6@eldermoraes.com                                                                                                          | User6
7          | user7@eldermoraes.com                                                                                                          | User7
8          | user8@eldermoraes.com                                                                                                          | User8
9          | user9@eldermoraes.com                                                                                                          | User9
10         | user10@eldermoraes.com                                                                                                         | User10

10 rows selected
ij(CONNECTION1)> exit;
PS C:\Users\1204003>
```
## Test
To try it, just open this URL in your browser:

```shell
http://localhost:8080/ch06-connectionpooling/PoolTestServlet
```