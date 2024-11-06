package ca.codepet.util;

public enum FAIcons {
  CHART("\uE473"),
  MAP("\uF279"),
  HELP("\uF059"),
  SETTINGS("\uF013"),
  STAR("\uF005"),
  USER("\uF007");

  private final String unicode;

  FAIcons(String unicode) {
    this.unicode = unicode;
  }

  public String code() {
    return unicode;
  }

}
