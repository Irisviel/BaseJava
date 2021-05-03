package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.get().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String name = request.getParameter("name");
        Writer writer = response.getWriter();
        writer.write("" +
                "<!doctype html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=\"utf-8\">\n" +
                "  <title>Список всех резюме</title>\n" +
                "  <meta name=\"description\" content=\"Список всех резюме\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<table border=\"1\" cellpadding=\"8\" cellspacing=\"0\">\n"
        );
        for (Resume resume : storage.getAllSorted()) {
            writer.write("" +
                    "<tr>\n" +
                    "<td>" + resume.getUuid() + "</td>\n" +
                    "<td>" + resume.getFullName() + "</td>\n" +
                    "</tr>\n"
            );
        }
        writer.write("" +
                "</table>\n" +
                "</body>\n" +
                "</html>"
        );
    }
}
