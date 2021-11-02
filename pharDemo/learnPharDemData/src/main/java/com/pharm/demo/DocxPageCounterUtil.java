/*
package com.pharm.demo;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class DocxPageCounterUtil {

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".tmp";
    private static final Logger LOGGER = LoggerFactory.getLogger(DocxPageCounterUtil.class);

    public static int findPageNumbersInZip(final InputStream inputStream) {
        try {
            ZipFile zf = new ZipFile(stream2file(inputStream));

            int i = 0;
            for (final Enumeration e = zf.entries(); e.hasMoreElements();) {
                InputStream zipItemInputstream = null;
                try {
                    ZipEntry entry = (ZipEntry) e.nextElement();
                    zipItemInputstream = zf.getInputStream(entry);
                    if(Objects.nonNull(zipItemInputstream) && entry.getName().endsWith("document.xml")){
                        String text = getText(zipItemInputstream);
                        return getPageCount(text);
                    }
                } catch (IOException ex) {
                    LOGGER.error("IOException while counting pages  {}", ex.getCause());
                } finally {
                    try {
                        zipItemInputstream.close();
                    } catch (IOException ex) {
                        //Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        } catch (IOException ex) {
            LOGGER.error("IOException while counting pages  {}", ex.getCause());
        }

        return 1;
    }

    private static int getPageCount(String text) {
        int originalPageCount = 1;
        originalPageCount += StringUtils.countMatches(text, "<w:lastRenderedPageBreak/>");
        return originalPageCount;
    }

    private static File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    private static String getText(final InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int length;
        byte[] data = new byte[1024];

        while ((length = inputStream.read(data)) != -1) {
            outputStream.write(data, 0, length);
        }

        return outputStream.toString(StandardCharsets.UTF_8.name());
    }
}
*/
