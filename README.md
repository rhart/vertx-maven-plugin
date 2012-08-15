vertx-maven-plugin
==================

Maven Plugin for running verticles in their own vert.x instance or managing a vert.x standalone server.

Install
-----
Until the plugin artifact has been added to Maven Central, which will happen when vert.x dependencies are there too,
you will need to download the latest plugin jar and POM from the downloads page (https://github.com/rhart/vertx-maven-plugin/downloads) or build the jar from source.

Manually install the plugin jar to your Maven repository

	mvn install:install-file -Dfile=vertx-maven-plugin-1.0.2-RELEASE.jar -DpomFile=vertx-maven-plugin-1.0.2-RELEASE.pom -DgroupId=org.vertx -DartifactId=vertx-maven-plugin -Dversion=1.0.2-RELEASE -Dpackaging=maven-plugin

Until the vert.x jars are available in the Maven central repository you will also have to manually install these into your local Maven repository too.
You can find them in lib/jars folder of the vert.x distro.  

	mvn install:install-file -Dfile=vert.x-platform.jar -DgroupId=org.vertx -DartifactId=platform -Dversion=1.0.0.final -Dpackaging=jar  
	
	mvn install:install-file -Dfile=vert.x-core.jar -DgroupId=org.vertx -DartifactId=core -Dversion=1.0.0.final -Dpackaging=jar  
      	

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

This goal will run a verticle or vert.x module in it's own vert.x instance.  vert.x will continue to run until the plugin is explicitly stopped.  Simply type:

	mvn vertx:run
	
The plugin forks a parallel lifecycle to ensure that the "package" phase has been completed before invoking 
vert.x. This means that you do not need to explicitly execute a "mvn package" first. It also means that a 
"mvn clean vertx:run" will ensure that a full fresh compile and package is done before invoking vert.x.  
	
For Java verticles, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.2-RELEASE</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
	</plugin>  
	
For Groovy verticles, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.2-RELEASE</version>
		<configuration>
			<verticleName>com/acme/MyVerticle.groovy</verticleName>
		</configuration>
	</plugin>  
	
For Javascript verticles, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.2-RELEASE</version>
		<configuration>
			<verticleName>src/main/javascript/com/acme/MyVerticle.js</verticleName>
		</configuration>
	</plugin>  

For modules, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.2-RELEASE</version>
		<configuration>
			<moduleName>some-module-name</moduleName>
			<moduleRepoUrl>http://some.module.repo.url</moduleRepoUrl>
		</configuration>
	</plugin>  

	Note that the moduleRepoUrl parameter is optional, the default value is: http://github.com/vert-x/vertx-mods
	
Sometimes you want automatic execution of the plugin, for example when doing integration testing.
To do this you can run the plugin in Maven execution scenarios and use the daemon=true configuration option to prevent vert.x from running indefinitely.

	<plugin>
		<groupId>org.vertx</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.0.2-RELEASE</version>
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

If you need to use any of the out-of-the-box mods then you need a local vert.x install and set the vertxHomeDirectory Maven configuration option. If you need to use any 3rd party mods you will have to wait for the next release :)


This plugin currently works for all verticle languages except Ruby.



	