package pl.project13.wordhitcharter.pdf;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;

public class TextExtractor {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public String fromFileToString(String location) {
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
