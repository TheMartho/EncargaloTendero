package mx.com.encargalo.tendero.Inicio_sesion.Entidad;

public class Publicidad {
    private String pub_strtitulo;
    private String pub_strfecha;
    private String pub_strduracion;
    private String pub_strvistas;
    private String pub_strconversion;
    private String pub_intimagenid;

    public Publicidad() {}

    public Publicidad(String titulo, String fecha, String duracion, String vistas, String conversion, String imagenid) {
        this.pub_strtitulo = titulo;
        this.pub_strfecha = fecha;
        this.pub_strduracion = duracion;
        this.pub_strvistas = vistas;
        this.pub_strconversion = conversion;
        this.pub_intimagenid = imagenid;
    }

    public String getPub_strtitulo() {
        return pub_strtitulo;
    }

    public void setPub_strtitulo(String pub_strtitulo) {
        this.pub_strtitulo = pub_strtitulo;
    }

    public String getPub_strfecha() {
        return pub_strfecha;
    }

    public void setPub_strfecha(String pub_strfecha) {
        this.pub_strfecha = pub_strfecha;
    }

    public String getPub_strduracion() {
        return pub_strduracion;
    }

    public void setPub_strduracion(String pub_strduracion) {
        this.pub_strduracion = pub_strduracion;
    }

    public String getPub_strvistas() {
        return pub_strvistas;
    }

    public void setPub_strvistas(String pub_strvistas) {
        this.pub_strvistas = pub_strvistas;
    }

    public String getPub_strconversion() {
        return pub_strconversion;
    }

    public void setPub_strconversion(String pub_strconversion) {
        this.pub_strconversion = pub_strconversion;
    }

    public String getPub_intimagenid() {
        return pub_intimagenid;
    }

    public void setPub_intimagenid(String pub_intimagenid) {
        this.pub_intimagenid = pub_intimagenid;
    }
}


