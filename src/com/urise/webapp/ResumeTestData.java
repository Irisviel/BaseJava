package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class ResumeTestData {

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

    public static Resume newResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        populateContacts(resume);
        populateTextSections(resume);
        populateListSections(resume);
        populateWorkSection(resume);
        populateEducationSections(resume);
        return resume;
    }

    public static void populateContacts(Resume resume) {
        resume.setContact(ContactType.PHONE, "1234567890");
        resume.setContact(ContactType.SKYPE, "SkypeName");
        resume.setContact(ContactType.GITHUB, "GithubLink");
        resume.setContact(ContactType.HOME_PAGE, "HomePage");
        resume.setContact(ContactType.LINKEDIN, "LinkedInLink");
        resume.setContact(ContactType.MAIL, "Mail");
        resume.setContact(ContactType.STACKOVERFLOW, "StackoverflowProfileLink");
    }

    public static void populateTextSections(Resume resume) {
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Employer."));
        resume.setSection(SectionType.PERSONAL, new TextSection("Personal qualities come here."));
    }

    public static void populateListSections(Resume resume) {
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Achievement1", "Achievement2")));
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Qualification1", "Qualification2")));
    }

    public static void populateWorkSection(Resume resume) {
        Organization organizationWork = new Organization(
                new Link("linkName", "linkUrl"),
                Arrays.asList(
                        new Organization.Position(2003, Month.JANUARY, 2004, Month.JULY, "WorkFirst", "Description1"),
                        new Organization.Position(2004, Month.SEPTEMBER, 2005, Month.DECEMBER, "WorkSecond", "Description2"),
                        new Organization.Position(2006, Month.JANUARY, 2007, Month.FEBRUARY, "WorkThird", null)
                ));
        Organization organizationWork2 = new Organization(
                new Link("linkName", null),
                Arrays.asList(
                        new Organization.Position(2007, Month.MARCH, 2008, Month.JUNE, "WorkFourth", "Description4")
                ));
        resume.setSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(organizationWork, organizationWork2)));
    }

    public static void populateEducationSections(Resume resume) {
        Organization organizationEdu = new Organization(
                new Link("linkEduName", "linkEduUrl"),
                Collections.singletonList(
                        new Organization.Position(2002, Month.JANUARY, 2002, Month.DECEMBER, "PositionTitle", "PositionDescription")
                ));
        resume.setSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(organizationEdu)));
    }
}
