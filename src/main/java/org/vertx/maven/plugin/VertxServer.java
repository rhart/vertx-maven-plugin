package org.vertx.maven.plugin;

import java.util.List;

import org.vertx.java.deploy.impl.cli.VertxMgr;

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
			VertxMgr.main(this.args);
		}
	}
}
