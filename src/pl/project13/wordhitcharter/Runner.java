package pl.project13.wordhitcharter;

import java.io.File;
import java.io.IOException;

public class Runner {

  public static void main(String[] args) throws IOException {
    WordHitCharter wordHitCharter = new WordHitCharter();

    wordHitCharter.givenPdfs("/home/ktoso/2011 Sponsor Package.pdf");

    GoogleLineChartHtmlMaker chartMaker = new GoogleLineChartHtmlMaker();
    chartMaker.addStringColumn("Word");
    chartMaker.addNumberColumn("Count");

    chartMaker.addRow("Word", "Konrad");
    chartMaker.addRow("Count", "12");

    chartMaker.addRow("Word", "Bok");
    chartMaker.addRow("Count", "158");

    File file = chartMaker.printPageToTempFile();

    BrowserHelper.openLineChartInBrowser("file:///" + file.getAbsolutePath());
  }
}
