#!/bin/sh
set -e

echo "DB_HOST=$DB_HOST"
echo "DB_PORT=$DB_PORT"
echo "DB_NAME=$DB_NAME"
echo "DB_USER=$DB_USER"

mkdir -p /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/META-INF

cat > /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/META-INF/persistence.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<persistence xmlns="https://jakarta.ee/xml/ns/persistence"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
             https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
             version="3.0">

    <persistence-unit name="student_management" transaction-type="RESOURCE_LOCAL">

        <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>

        <class>org.example.studentslist.Student</class>

        <properties>

            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>

            <property name="jakarta.persistence.jdbc.url"
                      value="jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&amp;allowPublicKeyRetrieval=true&amp;serverTimezone=UTC"/>

            <property name="jakarta.persistence.jdbc.user" value="${DB_USER}"/>

            <property name="jakarta.persistence.jdbc.password" value="${DB_PASSWORD}"/>

            <property name="eclipselink.logging.level" value="FINE"/>

            <property name="eclipselink.ddl-generation" value="none"/>

            <property name="eclipselink.ddl-generation.output-mode" value="database"/>

        </properties>

    </persistence-unit>

</persistence>
EOF

exec catalina.sh run