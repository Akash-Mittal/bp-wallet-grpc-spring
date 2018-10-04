docker rm -f bp-dev-mysql

docker run --detach --name=bp-dev-mysql --env="MYSQL_ROOT_PASSWORD=mypassword" --publish 6603:3306 --volume  type=bind,src=/src/main/resources/docker/mysql/data,dst=/var/lib/mysql  --volume  type=bind,src=/src/main/resources/docker/mysql/conf/my.cnf,dst=/etc/mysql/my.cnf -d mysql
