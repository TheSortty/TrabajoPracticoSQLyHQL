package org.example;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nombre_basedatos";
        String user = "usuario";
        String password = "contraseña";
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
            PreparedStatement stmtCompu = conn.prepareStatement(sqlComputadora, Statement.RETURN_GENERATED_KEYS);
            stmtCompu.setString(1, codigo);
            stmtCompu.setString(2, marca);
            stmtCompu.setString(3, modelo);
            stmtCompu.executeUpdate();

            // Obtener el ID generado de la computadora
            ResultSet generatedKeys = stmtCompu.getGeneratedKeys();
            long computadoraId = 0;
            if (generatedKeys.next()) {
                computadoraId = generatedKeys.getLong(1);
            }

            // Solicitar componentes asociados
            System.out.println("¿Cuántos componentes desea agregar?");
            int numComponentes = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            for (int i = 0; i < numComponentes; i++) {
                System.out.println("Ingrese el nombre del componente: ");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el número de serie del componente: ");
                String nroSerie = scanner.nextLine();

                // Insertar componente
                String sqlComponente = "INSERT INTO Componente (Nombre, nroSerie, idComputadora) VALUES (?, ?, ?)";
                PreparedStatement stmtComp = conn.prepareStatement(sqlComponente);
                stmtComp.setString(1, nombre);
                stmtComp.setString(2, nroSerie);
                stmtComp.setLong(3, computadoraId);
                stmtComp.executeUpdate();
            }

            // Si todo va bien, confirmar transacción
            conn.commit();
            System.out.println("Datos almacenados correctamente.");
        } catch (SQLException e) {
            // Si hay error, hacer rollback
            try {
                if (conn != null) {
                    conn.rollback();
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
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
