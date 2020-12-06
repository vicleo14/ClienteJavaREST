#!/bin/bash
# Definimos variable CATALINA_HOME
#export CATALINA_HOME="/home/ubuntu/ServicioWebREST_Azure/apache-tomcat-8.5.60"
export CATALINA_HOME="/mnt/E4C22EA7C22E7DC4/NOVENO_SEMESTRE/SistemasDistribuidos/Tarea8_MoralesFlores/apache-tomcat-8.5.60"
# Ingresamos a carpeta Servicio
cd Servicio
# Compilamos la clase Servicio.java
javac -cp $CATALINA_HOME/lib/javax.ws.rs-api-2.0.1.jar:$CATALINA_HOME/lib/gson-2.3.1.jar:. negocio/Servicio.java
# Eliminamos todos los archivos de negocio/ para remplazarlos por los nuevos
rm WEB-INF/classes/negocio/*
# Copiamos los archivos class a WEB-INF
cp negocio/*.class WEB-INF/classes/negocio/
#Creamos archivo war
jar cvf Servicio.war WEB-INF META-INF

# Copiamos el archivo WAR a webapps de Tomcat

cp Servicio.war $CATALINA_HOME/webapps/
