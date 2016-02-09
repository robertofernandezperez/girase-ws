# Gisae WS

Proyecto Java para permitir llamadas a los programas Cobol de Gisae a través de un web service.

## El entorno de ejecución es el siguiente:
+ Java 1.4 (1.4.2-17)
+ Servidor Tomcat 5.5.25 (http://archive.apache.org/dist/tomcat/tomcat-5/v5.5.25/bin/apache-tomcat-5.5.25.zip)
+ Axis 2 versión 1.4 (http://archive.apache.org/dist/ws/axis2/1_4/axis2-1.4-war.zip)

## Generar el fichero AAR del servicio
Se ha añadido al proyecto Java el plugin de Axis para generación de servicios web, en nuestro caso en la forma de fichero AAR. Para poder generar el servicio AAR desde el código fuente hay que ejecutar el siguiente comando:

```
	mvn clean package org.apache.axis2:axis2-aar-maven-plugin:aar
```
Por defecto, el servicio AAR se genera en el directorio ${basedir}/target/axis2-services-aar. Posteriormente desplegamos este servicio mediante la consola Axis2 ubicada en el servidor Tomcat.
