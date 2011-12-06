package pl.project13.wordhitcharter.pdf;

import com.google.common.base.Stopwatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.util.Calendar;

public class CreationYearExtractor {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public int fromFile(String location) {
    try {
      Stopwatch stopwatch = new Stopwatch().start();

      StringWriter outputWriter = new StringWriter();
      PDDocument document = PDDocument.load(location);

      PDDocumentInformation documentInformation = document.getDocumentInformation();
      Calendar creationDate = documentInformation.getCreationDate();

//      System.out.println("Processing took: " + stopwatch.stop());

      return creationDate.get(Calendar.YEAR);
    } catch (Exception ex) {
      System.out.println("Unable to process: " + location);
      System.err.println(ex);
    }

    return 0;
  }
}
