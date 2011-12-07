package pl.project13.wordhitcharter.pdf

import groovy.sql.Sql

class PdfFromDbFetcher {
  def sql

  static void main(args) {
    new PdfFromDbFetcher().saveAsFilesFromDb("dnas")
  }

  PdfFromDbFetcher() {
    def db = [url: 'jdbc:mysql://127.0.0.1/medycyna', user: 'root', password: '_______', driver: 'com.mysql.jdbc.Driver']
    sql = Sql.newInstance(db.url, db.user, db.password, db.driver)
  }
  
  def saveAsFilesFromDb(keyword) {
    sql.eachRow("""SELECT *
                   FROM  `articles`
                   WHERE  `keywords` LIKE ?
    """, ['%dna%']) { row ->
      def filename = "${row.idarticles}_${row.database}_${row.year}.pdf"
      println "Will create $filename..."

      def file = new File("/home/ktoso/coding/wordhitcharter/pdfs/$filename")
      file.bytes = row.pdf
    }
  }
}
