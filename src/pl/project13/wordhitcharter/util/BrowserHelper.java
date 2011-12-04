package pl.project13.wordhitcharter.util;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class BrowserHelper {

  public static void openLineChartInBrowser(String url) throws IOException {
    if (Desktop.isDesktopSupported()) {
      Desktop desktop = Desktop.getDesktop();
      desktop.browse(URI.create(url));
    } else {
      System.out.println("Please open: " + url);
    }
  }
}
