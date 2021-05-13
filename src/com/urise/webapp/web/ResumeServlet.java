package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ValidationException;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.util.EnumUtil.getEnumFromString;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        final boolean isCreate;
        Resume resume;
        try {
            String uuid = request.getParameter("uuid");
            String fullName = request.getParameter("fullName");
            if (HtmlUtil.isEmpty(fullName)) {
                throw new ValidationException("Empty field \"fullName\"");
            }
            isCreate = (uuid == null || uuid.length() == 0);
            if (isCreate) {
                resume = new Resume(fullName);
            } else {
                resume = storage.get(uuid);
                resume.setFullName(fullName);
            }

            for (ContactType type : ContactType.values()) {
                String contactValue = request.getParameter(type.name());
                if (HtmlUtil.isEmpty(contactValue)) {
                    resume.getContacts().remove(type);
                } else {
                    resume.setContact(type, contactValue);
                }
            }

            for (SectionType type : SectionType.values()) {
                String value = request.getParameter(type.name());
                String[] values = request.getParameterValues(type.name());
                if (HtmlUtil.isEmpty(value) && (values == null || values.length < 2)) {
                    resume.getSections().remove(type);
                } else {
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            resume.setSection(type, new TextSection(value));
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            resume.setSection(type,
                                    new ListSection(
                                            Arrays.stream(value.split("\\n"))
                                                    .filter(x -> !HtmlUtil.isEmpty(x)).toArray(String[]::new)
                                    ));
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            List<Organization> organizations = new ArrayList<>();
                            String[] urls = request.getParameterValues(type.name() + "url");
                            for (int i = 0; i < values.length; i++) {
                                String name = values[i];
                                if (!HtmlUtil.isEmpty(name)) {
                                    List<Organization.Position> positions = new ArrayList<>();
                                    String prefix = type.name() + i;
                                    String[] titles = request.getParameterValues(prefix + "title");
                                    String[] descriptions = request.getParameterValues(prefix + "description");
                                    String[] startDates = request.getParameterValues(prefix + "startDate");
                                    String[] endDates = request.getParameterValues(prefix + "endDate");
                                    for (int j = 0; j < titles.length; j++) {
                                        if (!HtmlUtil.isEmpty(titles[j])) {
                                            positions.add(
                                                    new Organization.Position(
                                                            DateUtil.parse(startDates[j]),
                                                            DateUtil.parse(endDates[j]),
                                                            titles[j],
                                                            descriptions[j]
                                                    ));
                                        }
                                    }
                                    organizations.add(new Organization(new Link(name, urls[i]), new ArrayList<>()));
                                }
                            }
                            resume.setSection(type, new OrganizationSection(organizations));
                            break;
                    }
                }
            }
        } catch (ValidationException e) {
            response.sendError(400, "Invalid request data: " + e.getMessage());
            return;
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String paramUuid = request.getParameter("uuid");
        String paramAction = request.getParameter("action");
        Action action = getEnumFromString(Action.class, paramAction);
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case DELETE:
                storage.delete(paramUuid);
                response.sendRedirect("resume");
                return;
            case ADD:
                resume = Resume.EMPTY;
                break;
            case VIEW:
                resume = storage.get(paramUuid);
                break;
            case EDIT:
                resume = storage.get(paramUuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE:
                        case PERSONAL:
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                            break;
                        case EXPERIENCE:
                        case EDUCATION:
                            OrganizationSection orgSection = (OrganizationSection) section;
                            List<Organization> emptyFirstOrganizations = new ArrayList<>();
                            emptyFirstOrganizations.add(Organization.EMPTY);
                            if (orgSection != null) {
                                for (Organization org : orgSection.getOrganizations()) {
                                    List<Organization.Position> emptyFirstPositions = new ArrayList<>();
                                    emptyFirstPositions.add(Organization.Position.EMPTY);
                                    emptyFirstPositions.addAll(org.getPositions());
                                    emptyFirstOrganizations.add(new Organization(org.getHomePage(), emptyFirstPositions));
                                }
                            }
                            section = new OrganizationSection(emptyFirstOrganizations);
                            break;
                    }
                    resume.setSection(type, section);
                }
                break;
            default:
                throw new IllegalArgumentException("Illegal action = " + paramAction);
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(Action.VIEW.equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
