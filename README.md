vertx-maven-plugin
==================

Maven Plugin for running verticles in their own vert.x instance.

Install
-----
This plugin is now available on Maven Central.

Group ID: org.anacoders.plugins

Artifact ID: vertx-maven-plugin

Current release version: 1.2.3.4


Versions
--------

This plugin's versions are aligned with vert.x versions with the minor version number to indicate increments of the plugin.
e.g. vert.x 1.2.3.FINAL would be 1.2.3.x

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
		<groupId>org.anacoders.plugins</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.2.3.4</version>
		<configuration>
			<verticleName>com.acme.MyVerticle</verticleName>
		</configuration>
	</plugin>  
	
For Groovy verticles, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.anacoders.plugins</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.2.3.4</version>
		<configuration>
			<verticleName>com/acme/MyVerticle.groovy</verticleName>
		</configuration>
	</plugin>  
	
For Javascript verticles, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.anacoders.plugins</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.2.3.4</version>
		<configuration>
			<verticleName>src/main/javascript/com/acme/MyVerticle.js</verticleName>
		</configuration>
	</plugin>  

For modules, the plugin will need to be configured in your project's POM as follows:

	<plugin>
		<groupId>org.anacoders.plugins</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.2.3.4</version>
		<configuration>
			<moduleName>some-module-name</moduleName>
			<moduleRepoUrl>http://some.module.repo.url</moduleRepoUrl>
		</configuration>
	</plugin>  

	Note that the moduleRepoUrl parameter is optional, the default value is: http://github.com/vert-x/vertx-mods
	
Sometimes you want automatic execution of the plugin, for example when doing integration testing.
To do this you can run the plugin in Maven execution scenarios and use the daemon=true configuration option to prevent vert.x from running indefinitely.

	<plugin>
		<groupId>org.anacoders.plugins</groupId>
		<artifactId>vertx-maven-plugin</artifactId>
		<version>1.2.3.4</version>
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


This plugin currently works for all verticle languages except Ruby and Python.



	