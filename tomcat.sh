#!/bin/bash
# Extraccion de Tomcat
unzip apache-tomcat-8.5.60.zip
rm -r apache-tomcat-8.5.60/webapps
mkdir apache-tomcat-8.5.60/webapps
mkdir apache-tomcat-8.5.60/webapps/ROOT


# Extraccion de Jersey 
unzip jaxrs-ri-2.24.zip

# Copia de archivos jar de Jersey a Tomcat
cp jaxrs-ri/api/*.jar apache-tomcat-8.5.60/lib/
cp jaxrs-ri/ext/*.jar apache-tomcat-8.5.60/lib/
cp jaxrs-ri/lib/*.jar apache-tomcat-8.5.60/lib/

#Solucion de error de compatibilidad de Tomcat con Jersey. El jar proviene de Jersey
rm apache-tomcat-8.5.60/lib/javax.servlet-api-3.0.1.jar

# Copia GSON a Tomcat
cp gson-2.3.1.jar apache-tomcat-8.5.60/lib/

#Driver JDBC de MySQL
unzip mysql-connector-java-8.0.22.zip
cp  mysql-connector-java-8.0.22/mysql-connector-java-8.0.22.jar apache-tomcat-8.5.60/lib/
