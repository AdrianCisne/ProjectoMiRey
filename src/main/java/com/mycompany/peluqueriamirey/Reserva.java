package com.mycompany.peluqueriamirey;

public class Reserva {
    private final String condicion;
    private final int horaReserva;
    private final String nombrePeluquero;
    private final String numeroReserva;

    public Reserva(String condicion, int horaReserva, String nombrePeluquero, String numeroReserva) {
        this.condicion = condicion;
        this.horaReserva = horaReserva;
        this.nombrePeluquero = nombrePeluquero;
        this.numeroReserva = numeroReserva;
    }

    public String getCondicion() {
        return condicion;
    }

    public int getHoraReserva() {
        return horaReserva;
    }

    public String getNombrePeluquero() {
        return nombrePeluquero;
    }

    public String getNumeroReserva() {
        return numeroReserva;
    }

    int getCantidadPersonas() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}