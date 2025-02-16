package lab1;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HtmlElement {
  DIV("div"),
  H5("h5"),
  SPAN("span"),
  BUTTON("button"),
  A("A");

  private final String value;
}
