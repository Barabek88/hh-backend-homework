package ru.hh.school;

import ru.hh.nab.starter.NabApplication;
import ru.hh.school.config.ProdConfig;

import java.util.Properties;

public class App {

  public static void main(String[] args) {
    Properties p = new Properties(System.getProperties());
    p.put("settingsDir", "backend/src/etc");
    System.setProperties(p);

    NabApplication
            .builder()
            .configureJersey()
            .bindToRoot()
            .build()
            .run(ProdConfig.class);
  }
}
