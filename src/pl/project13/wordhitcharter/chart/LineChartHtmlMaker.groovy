package pl.project13.wordhitcharter.chart

class LineChartHtmlMaker extends GoogleChartHtmlMaker {

  @Override
  def getVisualisationClassName() {
    "google.visualization.LineChart"
  }
}
