package com.urise.webapp.model;

public enum SectionType {
    // Text sections
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    // List sections
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    // Organization sections
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}