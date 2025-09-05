package com.example.bai_thi_module3.servlet;

import com.example.bai_thi_module3.dao.MatBangDao;
import com.example.bai_thi_module3.dao.MatBangJdbcDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(name = "MatBangDeleteServlet", urlPatterns = "/matbang/delete")
public class MatBangDeleteServlet extends HttpServlet {
    private final MatBangDao dao = new MatBangJdbcDao();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ma = req.getParameter("ma");
        if (ma != null) dao.delete(ma);
        resp.sendRedirect(req.getContextPath() + "/matbang");
    }
}