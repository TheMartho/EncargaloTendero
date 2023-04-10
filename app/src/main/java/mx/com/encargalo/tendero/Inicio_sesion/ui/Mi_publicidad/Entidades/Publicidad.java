package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_publicidad.Entidades;

public class Publicidad {
    private String pub_strtitulo;
    private String pub_strfechainicio;
    private String pub_strduracion;
    private String pub_strvistas;
    private String pub_strconversion;
    private String pub_strimagenurl;

    private String pub_strdescripcion;
    private String pub_strmonto;

    public Publicidad() {}

    public Publicidad(String pub_strtitulo, String pub_strfecha, String pub_strduracion, String pub_strvistas, String pub_strconversion, String pub_intimagenid, String pub_strdescripcion, String pub_strmonto) {
        this.pub_strtitulo = pub_strtitulo;
        this.pub_strfechainicio = pub_strfecha;
        this.pub_strduracion = pub_strduracion;
        this.pub_strvistas = pub_strvistas;
        this.pub_strconversion = pub_strconversion;
        this.pub_strimagenurl = pub_intimagenid;
        this.pub_strdescripcion = pub_strdescripcion;
        this.pub_strmonto = pub_strmonto;
    }

    public String getPub_strtitulo() {
        return pub_strtitulo;
    }

    public void setPub_strtitulo(String pub_strtitulo) {
        this.pub_strtitulo = pub_strtitulo;
    }

    public String getPub_strfechainicio() {
        return pub_strfechainicio;
    }

    public void setPub_strfechainicio(String pub_strfechainicio) {
        this.pub_strfechainicio = pub_strfechainicio;
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

    public String getPub_strimagenurl() {
        return pub_strimagenurl;
    }

    public void setPub_strimagenurl(String pub_strimagenurl) {
        this.pub_strimagenurl = pub_strimagenurl;
    }

    public String getPub_strdescripcion() {
        return pub_strdescripcion;
    }

    public void setPub_strdescripcion(String pub_strdescripcion) {
        this.pub_strdescripcion = pub_strdescripcion;
    }

    public String getPub_strmonto() {
        return pub_strmonto;
    }

    public void setPub_strmonto(String pub_strmonto) {
        this.pub_strmonto = pub_strmonto;
    }
}


