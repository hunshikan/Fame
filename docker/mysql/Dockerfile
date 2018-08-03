FROM mysql:5.7

ENV LANG C.UTF-8

COPY ./src/main/resources/init.sql /docker-entrypoint-initdb.d/init.sql
COPY ./docker/mysql/mysqld.cnf /etc/mysql/mysql.conf.d/mysqld.cnf
