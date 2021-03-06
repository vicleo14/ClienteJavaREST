#!/bin/bash


# Instalacion de paquetes del APT
sudo apt update
sudo apt install openjdk-8-jdk-headless
sudo apt install unzip
sudo apt install mysql-server

chmod +x tomcat.sh
chmod +x initDB.sh
chmod +x war.sh
./tomcat.sh
./initDB.sh

export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64



# Definimos variable CATALINA_HOME
export CATALINA_HOME="/home/ubuntu/ClienteJavaREST/apache-tomcat-8.5.60"
#export CATALINA_HOME="/mnt/E4C22EA7C22E7DC4/NOVENO_SEMESTRE/SistemasDistribuidos/Tarea8_MoralesFlores/apache-tomcat-8.5.60"
sh $CATALINA_HOME/bin/catalina.sh start
./war.sh
cp usuario_sin_foto.png apache-tomcat-8.5.60/webapps/ROOT/
cp WSClient.js apache-tomcat-8.5.60/webapps/ROOT/
cp prueba.html apache-tomcat-8.5.60/webapps/ROOT/


