# earthquake-restws
Entrega de prueba de servicios REST
===================================
Autor: M. Lopez R.
Fecha: 2020-marzo-02

Este es el proyecto de consumo de una API REST pública, solicitado para postular dentro de Emergya. 

Está construido en Intellij IDEA Ultima 2018.1 y Maven, con base de datos H2 y Spring Boot, Swagger 2 y JUnit 4.

Se definieron los siguientes servicios Web REST ('endpoints'):

http://localhost:9090/earthquakes/getEarthquakesByDate      [POST]
http://localhost:9090/earthquakes/getEarthquakesByMagnitude [POST]
http://localhost:9090/earthquakes/storeTodayEarthquakes     [GET]

y servicios REST también para manipular las tablas de bases de datos en H2:

http://localhost:9090/earthquakes/createDBTables [GET]
http://localhost:9090/earthquakes/dropDBTables   [GET]
http://localhost:9090/earthquakes/deleteDBData   [GET]
