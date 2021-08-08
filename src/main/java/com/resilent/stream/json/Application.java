package com.resilent.stream.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.resilient.stream.Flow;
import com.resilient.stream.Server;
import com.resilient.stream.http.request.method.Get;
import com.resilient.stream.http.request.method.Post;
import com.resilient.stream.http.response.Alternate;
import com.resilient.stream.http.response.Static;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

public class Application {

  static Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception{

    Server server = new Server();

    Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
    new Flow(server)
      .when(new Get("/*").with((s, m, p) -> new File("static","/".equals(p)? "index.html": p).exists()))
      .then((ctx, r) -> new Static(new File("static","/".equals(ctx.path().plain())? "index.html": ctx.path().plain())));

    new Flow(server).when(new Post("/execute")).as(Map.class).then(map -> {
      try {
        return gson.toJson(gson.fromJson((String) map.get("value"), Map.class));
      } catch (Exception e) {
        throw new Alternate(200, e.getMessage());
      }
    });

    server.start();

  }

}
