package pl.project13.wordhitcharter.chart

class PieChartHtmlMaker extends GoogleChartHtmlMaker {

  @Override
  def getVisualisationClassName() {
    "google.visualization.PieChart"
  }
}
