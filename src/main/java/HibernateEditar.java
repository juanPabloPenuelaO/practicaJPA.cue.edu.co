import jakarta.persistence.EntityManager;
import utils.JpaUtil;

import javax.swing.*;

public class HibernateEditar {
    public static void main(String[] args) {
        EntityManager em = JpaUtil.getEntityManager();
        try {

            String idStr = JOptionPane.showInputDialog("Ingrese el ID del cliente a editar:");
            Long id = Long.parseLong(idStr);

            Cliente c = em.find(Cliente.class, id);

            if (c != null) {

                String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", c.getNombre());
                String apellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:", c.getApellido());
                String pago = JOptionPane.showInputDialog("Ingrese la nueva forma de pago:", c.getFormaPago());

                em.getTransaction().begin();

                c.setNombre(nombre);
                c.setApellido(apellido);
                c.setFormaPago(pago);

                em.merge(c);
                em.getTransaction().commit();

                System.out.println("Cliente actualizado con éxito: " + c);
            } else {
                System.out.println("Cliente con ID " + id + " no encontrado.");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
