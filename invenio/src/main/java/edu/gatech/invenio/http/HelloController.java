package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Greeting;
import edu.gatech.invenio.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
  private static final String template = "Hello, %s!";

  private @Autowired GreetingRepository repository;

  @GetMapping(value = "/greeting", produces = MediaType.APPLICATION_JSON_VALUE)
  public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    repository.save(new Greeting(String.format(template, name)));

    return repository.findByContent(name);
  }
}
