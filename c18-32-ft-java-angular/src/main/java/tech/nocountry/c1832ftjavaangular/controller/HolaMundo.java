package tech.nocountry.c1832ftjavaangular.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HolaMundo {
  @GetMapping("/holamundo")
  public String holaMundo(){
    return "Hola Mundo";
  }

}
