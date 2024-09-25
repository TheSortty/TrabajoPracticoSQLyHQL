package org.example;
import javax.persistence.*;
import java.util.*;

public class MainJPA {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("miUnidadPersistencia");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Capturar datos de la computadora
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ingrese el código de la computadora: ");
            String codigo = scanner.nextLine();
            System.out.println("Ingrese la marca de la computadora: ");
            String marca = scanner.nextLine();
            System.out.println("Ingrese el modelo de la computadora: ");
            String modelo = scanner.nextLine();

            Computadora computadora = new Computadora();
            computadora.setCodigo(codigo);
            computadora.setMarca(marca);
            computadora.setModelo(modelo);

            // Capturar componentes
            List<Componente> componentes = new ArrayList<>();
            System.out.println("¿Cuántos componentes desea agregar?");
            int numComponentes = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            for (int i = 0; i < numComponentes; i++) {
                System.out.println("Ingrese el nombre del componente: ");
                String nombre = scanner.nextLine();
                System.out.println("Ingrese el número de serie del componente: ");
                String nroSerie = scanner.nextLine();

                Componente componente = new Componente();
                componente.setNombre(nombre);
                componente.setNroSerie(nroSerie);
                componente.setComputadora(computadora);

                componentes.add(componente);
            }

            computadora.setComponentes(componentes);

            // Persistir datos
            em.persist(computadora);

            em.getTransaction().commit();
            System.out.println("Datos almacenados correctamente.");
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}
