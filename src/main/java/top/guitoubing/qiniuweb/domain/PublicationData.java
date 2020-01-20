package top.guitoubing.qiniuweb.domain;

public class PublicationData {
  private String name;
  private String author;
  private String type;
  private String description;
  private String url;

  public PublicationData(String name, String author, String type, String description, String url) {
    this.name = name;
    this.author = author;
    this.type = type;
    this.description = description;
    this.url = url;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
