package com.resilient.stream.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.resilient.stream.Flow;
import com.resilient.stream.Server;
import com.resilient.stream.http.request.method.Get;
import com.resilient.stream.http.request.method.Post;
import com.resilient.stream.http.response.Alternate;
import com.resilient.stream.http.response.Static;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:arief.wara@gmail.com">Arief Warazuhudien</a>
 */
public class Application {

  static Logger logger = LoggerFactory.getLogger(Application.class);

  public static void main(String[] args) throws Exception{

    Server server = new Server();
    logger.info(new File("static").getAbsolutePath());
    Gson gson = new GsonBuilder().setPrettyPrinting().setLenient().create();
    new Flow(server)
      .when(new Get("/*").with((s, m, p) -> new File("static","/".equals(p)? "index.html": p).exists()))
      .then((ctx, r) -> new Static(new File("static","/".equals(ctx.path().plain())? "index.html": ctx.path().plain())));

    new Flow(server).when(new Post("/execute")).as(Map.class).then(map -> {
      try {
        String value = ((String) map.get("value")).trim();
        return gson.toJson(gson.fromJson(value, StringUtils.startsWith(value, "[")? List.class: Map.class));
      } catch (Exception e) {
        throw new Alternate(200, e.getMessage());
      }
    });

    server.start();

  }

}
