package mx.com.encargalo.tendero.Entidad;

public class rt_EntidadRubrosTienda {
    private int idRubroTienda;
    private int idTienda;
    private String rtDescripcion;

    public rt_EntidadRubrosTienda() {
    }

    public rt_EntidadRubrosTienda(int idRubroTienda, int idTienda, String rtDescripcion) {
        this.idRubroTienda = idRubroTienda;
        this.idTienda = idTienda;
        this.rtDescripcion = rtDescripcion;
    }

    public String getRtDescripcion() {
        return rtDescripcion;
    }

    public void setRtDescripcion(String rtDescripcion) {
        this.rtDescripcion = rtDescripcion;
    }

    public int getIdRubroTienda() {
        return idRubroTienda;
    }

    public void setIdRubroTienda(int idRubroTienda) {
        this.idRubroTienda = idRubroTienda;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    @Override
    public String toString() {
        return "sp_EntidadRubrosTienda{" +
                "idRubroTienda=" + idRubroTienda +
                ", idTienda=" + idTienda +
                ", rtDescripcion='" + rtDescripcion + '\'' +
                '}';
    }
}//+}@OHC04.11.2022
