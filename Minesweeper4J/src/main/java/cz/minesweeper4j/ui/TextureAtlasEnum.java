package cz.minesweeper4j.ui;

public enum TextureAtlasEnum {

  Clock("Clock")
  ,Empty("Empty")
  ,Flag("Flag")
  ,Mine("Mine")
  ,N1("N1")
  ,N2("N2")
  ,N3("N3")
  ,N4("N4")
  ,N5("N5")
  ,N6("N6")
  ,N7("N7")
  ,N8("N8")
  ,Slot("Slot")
  ;

  public final String texture;

  private TextureAtlasEnum(String name) {
    texture = name;
  }

}
