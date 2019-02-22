package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Greeting;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  private static final String template = "Hello, %s!";

  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    return new Greeting(String.format(template, name));
  }
}
