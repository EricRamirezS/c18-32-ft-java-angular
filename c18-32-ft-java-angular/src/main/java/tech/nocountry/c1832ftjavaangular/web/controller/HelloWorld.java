package tech.nocountry.c1832ftjavaangular.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
  @GetMapping("/hola-mundo")
  public String holaMundo(){
    return "Hola Mundo";
  }

}
