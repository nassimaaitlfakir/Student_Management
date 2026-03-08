#!/bin/sh
set -e

echo "DB_HOST=$DB_HOST"
echo "DB_PORT=$DB_PORT"
echo "DB_NAME=$DB_NAME"
echo "DB_USER=$DB_USER"

mkdir -p /usr/local/tomcat/conf/Catalina/localhost

cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF
<Context>
    <Resource name="jdbc/students"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="20"
              maxIdle="10"
              maxWaitMillis="-1"
              username="${DB_USER}"
              password="${DB_PASSWORD}"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"/>
</Context>
EOF

exec catalina.sh run