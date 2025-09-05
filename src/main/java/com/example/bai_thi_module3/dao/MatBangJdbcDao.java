package com.example.bai_thi_module3.dao;

import com.example.bai_thi_module3.model.MatBang;
import com.example.bai_thi_module3.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatBangJdbcDao implements MatBangDao {

    private MatBang map(ResultSet rs) throws SQLException {
        MatBang m = new MatBang();
        m.setMaMb(rs.getString("ma_mb"));
        m.setDienTich(rs.getInt("dien_tich"));
        m.setTrangThai(rs.getString("trang_thai"));
        m.setTang(rs.getInt("tang"));
        m.setLoai(rs.getString("loai"));
        m.setMoTa(rs.getString("mo_ta"));
        m.setGiaTien(rs.getLong("gia_tien"));

        Date start = rs.getDate("ngay_bat_dau");
        Date end = rs.getDate("ngay_ket_thuc");
        m.setNgayBatDau(start != null ? start.toLocalDate() : null);
        m.setNgayKetThuc(end != null ? end.toLocalDate() : null);

        return m;
    }

    @Override
    public List<MatBang> findAllSortedByArea() {
        String sql = "SELECT * FROM mat_bang ORDER BY dien_tich ASC";
        List<MatBang> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(map(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<MatBang> search(String loai, Integer tang, Long giaMin, Long giaMax,
                                LocalDate from, LocalDate to) {
        StringBuilder sb = new StringBuilder("SELECT * FROM mat_bang WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (loai != null && !loai.trim().isEmpty()) {
            sb.append(" AND loai = ?");
            params.add(loai);
        }
        if (tang != null) {
            sb.append(" AND tang = ?");
            params.add(tang);
        }
        if (giaMin != null) {
            sb.append(" AND gia_tien >= ?");
            params.add(giaMin);
        }
        if (giaMax != null) {
            sb.append(" AND gia_tien <= ?");
            params.add(giaMax);
        }
        if (from != null) {
            sb.append(" AND ngay_bat_dau >= ?");
            params.add(Date.valueOf(from));
        }
        if (to != null) {
            sb.append(" AND ngay_ket_thuc <= ?");
            params.add(Date.valueOf(to));
        }

        sb.append(" ORDER BY dien_tich ASC");

        List<MatBang> list = new ArrayList<>();
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sb.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                if (p instanceof Integer) ps.setInt(i + 1, (Integer) p);
                else if (p instanceof Long) ps.setLong(i + 1, (Long) p);
                else if (p instanceof Date) ps.setDate(i + 1, (Date) p);
                else ps.setObject(i + 1, p);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Optional<MatBang> findById(String maMb) {
        String sql = "SELECT * FROM mat_bang WHERE ma_mb = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maMb);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return Optional.of(map(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public boolean insert(MatBang m) {
        String sql = "INSERT INTO mat_bang(ma_mb, dien_tich, trang_thai, tang, loai, mo_ta, gia_tien, ngay_bat_dau, ngay_ket_thuc) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, m.getMaMb());
            ps.setInt(2, m.getDienTich());
            ps.setString(3, m.getTrangThai());
            ps.setInt(4, m.getTang());
            ps.setString(5, m.getLoai());
            ps.setString(6, m.getMoTa());
            ps.setLong(7, m.getGiaTien());
            ps.setDate(8, m.getNgayBatDau() != null ? Date.valueOf(m.getNgayBatDau()) : null);
            ps.setDate(9, m.getNgayKetThuc() != null ? Date.valueOf(m.getNgayKetThuc()) : null);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(String maMb) {
        String sql = "DELETE FROM mat_bang WHERE ma_mb = ?";
        try (Connection c = DBUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, maMb);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}