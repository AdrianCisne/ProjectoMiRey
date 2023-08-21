package com.mycompany.peluqueriamirey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

public class Peluqueria {
    private Map<String, Usuario> usuarios;
    private Map<String, List<Reserva>> reservas;
    private int capacidadSimultanea;
    private int horaInicio;
    private int horaFin;
    private String usuarioAutenticado;

    public Peluqueria() {
        // comentario  pap
        usuarios = new HashMap<>();
        reservas = new HashMap<>();
        capacidadSimultanea = 3;
        horaInicio = 8;
        horaFin = 18;
        usuarioAutenticado = null;
        crearUsuarioAdmin();
    }
//no se programar hola
    private void crearUsuarioAdmin() {
        Usuario admin = new Usuario("admin", "admin123");
        usuarios.put(admin.getNombre(), admin);
    }

    public void ejecutar() {
        // Lógica principal del programa
        crearUsuarioAdmin(); // Crear un usuario administrador para acceder al módulo de reservas

        boolean salir = false;
        while (!salir) {
            String opcionStr = JOptionPane.showInputDialog(null, "=== Peluquería mi Rey ===\n"
                    + "1. Login\n"
                    + "2. Módulo de reservas\n"
                    + "3. Consultar reserva\n"
                    + "4. Salir\n"
                    + "Ingrese una opción:");

            if (opcionStr == null) {
                opcionStr = "4"; // Si se cierra el diálogo, asumimos opción 4 (salir)
            }

            int opcion = Integer.parseInt(opcionStr);

            switch (opcion) {
                case 1 -> login();
                case 2 -> {
                    if (usuarioAutenticado != null) {
                        moduloReservas();
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesión para acceder al módulo de reservas.");
                    }
                }
                case 3 -> {
                    if (usuarioAutenticado != null) {
                        consultarReserva();
                    } else {
                        JOptionPane.showMessageDialog(null, "Debe iniciar sesión para consultar una reserva.");
                    }
                }
                case 4 -> {
                    salir = true;
                    JOptionPane.showMessageDialog(null, "¡Hasta luego!");
                }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, intente nuevamente.");
            }
        }
    }

    private void login() {
        // Lógica del módulo de login
        String usuario = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog(null, "Ingrese su contraseña:");

        Usuario usuarioValido = usuarios.get(usuario);
        if (usuarioValido != null && usuarioValido.getContrasena().equals(contrasena)) {
            usuarioAutenticado = usuario;
            JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.");
        } else {
            usuarioAutenticado = null;
            JOptionPane.showMessageDialog(null, "Nombre de usuario o contraseña incorrectos.");
        }
    }

    private void moduloReservas() {
        // Lógica del módulo de reservas
        JOptionPane.showMessageDialog(null, "=== Módulo de Reservas ===");

        String cantidadPersonasStr = JOptionPane.showInputDialog(null, "Ingrese la cantidad de personas:");
        int cantidadPersonas = Integer.parseInt(cantidadPersonasStr);

        if (cantidadPersonas <= capacidadSimultanea) {
            List<Reserva> listaReservas = new ArrayList<>();

            for (int i = 0; i < cantidadPersonas; i++) {
                String condicion = JOptionPane.showInputDialog(null, "Ingrese la condición (Niño/Adulto Mayor) de la persona " + (i + 1) + ":");
                if (!condicion.equalsIgnoreCase("Niño") && !condicion.equalsIgnoreCase("Adulto Mayor")) {
                    JOptionPane.showMessageDialog(null, "La condición ingresada no es válida. Por favor, elija 'Niño' o 'Adulto Mayor'.");
                    return;
                }
                String horaReservaStr = JOptionPane.showInputDialog(null, "Ingrese el horario de reserva (formato 24 horas, ej: 10 para las 10:00):");
                int horaReserva = Integer.parseInt(horaReservaStr);

                if (horaReserva >= horaInicio && horaReserva < horaFin) {
                    String nombrePeluquero = obtenerPeluqueroDisponible(horaReserva);
                    if (nombrePeluquero != null) {
                        String numeroReserva = generarNumeroReserva();
                        Reserva reserva = new Reserva(condicion, horaReserva, nombrePeluquero, numeroReserva);
                        listaReservas.add(reserva);
                    } else {
                        JOptionPane.showMessageDialog(null, "No hay espacio en el horario escogido. Por favor, elija otro horario.");
                        return;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "El horario ingresado está fuera del horario laboral de la peluquería. Por favor, elija un horario válido.");
                    return;
                }
            }

            StringBuilder reservaInfo = new StringBuilder();
            reservaInfo.append("Reservación exitosa. Detalles de la reserva:\n");

            for (Reserva reserva : listaReservas) {
                reservaInfo.append("Condición: ").append(reserva.getCondicion()).append("\n");
                reservaInfo.append("Hora de reserva: ").append(reserva.getHoraReserva()).append(":00\n");
                reservaInfo.append("Peluquero asignado: ").append(reserva.getNombrePeluquero()).append("\n");
                reservaInfo.append("Número de reserva: ").append(reserva.getNumeroReserva()).append("\n");
                reservaInfo.append("----------------------------\n");
            }

            reservaInfo.append("¡Gracias por elegir Peluquería mi Rey!");

            JOptionPane.showMessageDialog(null, reservaInfo.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No hay suficiente espacio en el horario escogido para la cantidad de personas. Por favor, elija otro horario o reduzca el número de personas.");
        }
    }

    private void consultarReserva() {
        // Lógica del módulo de consulta de reserva
        String numeroReserva = JOptionPane.showInputDialog(null, "Ingrese el número de reserva:");
        List<Reserva> reservasList = new ArrayList<>();

        for (List<Reserva> listaReservas : reservas.values()) {
            reservasList.addAll(listaReservas);
        }

        for (Reserva reserva : reservasList) {
            if (reserva.getNumeroReserva().equalsIgnoreCase(numeroReserva)) {
                StringBuilder reservaInfo = new StringBuilder();
                reservaInfo.append("Detalles de la reserva:\n");
                reservaInfo.append("Número de reserva: ").append(reserva.getNumeroReserva()).append("\n");
                reservaInfo.append("Condición: ").append(reserva.getCondicion()).append("\n");
                reservaInfo.append("Hora de reserva: ").append(reserva.getHoraReserva()).append(":00\n");
                reservaInfo.append("Peluquero asignado: ").append(reserva.getNombrePeluquero()).append("\n");

                JOptionPane.showMessageDialog(null, reservaInfo.toString());
                return;
            }
        }

        JOptionPane.showMessageDialog(null, "La reserva con el número proporcionado no existe.");
    }

    private String obtenerPeluqueroDisponible(int horaReserva) {
        // Lógica para obtener un peluquero disponible en un horario específico
        if (horaReserva < horaInicio || horaReserva > horaFin) {
            return null;
        }

        for (int i = horaInicio; i <= horaFin; i++) {
            if (i == horaReserva) {
                List<Reserva> reservasHora = reservas.getOrDefault(String.valueOf(i), new ArrayList<>());
                if (reservasHora.size() < capacidadSimultanea) {
                    return "Peluquero " + (reservasHora.size() + 1);
                }
            }
        }
        return null;
    }

    private String generarNumeroReserva() {
        // Lógica para generar un número de reserva aleatorio
        String letras = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int numero = (int) (Math.random() * 10);
        char letra = letras.charAt((int) (Math.random() * letras.length()));
        return String.valueOf(letra) + numero;
    }

    List<Reserva> getReservasList() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private static class Usuario {

        public Usuario() {
        }

        private Usuario(String admin, String admin123) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private String getNombre() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        private Object getContrasena() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
    }
}