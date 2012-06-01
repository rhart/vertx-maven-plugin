vertx-maven-plugin
==================

Maven Plugin for running verticles in their own vert.x instance or managing a vert.x standalone server.

Install
-----

Download the latest plugin jar from the downloads page (https://github.com/rhart/vertx-maven-plugin/downloads) or build the jar from source.

Manually install the plugin jar to your Maven repository

	mvn install -Dfile=vertx-maven-plugin-0.0.1-SNAPSHOT.jar -DgroupId=org.vertx -DartifactId=vertx-maven-plugin -Dversion=0.0.1-SNAPSHOT -Dpackaging=maven-plugin

Unfortunately, until the vert.x jars are available in the Maven central repository you will also have to manually install these into your local Maven repository too.

	<dependency>
		<groupId>org.vertx</groupId>
		<artifactId>platform</artifactId>
		<version>1.0.0.final</version>
	</dependency>
	<dependency>
		<groupId>org.vertx</groupId>
		<artifactId>core</artifactId>
		<version>1.0.0.final</version>
	</dependency>

Usage
-----

### vertx:run

This goal will run a verticle in it's own vert.x instance.  vert.x will continue to run until the plugin is explicitly stopped.  Simply type:

	mvn vertx:run
	
The plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
	</plugin>

Currently the verticle to run needs to be in the same Java project as the POM.  Future releases will allow verticles from other jars to be run.
Subsequent releases of the plugin will allow for configuration to be provided to the verticle, worker setting, number of instances and non Java verticles.



	