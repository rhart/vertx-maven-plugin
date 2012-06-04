package org.vertx.maven.plugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * <p>
 * This goal is used to run a vert.x verticle in it's own instance.
 * </p>
 * <p>
 * The plugin forks a parallel lifecycle to ensure that the "package" phase has 
 * been completed before invoking vert.x. This means that you do not need to 
 * explicitly execute a "mvn package" first. It also means that a "mvn clean vertx:run" 
 * will ensure that a full fresh compile and package is done before invoking vert.x.
 * </p>
 * 
 * @goal run
 * @execute phase="package"
 * @description Runs vert.x directly from a Maven project.
 */
public class VertxRunMojo extends AbstractMojo {

	private static final String VERTX_INSTALL_SYSTEM_PROPERTY = "vertx.install";

	/**
	 * The Maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 */
	protected MavenProject mavenProject;

	/**
	 * The name of the verticle to run.
	 * 
	 * If you're running a verticle written in JavaScript, Ruby, or Groovy then
	 * it's just the name of the script, e.g. server.js, server.rb, or
	 * server.groovy.
	 * 
	 * If the verticle is written in Java, the name is the fully qualified class
	 * name of the Main class e.g. com.acme.MyVerticle.
	 * 
	 * @parameter expression="${run.verticleClass}"
	 * @required
	 */
	private String verticleName;

	/**
	 * Determines whether the verticle is a worker verticle or not. The default
	 * is false.
	 * 
	 * @parameter expression="${run.worker}" default-value=false
	 */
	private boolean worker;
	
	/**
	 * <p>
     * Determines whether or not the server blocks when started. The default
     * behaviour (daemon = false) will cause the server to pause other processes
     * while it continues to run the verticle. This is useful when starting the
     * server with the intent to work with it interactively.
     * </p><p>
     * Often, it is desirable to let the server start and continue running subsequent
     * processes in an automated build environment. This can be facilitated by setting
     * daemon to true.
     * </p>
	 * 
	 * @parameter expression="${run.daemon}" default-value=false
	 */
	private boolean daemon;
	
	/**
	 * <p>
	 * The config file for this verticle.
	 * </p><p>
	 * If the path is relative (does not start with / or a drive letter like C:), the path 
	 * is relative to the directory containing the POM.
	 * </p><p>
	 * An example value would be src/main/resources/com/acme/MyVerticle.conf
	 * </p>
	 * 
	 * @parameter expression="${run.configFile}"
	 */
	private File configFile;
	
	/**
     * The number of instances of the verticle to instantiate in the vert.x server. The
     * default is 1.
     *
     * @parameter expression="${run.instances}" default-value=1
     */
    private Integer instances;
    
    /**
     * <p>
	 * The path on which to search for the main and any other resources used by the verticle. 
	 * </p><p>
	 * If your verticle references other scripts, classes or other resources (e.g. jar files) then 
	 * make sure these are on this path. The path can contain multiple path entries separated by : (colon).
	 * </p>
	 * 
	 * @parameter expression="${run.classpath}"
	 */
	private String classpath;
	
	/**
     * <p>
	 * The home directory of your vert.x installation i.e. where you unzipped the vert.x distro.  
	 * For example C:/vert.x/vert.x-1.0.1.final
	 * </p><p>
	 * You will need to set this configuration option if you want to run any out-of-the box modules like web-server.
	 * </p>
	 * 
	 * @parameter expression="${run.vertxHomeDirectory}"
	 */
	private String vertxHomeDirectory;

	public void execute() throws MojoExecutionException {
		getLog().info("Launching verticle [" + verticleName + "]");
		
		if (vertxHomeDirectory != null) {
			System.setProperty(VERTX_INSTALL_SYSTEM_PROPERTY, vertxHomeDirectory);
		}
				
		List<String> args = new ArrayList<String>();
		args.add(verticleName);
		args.add("-cp");
		args.add(getDefaultClasspathString() + (classpath != null ? (":" + classpath) : ""));
		
		if (worker) {
			args.add("-worker");
		}
		
		if (configFile != null) {
			args.add("-conf");
			args.add(configFile.getAbsolutePath());
		}
		
		args.add("-instances");
		args.add(instances.toString());

		new VertxServer().run(args, daemon); 
	}

	/**
	 * Build a default classpath.
	 * 
	 * If the packaging of the Maven project is jar then add the path of the
	 * compiled jar file to the default classpath.
	 * 
	 * @return default classpath
	 */
	private String getDefaultClasspathString() {
		String defaultClasspath = "";

		if (mavenProject.getPackaging().toUpperCase().equals("JAR")) {
			defaultClasspath += mavenProject.getBuild().getDirectory().replace("\\", "/") + "/"
					+ mavenProject.getBuild().getFinalName() + "."
					+ mavenProject.getPackaging();
		}

		getLog().debug("Default classpath [" + defaultClasspath + "]");
		return defaultClasspath;
	}
}
