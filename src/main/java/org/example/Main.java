package org.example;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Cambia estos valores con tu información de la base de datos.
        String url = "jdbc:mysql://localhost:3306/pruebas";
        String user = "root";
        String password = "";
        Connection conn = null;

        try {
            // Conectar a la base de datos
            conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false); // Iniciar transacción

            // Solicitar datos de la computadora
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el código de la computadora: ");
            String codigo = scanner.nextLine();
            System.out.println("Ingrese la marca de la computadora: ");
            String marca = scanner.nextLine();
            System.out.println("Ingrese el modelo de la computadora: ");
            String modelo = scanner.nextLine();

            // Insertar la computadora
            String sqlComputadora = "INSERT INTO Computadora (Codigo, Marca, Modelo) VALUES (?, ?, ?)";
            try (PreparedStatement stmtCompu = conn.prepareStatement(sqlComputadora, Statement.RETURN_GENERATED_KEYS)) {
                stmtCompu.setString(1, codigo);
                stmtCompu.setString(2, marca);
                stmtCompu.setString(3, modelo);
                stmtCompu.executeUpdate();

                // Obtener el ID generado de la computadora
                try (ResultSet generatedKeys = stmtCompu.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        long computadoraId = generatedKeys.getLong(1);

                        // Solicitar componentes asociados
                        System.out.println("¿Cuántos componentes desea agregar?");
                        int numComponentes = scanner.nextInt();
                        scanner.nextLine(); // Limpiar buffer

                        // Preparar la sentencia para componentes
                        String sqlComponente = "INSERT INTO Componente (Nombre, nroSerie, idComputadora) VALUES (?, ?, ?)";
                        try (PreparedStatement stmtComp = conn.prepareStatement(sqlComponente)) {
                            for (int i = 0; i < numComponentes; i++) {
                                System.out.println("Ingrese el nombre del componente: ");
                                String nombre = scanner.nextLine();
                                System.out.println("Ingrese el número de serie del componente: ");
                                String nroSerie = scanner.nextLine();

                                // Insertar componente
                                stmtComp.setString(1, nombre);
                                stmtComp.setString(2, nroSerie);
                                stmtComp.setLong(3, computadoraId);
                                stmtComp.executeUpdate();
                            }
                        }
                    }
                }
            }

            // Confirmar la transacción
            conn.commit();
            System.out.println("Datos almacenados correctamente.");
        } catch (SQLException e) {
            // Si hay error, hacer rollback
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("Transacción revertida debido a un error.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Cerrar conexión
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("Conexión cerrada.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
