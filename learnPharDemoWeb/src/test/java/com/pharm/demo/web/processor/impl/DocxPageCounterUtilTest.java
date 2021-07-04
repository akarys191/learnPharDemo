/*
package com.pharm.demo.web.processor.impl;

import com.pharm.demo.DocxPageCounterUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.poifs.filesystem.FileMagic;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class DocxPageCounterUtilTest {

    private InputStream docInputStream;
    private DocxPageCounterUtil docxPageCounterUtil;
    @Before
    public void initDocPageTest() {
        this.docInputStream = this.getClass().getResourceAsStream("/KURSOVOI.docx");
    }

    @Test
    public void testDocPages() throws Exception {
        //when
        int pageNumbersInZip = DocxPageCounterUtil.findPageNumbersInZip(this.docInputStream);

        assertEquals(pageNumbersInZip, 20);
    }



}
*/
