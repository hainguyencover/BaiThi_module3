package com.example.bai_thi_module3.servlet;


import com.example.bai_thi_module3.dao.MatBangDao;
import com.example.bai_thi_module3.dao.MatBangJdbcDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;


@WebServlet(name = "MatBangSearchServlet", urlPatterns = "/matbang/search")
public class MatBangSearchServlet extends HttpServlet {
    private final MatBangDao dao = new MatBangJdbcDao();

    // Lưu ý: <input type="date"> luôn gửi giá trị yyyy-MM-dd
    private static final DateTimeFormatter ISO_DTF = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String loai = emptyToNull(req.getParameter("loai"));
        Integer tang = parseIntOrNull(req.getParameter("tang"));
        Long giaMin = parseLongOrNull(req.getParameter("giaMin"));
        Long giaMax = parseLongOrNull(req.getParameter("giaMax"));
        LocalDate from = parseDateOrNull(req.getParameter("from"));
        LocalDate to = parseDateOrNull(req.getParameter("to"));

        req.setAttribute("loai", loai);
        req.setAttribute("tang", tang);
        req.setAttribute("giaMin", giaMin);
        req.setAttribute("giaMax", giaMax);
        // Gọi DAO lấy danh sách đã search
        req.setAttribute("list", dao.search(loai, tang, giaMin, giaMax, from, to));
        req.getRequestDispatcher("/WEB-INF/views/matbang-list.jsp").forward(req, resp);
    }

    // ---- Helper methods ----
    private static String emptyToNull(String s) {
        return (s == null || s.trim().isEmpty()) ? null : s;
    }

    private static Integer parseIntOrNull(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    //    private static Long parseLongOrNull(String s) {
//        try {
//            return (s == null || s.trim().isEmpty()) ? null : Long.valueOf(s.trim());
//        } catch (NumberFormatException e) {
//            return null;
//        }
//    }
    private static Long parseLongOrNull(String s) {
        try {
            if (s == null || s.trim().isEmpty()) return null;
            // Bỏ dấu phân cách ngàn (phẩy hoặc chấm)
            s = s.replaceAll("[,\\.]", "").trim();
            return Long.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }

    private static LocalDate parseDateOrNull(String s) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : LocalDate.parse(s, ISO_DTF);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}