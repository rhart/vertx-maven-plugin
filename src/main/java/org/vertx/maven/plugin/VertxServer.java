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

import java.util.List;

import org.vertx.java.deploy.impl.cli.Starter;

public class VertxServer {
	
	private static final String VERTX_RUN_COMMAND = "run";

	public void run(List<String> serverArgs) {
		this.run(serverArgs, false); 
	}
	
	public void run(List<String> serverArgs, boolean daemon) {
		serverArgs.add(0, VERTX_RUN_COMMAND);
		
		VertxManager managerThread = new VertxManager(serverArgs.toArray(new String[serverArgs.size()]));
		managerThread.start();
		if (!daemon) {
			try {
				managerThread.join();
			} catch (InterruptedException e) {
				//TODO not sure what needs to happen here yet.
			}
		}
	}
	
	
	private class VertxManager extends Thread {
		
		private String[] args;
		
		public VertxManager(String[] args) {
			this.args = args;
		}

		@Override
		public void run() {
			Starter.main(this.args);
		}
	}
}
