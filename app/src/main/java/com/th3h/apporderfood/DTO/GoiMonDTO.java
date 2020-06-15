package com.th3h.apporderfood.DTO;

public class GoiMonDTO {
    private int maGoiMon;
    private  int maBan;
    private int maNhanVien;
    private String tinhTrang;
    private String ngayGoi;

    public int getMaGoiMon() {
        return maGoiMon;
    }

    public void setMaGoiMon(int maGoiMon) {
        this.maGoiMon = maGoiMon;
    }

    public int getMaBan() {
        return maBan;
    }

    public void setMaBan(int maMonAn) {
        this.maBan = maMonAn;
    }

    public int getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(int maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayGoi() {
        return ngayGoi;
    }

    public void setNgayGoi(String ngayGoi) {
        this.ngayGoi = ngayGoi;
    }
}
