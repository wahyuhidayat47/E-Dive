package com.example.e_dive;

public class My_ticket {

    String nama_order, total_harga, jumlah_order, url_photo, id_ticket;



    public My_ticket() {
    }

    public My_ticket(String nama_order, String total_harga, String jumlah_order, String url_photo, String id_ticket) {
        this.nama_order = nama_order;
        this.total_harga = total_harga;
        this.jumlah_order = jumlah_order;
        this.url_photo = url_photo;
        this.id_ticket = id_ticket;
    }

    public String getNama_order() {
        return nama_order;
    }

    public void setNama_order(String nama_order) {
        this.nama_order = nama_order;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public String getJumlah_order() {
        return jumlah_order;
    }

    public void setJumlah_order(String jumlah_order) {
        this.jumlah_order = jumlah_order;
    }

    public String getUrl_photo() {
        return url_photo;
    }

    public void setUrl_photo(String url_photo) {
        this.url_photo = url_photo;
    }

    public String getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(String id_ticket) {
        this.id_ticket = id_ticket;
    }
}
