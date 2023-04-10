package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades;

public class Favorito {
    String ap_strtitulo;
    String ap_strlink;
    String ap_strestado;
    String ap_strid;

    public Favorito() {
    }

    public Favorito(String ap_strtitulo, String ap_strlink, String ap_strestado, String ap_strid) {
        this.ap_strtitulo = ap_strtitulo;
        this.ap_strlink = ap_strlink;
        this.ap_strestado = ap_strestado;
        this.ap_strid = ap_strid;
    }

    public String getAp_strtitulo() {
        return ap_strtitulo;
    }

    public void setAp_strtitulo(String ap_strtitulo) {
        this.ap_strtitulo = ap_strtitulo;
    }

    public String getAp_strlink() {
        return ap_strlink;
    }

    public void setAp_strlink(String ap_strlink) {
        this.ap_strlink = ap_strlink;
    }

    public String getAp_strestado() {
        return ap_strestado;
    }

    public void setAp_strestado(String ap_strestado) {
        this.ap_strestado = ap_strestado;
    }

    public String getAp_strid() {
        return ap_strid;
    }

    public void setAp_strid(String ap_strid) {
        this.ap_strid = ap_strid;
    }
}
