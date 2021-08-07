package com.resilent.stream.json;

import com.resilient.stream.Flow;
import com.resilient.stream.Server;
import com.resilient.stream.http.request.method.Get;
import com.resilient.stream.http.response.Static;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Application {

    static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception{

        Server server = new Server();

        new Flow(server)
          .when(new Get("/*").with((s, m, p) -> new File("static","/".equals(p)? "index.html": p).exists()))
          .then((ctx, r) -> new Static(new File("static","/".equals(ctx.path().plain())? "index.html": ctx.path().plain())));

        server.start();

    }

}
