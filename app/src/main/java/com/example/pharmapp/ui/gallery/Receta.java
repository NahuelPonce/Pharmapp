package com.example.pharmapp.ui.gallery;

public class Receta {
    private int recetaID;
    private byte [] imagen;

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public int getRecetaID() {
        return recetaID;
    }

    public void setRecetaID(int recetaID) {
        this.recetaID = recetaID;
    }


}
