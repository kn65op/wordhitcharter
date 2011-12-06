package pl.project13.wordhitcharter;


import pl.project13.wordhitcharter.chart.GoogleChartHtmlMaker
import pl.project13.wordhitcharter.chart.LineChartHtmlMaker
import pl.project13.wordhitcharter.model.PdfDescriptor
import pl.project13.wordhitcharter.util.BrowserHelper

class ByYearCharter {

  private static final String YEAR = "Year"
  private static final String COUNT = "Article Count"
  private static final String TITLE = "Articles By Year"

  GoogleChartHtmlMaker chartMaker = new LineChartHtmlMaker()

  private File chartFile

  // year -> article count (word hits)
  private def hitsPerYear = [:].withDefault { 0 }

  ByYearCharter inGivenPdfs(List<PdfDescriptor> pdfs) {
    pdfs.each { pdf ->
      hitsPerYear[pdf.year] = hitsPerYear[pdf.year] + 1
    }

    this
  }

  public ByYearCharter createLineChart() {
    chartMaker.setTitle(TITLE)

    chartMaker.addStringColumn(YEAR);
    chartMaker.addNumberColumn(COUNT);

    hitsPerYear.keySet()
               .sort()
               .each { year ->
                        if (year > 0) {
                          def articleCount = hitsPerYear[year]

                          println "$articleCount articles in the year $year"
                          chartMaker.addRow(YEAR, year)
                          chartMaker.addRow(COUNT, articleCount)
                        }
               }

    chartFile = chartMaker.printPageToTempFile();

    this
  }

  ByYearCharter openInBrowser() throws IOException {
    if (chartFile != null) {
      BrowserHelper.openLineChartInBrowser("file:///$chartFile.absolutePath")
    }

    this
  }
}
