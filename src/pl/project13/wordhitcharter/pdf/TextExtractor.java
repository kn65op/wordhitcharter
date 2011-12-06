package pl.project13.wordhitcharter.pdf;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import java.io.StringWriter;

public class TextExtractor {

  public TextExtractor() {
    System.out.println("Created text extractor...");
  }

  public String fromFileToString(String location) {
    try {
      Stopwatch stopwatch = new Stopwatch().start();

      StringWriter outputWriter = new StringWriter();
      PDDocument document = PDDocument.load(location);

      PDFTextStripper stripper;
      stripper = new PDFTextStripper(Charsets.UTF_8.name());
      stripper.setForceParsing(true);

      stripper.writeText(document, outputWriter);
      System.out.print('.');
//      System.out.println("Processing took: " + stopwatch.stop());

      return outputWriter.toString();
    } catch (Exception ex) {
      System.err.println(ex);
    }

    return null;
  }
}
