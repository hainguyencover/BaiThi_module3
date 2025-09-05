package com.example.bai_thi_module3.servlet;

import com.example.bai_thi_module3.dao.MatBangDao;
import com.example.bai_thi_module3.dao.MatBangJdbcDao;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name="MatBangListServlet", urlPatterns = "/matbang")
public class MatBangListServlet extends HttpServlet {
    private final MatBangDao dao = new MatBangJdbcDao();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("list", dao.findAllSortedByArea());
        req.getRequestDispatcher("/WEB-INF/views/matbang-list.jsp").forward(req, resp);
    }
}