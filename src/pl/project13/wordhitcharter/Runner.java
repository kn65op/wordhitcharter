package pl.project13.wordhitcharter;

import com.google.common.collect.ImmutableList;
import pl.project13.wordhitcharter.model.PdfDescriptor;

import java.io.IOException;

public class Runner {

  private static final String SEARCH_PHRASE = "DNA";

  public static void main(String[] args) throws IOException {
    KeyWordsInYearCharter wordHitCharter = new KeyWordsInYearCharter();

    wordHitCharter.searchForPhrase(SEARCH_PHRASE)
                  .inGivenPdfs(findPdfs())
                  .createLineChart()
                  .openInBrowser();
  }

  private static ImmutableList<PdfDescriptor> findPdfs() {
    ImmutableList.Builder<PdfDescriptor> builder = ImmutableList.<PdfDescriptor>builder()
                                                     .add(new PdfDescriptor(2011, "/home/ktoso/2011 Sponsor Package.pdf"));
    return builder.build();
  }
}
