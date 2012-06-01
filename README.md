vertx-maven-plugin
==================

Maven Plugin for running verticles in their own vert.x instance or managing a vert.x standalone server.

Install
-----

Download the latest plugin jar from the downloads page (https://github.com/rhart/vertx-maven-plugin/downloads) or build the jar from source.

Manually install the plugin jar to your Maven repository
	mvn install -Dfile=vertx-maven-plugin-0.0.1-SNAPSHOT.jar -DgroupId=org.vertx -DartifactId=vertx-maven-plugin -Dversion=0.0.1-SNAPSHOT -Dpackaging=maven-plugin

Usage
-----

### vertx:run

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>0.0.1-SNAPSHOT</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
	</plugin>

	mvn vertx:run