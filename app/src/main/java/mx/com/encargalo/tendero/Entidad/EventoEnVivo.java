package mx.com.encargalo.tendero.Inicio_sesion.Entidad;

public class EventoEnVivo {
    String ap_strtitulo;
    String ap_strcharla;
    String ap_strfecha;

    public EventoEnVivo() {
    }

    public EventoEnVivo(String ap_strtitulo, String ap_strcharla, String ap_strfecha) {
        this.ap_strtitulo = ap_strtitulo;
        this.ap_strcharla = ap_strcharla;
        this.ap_strfecha = ap_strfecha;
    }

    public String getAp_strtitulo() {
        return ap_strtitulo;
    }

    public void setAp_strtitulo(String ap_strtitulo) {
        this.ap_strtitulo = ap_strtitulo;
    }

    public String getAp_strcharla() {
        return ap_strcharla;
    }

    public void setAp_strcharla(String ap_strcharla) {
        this.ap_strcharla = ap_strcharla;
    }

    public String getAp_strfecha() {
        return ap_strfecha;
    }

    public void setAp_strfecha(String ap_strfecha) {
        this.ap_strfecha = ap_strfecha;
    }
}
