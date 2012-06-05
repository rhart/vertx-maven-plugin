vertx-maven-plugin
==================

Maven Plugin for running verticles in their own vert.x instance or managing a vert.x standalone server.

Install
-----
Until the plugin artifact has been added to Maven Central, which will happen when vert.x dependencies are there too,
you will need to download the latest plugin jar and POM from the downloads page (https://github.com/rhart/vertx-maven-plugin/downloads) or build the jar from source.

Manually install the plugin jar to your Maven repository

	mvn install:install-file -Dfile=vertx-maven-plugin-1.0.1-RELEASE.jar -DpomFile=vertx-maven-plugin-1.0.1-RELEASE.pom -DgroupId=org.vertx -DartifactId=vertx-maven-plugin -Dversion=1.0.1-RELEASE -Dpackaging=maven-plugin

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
	
The plugin forks a parallel lifecycle to ensure that the "package" phase has been completed before invoking 
vert.x. This means that you do not need to explicitly execute a "mvn package" first. It also means that a 
"mvn clean vertx:run" will ensure that a full fresh compile and package is done before invoking vert.x.  
	
The plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.1-RELEASE</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
	</plugin>

Sometimes you want automatic execution of the plugin, for example when doing integration testing.
To do this you can run the plugin in Maven execution scenarios and use the daemon=true configuration option to prevent vert.x from running indefinitely.

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.1-RELEASE</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
		<executions>
			<execution>
				<id>instance1</id>
				<phase>pre-integration-test</phase>
				<goals>
					<goal>run</goal>
				</goals>
				<configuration>
					<daemon>true</daemon>
				</configuration>
			</execution>
		</executions>
	</plugin> 


This plugin currently only works for Java based verticles.



	