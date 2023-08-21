package com.mycompany.peluqueriamirey;

import java.util.List;
import javax.swing.JOptionPane;

public class Facturacion {
    // comentario

    private Peluqueria peluqueria;

    public Facturacion(Peluqueria peluqueria) {
        this.peluqueria = peluqueria;
    }
//adios
    public void generarFactura() {
        // Solicitar número de reserva al cliente
        String numeroReserva = JOptionPane.showInputDialog(null, "Ingrese el número de reserva:");

        // Obtener lista de reservas
        List<Reserva> reservasList = peluqueria.getReservasList();

        // Buscar la reserva correspondiente al número ingresado
        Reserva reserva = null;
        for (Reserva r : reservasList) {
            if (r.getNumeroReserva().equalsIgnoreCase(numeroReserva)) {
                reserva = r;
                break;
            }
        }

        // Si no se encuentra la reserva, mostrar mensaje de error y salir
        if (reserva == null) {
            JOptionPane.showMessageDialog(null, "La reserva con el número proporcionado no existe.");
            return;
        }

        // Obtener información para la factura
        String nombreCliente = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente:");
        String numeroIdentificacion = JOptionPane.showInputDialog(null, "Ingrese el número de identificación del cliente:");

        // Calcular monto total de la factura
        int montoTotal = calcularMontoTotal(reserva.getCondicion(), reserva.getCantidadPersonas());

        // Mostrar la factura
        mostrarFactura(nombreCliente, numeroIdentificacion, reserva, montoTotal);
    }

    private int calcularMontoTotal(String condicion, int cantidadPersonas) {
        int precioCorte = (condicion.equalsIgnoreCase("Niño")) ? 5000 : 8000;
        return precioCorte * cantidadPersonas;
    }

    private void mostrarFactura(String nombreCliente, String numeroIdentificacion, Reserva reserva, int montoTotal) {
        StringBuilder factura = new StringBuilder();
        factura.append("=== Factura ===\n\n");
        factura.append("Datos de la empresa:\n");
        factura.append("Nombre: Peluquería mi Rey\n");
        factura.append("Dirección: Calle 123, Ciudad\n");
        factura.append("Teléfono: 555-123456\n\n");
        factura.append("Datos del cliente:\n");
        factura.append("Nombre: ").append(nombreCliente).append("\n");
        factura.append("Número de identificación: ").append(numeroIdentificacion).append("\n\n");
        factura.append("Datos de la reserva:\n");
        factura.append("Número de reserva: ").append(reserva.getNumeroReserva()).append("\n");
        factura.append("Cantidad de personas: ").append(reserva.getCantidadPersonas()).append("\n");
        factura.append("Condición: ").append(reserva.getCondicion()).append("\n");
        factura.append("Horario: ").append(reserva.getHoraReserva()).append(":00\n");
        factura.append("Monto a pagar por persona: ");
        factura.append((reserva.getCondicion().equalsIgnoreCase("Niño")) ? "5000\n" : "8000\n");
        factura.append("Monto total de la factura: ").append(montoTotal).append("\n");

        JOptionPane.showMessageDialog(null, factura.toString());
    }
}