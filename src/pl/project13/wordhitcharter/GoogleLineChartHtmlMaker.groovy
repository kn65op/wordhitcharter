package pl.project13.wordhitcharter

class GoogleLineChartHtmlMaker {

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
        var chart = new google.visualization.PieChart(document.getElementById('chart_div'));
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

      def item = [colNum: columnIndex, value: escapedValue]
      rows.add(item)
    } else {
      throw new RuntimeException("Unable to find such column name ('$columnName') to map to...")
    }
  }

  private def addRowsJs() {
    def js = "  data.addRows(${rowsCount()});\n"

    rowsCount().times { rowNumber ->
      def row = row(rowNumber)

      js += "  data.setValue($rowNumber, $row.colNum, $row.value);\n"
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

  private
  def row(rowNumber) {
    rows[rowNumber]
  }

  private
  def rowsCount() {
    rows.size()
  }

}
