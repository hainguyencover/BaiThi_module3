package com.example.bai_thi_module3.servlet;

import com.example.bai_thi_module3.dao.MatBangDao;
import com.example.bai_thi_module3.dao.MatBangJdbcDao;
import com.example.bai_thi_module3.model.MatBang;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


@WebServlet(name = "MatBangAddServlet", urlPatterns = "/matbang/add")
public class MatBangAddServlet extends HttpServlet {
    private final MatBangDao dao = new MatBangJdbcDao();
    private static final DateTimeFormatter ISO_DTF = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final String CODE_REGEX = "^[A-Z0-9]{3}-[A-Z0-9]{2}-[A-Z0-9]{2}$";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/matbang-add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Map<String, String> errors = new LinkedHashMap<>();

        // Lấy input
        String ma = n(req.getParameter("ma"));
        String trangThai = n(req.getParameter("trangThai"));
        String loai = n(req.getParameter("loai"));
        String moTa = n(req.getParameter("moTa"));

        Integer dienTich = parseInt(req.getParameter("dienTich"), "dienTich", errors);
        Integer tang = parseInt(req.getParameter("tang"), "tang", errors);
        Long giaTien = parseLong(req.getParameter("giaTien"), "giaTien", errors);
        LocalDate start = parseDate(req.getParameter("ngayBatDau"), "ngayBatDau", errors);
        LocalDate end = parseDate(req.getParameter("ngayKetThuc"), "ngayKetThuc", errors);

        // Validate nghiệp vụ
        if (ma == null || !ma.matches(CODE_REGEX)) {
            errors.put("ma", "Mã mặt bằng phải theo định dạng XXX-XX-XX (chữ in hoa/số)");
        }
        if (dienTich == null || dienTich <= 20) {
            errors.put("dienTich", "Diện tích phải > 20");
        }
        if (tang == null || tang < 1 || tang > 15) {
            errors.put("tang", "Tầng phải từ 1 đến 15");
        }
        if (giaTien == null || giaTien <= 1_000_000L) {
            errors.put("giaTien", "Giá tiền phải > 1.000.000 VND");
        }
        if (start == null) {
            errors.put("ngayBatDau", "Ngày bắt đầu không hợp lệ (yyyy-MM-dd)");
        }
        if (end == null) {
            errors.put("ngayKetThuc", "Ngày kết thúc không hợp lệ (yyyy-MM-dd)");
        }

        if (start != null && end != null) {
            LocalDate minEnd = start.plusMonths(6);
            if (!end.isAfter(minEnd.minusDays(1))) {
                errors.put("ngayKetThuc", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 6 tháng");
            }
        }

        // Check mã trùng
        if (errors.isEmpty() && dao.findById(ma).isPresent()) {
            errors.put("ma", "Mã mặt bằng đã tồn tại");
        }

        // Nếu có lỗi → trả lại form
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/WEB-INF/views/matbang-add.jsp").forward(req, resp);
            return;
        }

        // Insert DB
        MatBang m = new MatBang(ma, dienTich, trangThai, tang, loai, moTa, giaTien, start, end);
        dao.insert(m);
        resp.sendRedirect(req.getContextPath() + "/matbang");
    }

    // ---- Helper ----
    private static String n(String s) {
        return (s == null) ? null : s.trim();
    }

    private static Integer parseInt(String s, String field, Map<String, String> e) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : Integer.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            e.put(field, "Giá trị không hợp lệ");
            return null;
        }
    }

    private static Long parseLong(String s, String field, Map<String, String> e) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : Long.valueOf(s.trim());
        } catch (NumberFormatException ex) {
            e.put(field, "Giá trị không hợp lệ");
            return null;
        }
    }

    private static LocalDate parseDate(String s, String field, Map<String, String> e) {
        try {
            return (s == null || s.trim().isEmpty()) ? null : LocalDate.parse(s.trim(), ISO_DTF);
        } catch (DateTimeParseException ex) {
            e.put(field, "Ngày không hợp lệ (yyyy-MM-dd)");
            return null;
        }
    }
}