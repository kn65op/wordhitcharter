package pl.project13.wordhitcharter;


import pl.project13.wordhitcharter.chart.GoogleChartHtmlMaker
import pl.project13.wordhitcharter.chart.LineChartHtmlMaker
import pl.project13.wordhitcharter.model.PdfDescriptor
import pl.project13.wordhitcharter.pdf.TextExtractor
import pl.project13.wordhitcharter.util.BrowserHelper

class KeyWordsInYearCharter {

  private static final String YEAR = "Year"
  private static final String COUNT = "Count"

  TextExtractor textExtractor = new TextExtractor()
  GoogleChartHtmlMaker chartMaker = new LineChartHtmlMaker()

  private File chartFile
  private String searchPhrase

  // year -> article count (word hits)
  private def hitsPerYear = [:].withDefault { 0 }

  KeyWordsInYearCharter searchForPhrase(String phrase) {
    searchPhrase = phrase

    this;
  }

  KeyWordsInYearCharter inGivenPdfs(List<PdfDescriptor> pdfs) {
    pdfs.each { pdf ->
      String contents = textExtractor.fromFileToString(pdf.location);

      if (contents != null && contents.contains(searchPhrase)) {
        hitsPerYear[pdf.year] = hitsPerYear[pdf.year] + 1
      }
    }

    this
  }

  public KeyWordsInYearCharter createLineChart() {
    chartMaker.addStringColumn(YEAR);
    chartMaker.addNumberColumn(COUNT);

    hitsPerYear.each { year, articleCount ->
      chartMaker.addRow(YEAR, year)
      chartMaker.addRow(COUNT, articleCount)
    }

    chartFile = chartMaker.printPageToTempFile();

    this
  }

  KeyWordsInYearCharter openInBrowser() throws IOException {
    if (chartFile != null) {
      String path = "file:///$chartFile.absolutePath"
      path = path.replaceAll("\\\\", "/")
      BrowserHelper.openLineChartInBrowser(path)
    }

    this
  }
}
