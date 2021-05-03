package com.urise.webapp.util;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class JsonParserTest {
    @Test
    public void testResume() throws Exception {
        Resume resumeOld = ResumeTestData.newResume(UUID.randomUUID().toString(), "Name1");
        String json = JsonParser.write(resumeOld);
        System.out.println(json);
        Resume resumeNew = JsonParser.read(json, Resume.class);
        Assert.assertEquals(resumeOld, resumeNew);
    }

    @Test
    public void write() throws Exception {
        AbstractSection sectionOld = new TextSection("Objective1");
        String json = JsonParser.write(sectionOld, AbstractSection.class);
        System.out.println(json);
        AbstractSection sectionNew = JsonParser.read(json, AbstractSection.class);
        Assert.assertEquals(sectionOld, sectionNew);
    }
}
