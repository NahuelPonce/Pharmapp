package com.example.pharmapp.ui.orden;

import android.icu.number.IntegerWidth;

import java.sql.Blob;
import java.util.Date;

public class OrdenBD {

    public OrdenBD(int idorden, int idpedido, String usuario, String estado, String fecha, String medicamentosid, String cantidades, Double preciototal) {
        this.idorden = idorden;
        this.idpedido = idpedido;
        this.usuario = usuario;
        this.estado = estado;
        this.fecha = fecha;
        this.medicamentosid = medicamentosid;
        this.cantidades = cantidades;
        this.preciototal = preciototal;

    }


    private int idorden;
    private int idpedido;
    private  String usuario;
    private  String estado;
    private  String fecha;
    private  String medicamentosid;
    private  String cantidades;
    private  Double preciototal;


    public int getIdorden() {
        return idorden;
    }

    public void setIdorden(int idorden) {
        this.idorden = idorden;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getMedicamentosid() {
        return medicamentosid;
    }

    public void setMedicamentosid(String medicamentosid) {
        this.medicamentosid = medicamentosid;
    }

    public String getCantidades() {
        return cantidades;
    }

    public void setCantidades(String cantidades) {
        this.cantidades = cantidades;
    }

    public Double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(Double preciototal) {
        this.preciototal = preciototal;
    }







}
