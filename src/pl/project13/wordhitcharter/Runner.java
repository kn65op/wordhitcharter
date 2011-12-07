package pl.project13.wordhitcharter;

import com.google.common.collect.ImmutableList;
import pl.project13.wordhitcharter.model.PdfDescriptor;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class Runner {

  private static final String SEARCH_PHRASE = "cryptography";
  private static final String PDF_FOLDER = "C:\\Download\\arty2";

  KeyWordsInYearCharter wordHitCharter = new KeyWordsInYearCharter();
  ByYearCharter byYearCharter = new ByYearCharter();

  public static void main(String[] args) throws IOException {
    new Runner().run();
  }

  private void run() throws IOException {
    ImmutableList<PdfDescriptor> allPdfs = findPdfs();

    System.out.println("FOUND " + allPdfs.size() + " PDF FILES...");

    System.out.println("Checking how many contain '" + SEARCH_PHRASE + "'");
    wordHitCharter.searchForPhrase(SEARCH_PHRASE)
                  .inGivenPdfs(allPdfs)
                  .createLineChart()
                  .openInBrowser();

    System.out.println("Graphing articles in years...");
    byYearCharter.inGivenPdfs(allPdfs)
                 .createLineChart()
                 .openInBrowser();
  }

  private static ImmutableList<PdfDescriptor> findPdfs() {
    ImmutableList.Builder<PdfDescriptor> builder = ImmutableList.builder();
    PdfVisitor visitor = new PdfVisitor();

    try {
      Files.walkFileTree(Paths.get(PDF_FOLDER), visitor);
    } catch (IOException e) {
      System.out.println("Exception while finding PDFs..." + e);
    }

    builder.addAll(visitor.getFoundPdfs());

    return builder.build();
  }

  private static class PdfVisitor extends SimpleFileVisitor<Path> {
    List<PdfDescriptor> foundPdfs = newArrayList();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      if (file.toString().endsWith(".pdf")) {
        String filename = file.toString();

        foundPdfs.add(PdfDescriptor.from(filename));
      }

      return FileVisitResult.CONTINUE;
    }

    public List<PdfDescriptor> getFoundPdfs() {
      return foundPdfs;
    }
  }
}
