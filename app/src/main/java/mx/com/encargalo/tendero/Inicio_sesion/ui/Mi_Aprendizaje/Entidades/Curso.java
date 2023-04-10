package mx.com.encargalo.tendero.Inicio_sesion.ui.Mi_Aprendizaje.Entidades;

public class Curso {
    private String ap_varstrid;
    private String ap_varstrnombrecurso;
    private String ap_varstrlinkcurso;
    private String ap_varstrlike;

    public Curso() {
    }

    public Curso(String ap_varstrid, String ap_varstrnombrecurso, String ap_varstrlinkcurso, String ap_varstrlike) {
        this.ap_varstrid = ap_varstrid;
        this.ap_varstrnombrecurso = ap_varstrnombrecurso;
        this.ap_varstrlinkcurso = ap_varstrlinkcurso;
        this.ap_varstrlike = ap_varstrlike;
    }

    public String getAp_varstrid() {
        return ap_varstrid;
    }

    public void setAp_varstrid(String ap_varstrid) {
        this.ap_varstrid = ap_varstrid;
    }

    public String getAp_varstrnombrecurso() {
        return ap_varstrnombrecurso;
    }

    public void setAp_varstrnombrecurso(String ap_varstrnombrecurso) {
        this.ap_varstrnombrecurso = ap_varstrnombrecurso;
    }

    public String getAp_varstrlinkcurso() {
        return ap_varstrlinkcurso;
    }

    public void setAp_varstrlinkcurso(String ap_varstrlinkcurso) {
        this.ap_varstrlinkcurso = ap_varstrlinkcurso;
    }

    public String getAp_varstrlike() {
        return ap_varstrlike;
    }

    public void setAp_varstrlike(String ap_varstrlike) {
        this.ap_varstrlike = ap_varstrlike;
    }
}
