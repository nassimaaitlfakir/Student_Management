#!/bin/sh
set -e

echo "MYSQLHOST=$MYSQLHOST"
echo "MYSQLPORT=$MYSQLPORT"
echo "MYSQLDATABASE=$MYSQLDATABASE"
echo "MYSQLUSER=$MYSQLUSER"

mkdir -p /usr/local/tomcat/conf/Catalina/localhost

cat > /usr/local/tomcat/conf/Catalina/localhost/ROOT.xml <<EOF
<Context>
    <Resource name="jdbc/students"
              auth="Container"
              type="javax.sql.DataSource"
              maxTotal="20"
              maxIdle="10"
              maxWaitMillis="-1"
              username="${MYSQLUSER}"
              password="${MYSQLPASSWORD}"
              driverClassName="com.mysql.cj.jdbc.Driver"
              url="jdbc:mysql://${MYSQLHOST}:${MYSQLPORT}/${MYSQLDATABASE}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"/>
</Context>
EOF

exec catalina.sh run