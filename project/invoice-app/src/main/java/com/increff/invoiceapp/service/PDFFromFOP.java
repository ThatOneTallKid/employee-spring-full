package com.increff.invoiceapp.service;

import org.apache.fop.apps.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

public class PDFFromFOP {
    public String createPDF(String xmlEncodedString) throws Exception {
        try {
            File xmlfile = new File("C:\\Users\\KIIT\\Desktop\\Projects\\Pos_project\\project\\invoice-app\\src\\main\\resources\\xml\\Invoice.xml");
            File xsltfile = new File("C:\\Users\\KIIT\\Desktop\\Projects\\Pos_project\\project\\invoice-app\\src\\main\\resources\\xsl\\Invoice.xsl");
            File pdfDir = new File("./Test");
            pdfDir.mkdirs();
            File pdfFile = new File(pdfDir, "invoice.pdf");
            System.out.println(pdfFile.getAbsolutePath());
            // configure fopFactory as desired
            final FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
            // configure foUserAgent as desired
            // Setup output
            OutputStream outfile = new FileOutputStream(pdfFile);
            outfile = new java.io.BufferedOutputStream(outfile);
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            try {
                // Construct fop with desired output format
                Fop fop;
                fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);
                // Setup XSLT
                TransformerFactory factory = TransformerFactory.newInstance();
                Transformer transformer = factory.newTransformer(new StreamSource(xsltfile));
                // Setup input for XSLT transformation
                Source src =convert(xmlEncodedString);
                // Resulting SAX events (the generated FO) must be piped through to FOP
                Result res = new SAXResult(fop.getDefaultHandler());
                // Start XSLT transformation and FOP processing
                transformer.transform(src, res);
            } catch (FOPException | TransformerException e) {
                e.printStackTrace();
            } finally {
                outfile.close();
                byte[] pdf = out.toByteArray();
                String base64 = Base64.getEncoder().encodeToString(pdf);
                return base64;
            }
        }catch(Exception exp){
            exp.printStackTrace();
        }
        return null;
    }

    private static StreamSource convert(String base64EncodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedString);
        ByteArrayInputStream bais = new ByteArrayInputStream(decodedBytes);
        return new StreamSource(bais);
    }
}