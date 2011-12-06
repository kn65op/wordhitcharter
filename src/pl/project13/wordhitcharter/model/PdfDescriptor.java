package pl.project13.wordhitcharter.model;

import pl.project13.wordhitcharter.pdf.CreationYearExtractor;

public class PdfDescriptor {
  public final int year;
  public final String location;

  private PdfDescriptor(int year, String location) {
    this.year = year;
    this.location = location;
  }
  
  public static PdfDescriptor from(String location) {
    int year = new CreationYearExtractor().fromFile(location);

    return new PdfDescriptor(year, location);
  }
  
}
