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

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.vertx.java.deploy.impl.cli.VertxMgr;

/**
 * <p>
 * This goal is used to run a vert.x verticle in it's own instance.
 * </p>
 * 
 * @goal run
 * @description Runs vert.x directly from a Maven project.
 */
public class VertxRunMojo extends AbstractMojo {

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

	public void execute() throws MojoExecutionException {
		getLog().info("Launching verticle [" + verticleName + "]");

		VertxMgr.main(new String[] { "run", verticleName, "-cp",
				getDefaultClasspathString() });
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
			defaultClasspath += mavenProject.getBuild().getDirectory() + "\\"
					+ mavenProject.getBuild().getFinalName() + "."
					+ mavenProject.getPackaging();
		}

		return defaultClasspath;
	}
}
