package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

public class Greeting {
  @Id
  private String id;
  private String content;

  public Greeting(String content) {
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
