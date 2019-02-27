package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Greeting;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GreetingRepository extends MongoRepository<Greeting, String> {
  public Greeting findByContent(String content);
}
