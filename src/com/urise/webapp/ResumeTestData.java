package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("ResumeName");

        resume.addContact(ContactType.PHONE, "1234567890");
        resume.addContact(ContactType.SKYPE, "SkypeName");
        resume.addContact(ContactType.GITHUB, "GithubLink");
        resume.addContact(ContactType.HOME_PAGE, "HomePage");
        resume.addContact(ContactType.LINKEDIN, "LinkedInLink");
        resume.addContact(ContactType.MAIL, "Mail");
        resume.addContact(ContactType.STACKOVERFLOW, "StackoverflowProfileLink");

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Employer."));
        resume.addSection(SectionType.PERSONAL, new TextSection("Personal qualities come here."));
        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Achievement1", "Achievement2")));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Qualification1", "Qualification2")));

        Organization organizationWork = new Organization(
                "OrganizationName",
                "OrganizationUrl",
                LocalDate.of(2005,01,01),
                LocalDate.of(2005,06,01),
                "OrganizationTitle",
                "OrganizationDescription");
        resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(organizationWork)));

        Organization organizationEdu = new Organization(
                "OrganizationEduName",
                "OrganizationEduUrl",
                LocalDate.of(2003,01,01),
                LocalDate.of(2004,12,01),
                "OrganizationEduTitle",
                "OrganizationEduDescription");
        resume.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(organizationEdu)));

        printAll(resume);
    }

    static void printAll(Resume resume) {
        System.out.println(resume);
        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle() + ": "+ contact.getValue());
        }
        for (Map.Entry<SectionType, Section> section: resume.getSections().entrySet()) {
            System.out.println(section.getKey().getTitle() + ": " + section.getValue());
        }
    }
}
