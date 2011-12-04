package pl.project13.wordhitcharter.chart

abstract class GoogleChartHtmlMaker {

  private static final String STRING = 'string'
  private static final String NUMBER = 'number'

  def title = ""
  def width = 450
  def height = 300

  def columns = []
  def rows = []

  File printPageToTempFile() {
    def tmp = File.createTempFile("chartmaker", null)
    tmp.text = getPage()

    tmp
  }

  abstract def getVisualisationClassName();

  String getPage() {
    """
    <html>
    <head>
      <!--Load the AJAX API-->
      <script type="text/javascript" src="https://www.google.com/jsapi"></script>
      <script type="text/javascript">

        // Load the Visualization API and the piechart package.
        google.load('visualization', '1.0', {'packages':['corechart']});

        // Set a callback to run when the Google Visualization API is loaded.
        google.setOnLoadCallback(drawChart);


        // Callback that creates and populates a data table,
        // instantiates the pie chart, passes in the data and
        // draws it.
        function drawChart() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        ${addColumnsJs()}
        ${addRowsJs()}

        // Set chart options
        var options = {
                       'title':  '$title',
                       'width':  $width,
                       'height': $height
                      };

        // Instantiate and draw our chart, passing in some options.
        var chart = new ${getVisualisationClassName()}(document.getElementById('chart_div'));
        chart.draw(data, options);
      }
      </script>
    </head>

    <body>
    <!--Div that will hold the pie chart-->
      <div id="chart_div" style="width:$width; height:$height"></div>
    </body>
    </html>
  """
  }

  def addStringColumn(columnName) {
    columns << [type: STRING, name: columnName]
  }

  def addNumberColumn(columnName) {
    columns << [type: NUMBER, name: columnName]
  }

  def addRow(columnName, value) {
    def columnByName = {column -> column.name == columnName}

    if (columns.any(columnByName)) {
      def columnIndex = columns.findIndexOf(columnByName)
      def column = columns[columnIndex]
      def escapedValue = column.type == STRING ? "'$value'" : value;

      def item = [colNum: columnIndex, colName: column.name, value: escapedValue]
      rows.add(item)
    } else {
      throw new RuntimeException("Unable to find such column name ('$columnName') to map to...")
    }
  }

  private def addRowsJs() {
    def rowsCount = rows.size()

    def js = "  data.addRows($rowsCount);\n"

    def groupedByColumn = rows.groupBy {row -> row.colNum}

    groupedByColumn.each {columnNumber, rows ->
      rows.eachWithIndex { item, index ->
        js += "  data.setValue($index, $columnNumber, $item.value); /*$item.colName*/\n"
      }
    }
    
    js
  }

  private def addColumnsJs() {
    def js = ""
    columns.each {col ->
      js += "data.addColumn('$col.type', '$col.name');\n"
    }

    js
  }

}
