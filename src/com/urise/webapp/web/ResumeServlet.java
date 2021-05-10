package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.*;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume resume;
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
                        resume.setSection(type, new ListSection(value.split("\\n")));
                        break;
                }
            }
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
            case EDIT:
                resume = storage.get(paramUuid);
                break;
            default:
                throw new IllegalArgumentException("Illegal action = " + paramAction);
        }
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(Action.VIEW.equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
                .forward(request, response);
    }
}
