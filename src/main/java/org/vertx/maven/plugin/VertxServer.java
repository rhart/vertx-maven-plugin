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

import org.vertx.java.deploy.impl.cli.Starter;

import java.util.List;

public class VertxServer {

    private static final String VERTX_RUN_COMMAND = "run";

    public void run(List<String> serverArgs) {
        this.run(serverArgs, false);
    }

    public void run(final List<String> serverArgs, boolean daemon) {
        serverArgs.add(0, VERTX_RUN_COMMAND);

        final Thread managerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final String[] args = serverArgs.toArray(new String[serverArgs.size()]);
                Starter.main(args);
            }
        }, "Vertx Manager Thread");
        
        managerThread.start();
        
        if (!daemon) {
            try {
                managerThread.join();
            } catch (InterruptedException e) {
                System.err.println("Unexpected thread interupt while waiting for vertx manager thread:");
                e.printStackTrace();
            }
        }
    }
}
