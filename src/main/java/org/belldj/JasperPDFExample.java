package org.belldj;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

public class JasperPDFExample {

  @SuppressWarnings("preview")
  public static void main(String[] args) throws Exception {

    // Our json object. This can be loaded from file
    String rawJsonData = readFile("data.json");

    InputStream is = JasperPDFExample.class.getClassLoader().getResourceAsStream("samle.jrxml");
    if (is == null) {
      throw new Exception("file not found");
    }

    JasperDesign design = JRXmlLoader.load(is);
    JasperReport report = JasperCompileManager.compileReport(design);

    ByteArrayInputStream json_stream = new ByteArrayInputStream(rawJsonData.getBytes());
    JsonDataSource json_ds = new JsonDataSource(json_stream);
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("title", "Jasper PDF Example");
    parameters.put("name", "Name");
    parameters.put("value", "Value");

    JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, json_ds);
    JasperExportManager.exportReportToPdfFile(jasperPrint, "jasperpdfexample.pdf");

  }

  private static String readFile(String filename) throws Exception {
    assert filename != null : "File \"" + filename + "\" not found";
    InputStream is = JasperPDFExample.class.getClassLoader().getResourceAsStream(filename);
    StringBuilder sb = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
      String line;
      while ((line = br.readLine()) != null)
        sb.append(line).append("\n");
    }
    return sb.toString();
  }
}
