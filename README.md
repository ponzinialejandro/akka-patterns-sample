Introducción Akka Scala
===================


El objetivo de este repositorio es introduccir al lector en la programación de Scala y Akka en un entorno similar al de Gulliver.

> **Principales temas:**

> - Actores
> - Dispatchers
> - Workers
> - Routers
> - Patrones que se utilizan en Gulliver para interactuar con proveedores.
	- Single request
	- Orchestrator
	- Scatter Gatter
	- Scatter Gatter first in complete
> - Metricas de actores usando grafana.	

----------

Correr el proyecto
-------------

1. Importar proyecto
	```
	git clone git@gullivertests.tekgenesis.com:primeros-pasos/intro-akka-scala-patterns.git
	```

2. Importar proyecto. Abrir Idea, seleccionar `importar` marcando el build.gradle (Utilizar el gradle wrapper)

3. Instanciar stack Grafana (este paso es optativo, utilizar cuando se quiera monitorear)
	```
	docker run \
	  --detach \
	   --publish=80:80 \
	   --publish=81:81 \
	   --publish=8125:8125/udp \
	   --publish=8126:8126 \
	   --name kamon-grafana-dashboard \
	   kamon/grafana_graphite
	```

4. Correr gradle refresh desde el ide o en caso de no funcionar `./gradlew clean build`

5. Crear un run configurations de "Aplication" con la siguiente configuración:
	```
	MainClass : com.garba.viajes.aponzini.spray.endpoint.Boot
	Vm Options : -javaagent:PATH_TO_LIBS/aspectjweaver-1.8.9.jar   <---podría mejorarse
	Working Directory: /Users/YOUR_USER/DIR...TO...PROYECTO/akka-patterns-sample
	```

6. Correr el run configurations.
7. entrar a localhost:80 con las credenciales admin@admin
8. Probar los servicios (seguir leyendo)

----------
Estrutura del proyecto
-------------

    + src
    	+ main
    	    + resources
    	    + scala
    		    + common
    			+ db
    			+ requestProviderOrchestrator
    			+ ScatterGatterFirstCompleteRouter
    			+ scatterGatter
    			+ ScatterGatterWithRouter
    			+ SingleRequest
    			+ Spray.endpoint
    	+ test
    + build.gradle


### Test
	 tests unitarios que corroboran el correcto funcionamiento de las clases.

### Resources:
	application.conf = configuracion de actores, kamon, dispatcher y spray.

### Scala Packages
#### common
	Clases comunes y utilitarios para el resto de los paquetes
#### db
	Clases que emulan la interacción y acceso a la base de datos ficticia. Se puede apreciar el uso de pool de threads para el acceso a la persitencia de "entidades"
#### SingleRequest
	Ejemplo de como invocar un servicio utilizando actores.

	curl localhost:8080/darksky
#### RequestProviderOrchestrator
	Ejemplifican el uso de un orquestador. Una entidad que es responsable de ejecutar una serie de pasos antes de devolver una respuesta.

	curl localhost:8080/cityweather
#### ScatterGatterFirstCompletedRouter
	Demuestra como solicitar información a diferentes proveedores y retornar la primer respuesta que reciba.

	curl localhost:8080/firstcomplete
#### ScatterGatter
	Demuestra como solicitar información a diferentes proveedores y devolver una mezcla de las respuestas.
#### ScatterGatterWithRouter
	Una versión mas elegante de ScatterGatter basada en el uso de router y broadcast.

	curl localhost:8080/scattergatter
#### Spray.Endpoint
	Punto de entrada de la applicacion donde se exponen servicios para consultar.

	curl localhost:8080


####

----------


#### <i class="icon-file"></i> Documentación

http://apps.tekgenesis.com/confluence/display/GUL/Scala+and+Akka+Documentation