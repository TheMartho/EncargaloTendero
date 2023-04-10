package mx.com.encargalo.tendero.Entidad;

public class rt_EntidadTiendasPersona {
    private int idTienda;
    private String tieNombre;
    private String tieImagen;
    private String tieImagenurl;
    private String tieURLWeb;
    private String tieDescripcion;
    private String tieCorreo;
    private String tieTelefono;
    private String tieDireccion;
    private String tieCiudad;
    private String tieEstado;
    private int tieVentasMensuales;
    private float tieInventarioEstimado;
    private String tieLatitud;
    private String tieLongitud;
    private String idDocumentoPersona;

    public rt_EntidadTiendasPersona() {
    }

    public rt_EntidadTiendasPersona(int idTienda, String tieNombre, String tieImagen, String tieImagenurl, String tieURLWeb, String tieDescripcion, String tieCorreo, String tieTelefono, String tieDireccion, String tieCiudad, String tieEstado, int tieVentasMensuales, float tieInventarioEstimado, String tieLatitud, String tieLongitud, String idDocumentoPersona) {
        this.idTienda = idTienda;
        this.tieNombre = tieNombre;
        this.tieImagen = tieImagen;
        this.tieImagenurl = tieImagenurl;
        this.tieURLWeb = tieURLWeb;
        this.tieDescripcion = tieDescripcion;
        this.tieCorreo = tieCorreo;
        this.tieTelefono = tieTelefono;
        this.tieDireccion = tieDireccion;
        this.tieCiudad = tieCiudad;
        this.tieEstado = tieEstado;
        this.tieVentasMensuales = tieVentasMensuales;
        this.tieInventarioEstimado = tieInventarioEstimado;
        this.tieLatitud = tieLatitud;
        this.tieLongitud = tieLongitud;
        this.idDocumentoPersona = idDocumentoPersona;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getTieNombre() {
        return tieNombre;
    }

    public void setTieNombre(String tieNombre) {
        this.tieNombre = tieNombre;
    }

    public String getTieImagen() {
        return tieImagen;
    }

    public void setTieImagen(String tieImagen) {
        this.tieImagen = tieImagen;
    }

    public String getTieImagenurl() {
        return tieImagenurl;
    }

    public void setTieImagenurl(String tieImagenurl) {
        this.tieImagenurl = tieImagenurl;
    }

    public String getTieURLWeb() {
        return tieURLWeb;
    }

    public void setTieURLWeb(String tieURLWeb) {
        this.tieURLWeb = tieURLWeb;
    }

    public String getTieDescripcion() {
        return tieDescripcion;
    }

    public void setTieDescripcion(String tieDescripcion) {
        this.tieDescripcion = tieDescripcion;
    }

    public String getTieCorreo() {
        return tieCorreo;
    }

    public void setTieCorreo(String tieCorreo) {
        this.tieCorreo = tieCorreo;
    }

    public String getTieTelefono() {
        return tieTelefono;
    }

    public void setTieTelefono(String tieTelefono) {
        this.tieTelefono = tieTelefono;
    }

    public String getTieDireccion() {
        return tieDireccion;
    }

    public void setTieDireccion(String tieDireccion) {
        this.tieDireccion = tieDireccion;
    }

    public String getTieCiudad() {
        return tieCiudad;
    }

    public void setTieCiudad(String tieCiudad) {
        this.tieCiudad = tieCiudad;
    }

    public String getTieEstado() {
        return tieEstado;
    }

    public void setTieEstado(String tieEstado) {
        this.tieEstado = tieEstado;
    }

    public int getTieVentasMensuales() {
        return tieVentasMensuales;
    }

    public void setTieVentasMensuales(int tieVentasMensuales) {
        this.tieVentasMensuales = tieVentasMensuales;
    }

    public float getTieInventarioEstimado() {
        return tieInventarioEstimado;
    }

    public void setTieInventarioEstimado(float tieInventarioEstimado) {
        this.tieInventarioEstimado = tieInventarioEstimado;
    }

    public String getTieLatitud() {
        return tieLatitud;
    }

    public void setTieLatitud(String tieLatitud) {
        this.tieLatitud = tieLatitud;
    }

    public String getTieLongitud() {
        return tieLongitud;
    }

    public void setTieLongitud(String tieLongitud) {
        this.tieLongitud = tieLongitud;
    }

    public String getIdDocumentoPersona() {
        return idDocumentoPersona;
    }

    public void setIdDocumentoPersona(String idDocumentoPersona) {
        this.idDocumentoPersona = idDocumentoPersona;
    }

    @Override
    public String toString() {
        return "rt_EntidadTiendasPersona{" +
                "idTienda=" + idTienda +
                ", tieNombre='" + tieNombre + '\'' +
                ", tieImagen='" + tieImagen + '\'' +
                ", tieImagenurl='" + tieImagenurl + '\'' +
                ", tieURLWeb='" + tieURLWeb + '\'' +
                ", tieDescripcion='" + tieDescripcion + '\'' +
                ", tieCorreo='" + tieCorreo + '\'' +
                ", tieTelefono='" + tieTelefono + '\'' +
                ", tieDireccion='" + tieDireccion + '\'' +
                ", tieCiudad='" + tieCiudad + '\'' +
                ", tieEstado='" + tieEstado + '\'' +
                ", tieVentasMensuales=" + tieVentasMensuales +
                ", tieInventarioEstimado=" + tieInventarioEstimado +
                ", tieLatitud='" + tieLatitud + '\'' +
                ", tieLongitud='" + tieLongitud + '\'' +
                ", idDocumentoPersona='" + idDocumentoPersona + '\'' +
                '}';
    }
}
