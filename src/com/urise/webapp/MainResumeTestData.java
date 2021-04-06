package com.urise.webapp;

import com.urise.webapp.model.*;

import java.util.Map;

import static com.urise.webapp.storage.ResumeTestData.newResume;

public class MainResumeTestData {

    public static void main(String[] args) {
        Resume resume = newResume("UUID", "ResumeName");

        printAll(resume);
    }

    static void printAll(Resume resume) {
        System.out.println(resume);
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": " + contact.getValue());
        }
        for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle() + ": " + section.getValue());
        }
    }
}
