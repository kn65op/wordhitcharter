package pl.project13.wordhitcharter;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;

public class WordHitCharter {

  private final Logger log = LoggerFactory.getLogger(getClass());

  private String[] data;


  public void givenPdfs(String... locations) throws IOException {
    for (String location : locations) {
      String contents = fromFileToString(location);

      if (contents != null) {
        // todo zrob z tym cos
      }
    }
  }

  private String fromFileToString(String location) {
    try {
      Stopwatch stopwatch = new Stopwatch().start();

      StringWriter outputWriter = new StringWriter();
      PDDocument document = PDDocument.load(location);

      PDFTextStripper stripper;
      stripper = new PDFTextStripper(Charsets.UTF_8.name());
      stripper.setForceParsing(true);

      stripper.writeText(document, outputWriter);
      log.info("Processing took: {}", stopwatch.stop());

      return outputWriter.toString();
    } catch (Exception ex) {
      log.error("Unable to process pdf [{}]", location);
    }

    return null;
  }

}
