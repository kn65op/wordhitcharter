package pl.project13.wordhitcharter.chart

class BarChartHtmlMaker extends GoogleChartHtmlMaker {

  @Override
  def getVisualisationClassName() {
    "google.visualization.BarChart"
  }
}
