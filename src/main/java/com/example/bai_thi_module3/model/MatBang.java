package com.example.bai_thi_module3.model;

import java.time.LocalDate;

public class MatBang {
    private String maMb;
    private int dienTich;
    private String trangThai;
    private int tang;
    private String loai;
    private String moTa;
    private long giaTien;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;


    public MatBang() {
    }


    public MatBang(String maMb, int dienTich, String trangThai, int tang, String loai,
                   String moTa, long giaTien, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        this.maMb = maMb;
        this.dienTich = dienTich;
        this.trangThai = trangThai;
        this.tang = tang;
        this.loai = loai;
        this.moTa = moTa;
        this.giaTien = giaTien;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }


    // getters & setters
    public String getMaMb() {
        return maMb;
    }

    public void setMaMb(String maMb) {
        this.maMb = maMb;
    }

    public int getDienTich() {
        return dienTich;
    }

    public void setDienTich(int dienTich) {
        this.dienTich = dienTich;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public long getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(long giaTien) {
        this.giaTien = giaTien;
    }

    public LocalDate getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(LocalDate ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public LocalDate getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(LocalDate ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }
}
