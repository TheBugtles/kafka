/*
 * The Bugtles, 2018.
 * -Aguilar Espíndola Ossiel.
 * -Barajas Tenorio Efraín.
 * -García Ramos Mauricio E.
 * -García Ruíz Paulo.
 * -Ledesma Granados Roberto A.
 */
package unam.ciencias.is.kafka.modelo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.Query;

/**
 *
 * @author ludus
 */
public class UsuarioDAO extends AbstractDAO<Usuario> {
    
    public boolean existeUsuarioConNombre(String nombre) {
        boolean existe = false;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction(); 
            String hql = "from Usuario as u where u.nombre = '" + nombre + "'";
            Query query = session.createQuery(hql);
            existe = query.uniqueResult() != null;
            transaccion.commit();
        }
        catch (Exception e) {

            if (transaccion != null) {
                transaccion.rollback();
            }
            
            e.printStackTrace(); 
        }
        finally {
            session.close();
        }
        
        return existe;
    }
    
    public boolean existeUsuarioConCorreo(String correo) {
        boolean existe = false;
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        
        try {
            transaccion = session.beginTransaction(); 
            String hql = "from Usuario as u where u.correo = '" + correo + "'";
            Query query = session.createQuery(hql);
            existe = query.uniqueResult() != null;
            transaccion.commit();
        }
        catch (Exception e) {

            if (transaccion != null) {
                transaccion.rollback();
            }
            
            e.printStackTrace(); 
        }
        finally {
            session.close();
        }
        
        return existe;
    }
    
    public Usuario selectPorNombre(String nombre) {
        Transaction transaccion = null;
        Session session = sessionFactory.openSession();
        Usuario usuario = null;
        
        try {
            transaccion = session.beginTransaction(); 
            String hql = "from Usuario as u where u.nombre = '" + nombre + "'";
            Query query = session.createQuery(hql);
            usuario = (Usuario) query.uniqueResult();
            transaccion.commit();
        }
        catch (Exception e) {

            if (transaccion != null) {
                transaccion.rollback();
            }
            
            e.printStackTrace(); 
        }
        finally {
            session.close();
        }
        
        return usuario;
    }
    
    public boolean activarCuenta(String nombre,String hash) {
        Usuario usuario = selectPorNombre(nombre);
        
        if (usuario != null && Integer.parseInt(hash) == usuario.hashCode()) {
            usuario.setEstado("ACTIVO");
            update(usuario);
            return true;
        }
        
        return false;
    }
    
}
