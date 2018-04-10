package Controlador;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import modelo.UsuarioDAO;

/**
 *
 * @author gerardo
 */
@ManagedBean
@SessionScoped
public class Login {
    
    private String correo;
    private String contrasena;
    public static boolean login = false;
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    public String login() {
        Login.login = true;
        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = dao.busca(correo);
        FacesContext context = FacesContext.getCurrentInstance();

        if (u == null) {
            context.addMessage(null, new FacesMessage("El correo que ingres� no est� registrado, intente de nuevo"));
            correo = null;
            contrasena = null;
            return null;
        } else if(u.getContrasena().equals(this.contrasena)){
            context.getExternalContext().getSessionMap().put("user", u);
            
            return "InicioIH?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage("Contrase�a incorrecta"));
            correo = null;
            contrasena = null;
            return null; 
        }
    }

    public String logout() {
        Login.login = false;
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }
    
    public String regreso() {
        return "index";
    }
}