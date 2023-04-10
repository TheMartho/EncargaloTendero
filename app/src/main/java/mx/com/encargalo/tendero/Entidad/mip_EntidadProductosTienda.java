package mx.com.encargalo.tendero.Entidad;

public class mip_EntidadProductosTienda {
    private int idListadoProductoTienda;
    private String lptDescripcionProductoTienda;
    private double lptStock;
    private String lptUnidadMedida;
    private double lptStockMinimo;
    private String lptImagen1;
    private String lptImagen2;
    private String lptImagen3;
    private double lptPrecioCompra;
    private double lptPrecioVenta;
    private String lpEstado;
    private int idProducto;
    private int idTienda;

    private String proDescripcion;
    private int idCategoriaProducto;
    private String cpNombre;

    private String proImagenurl;
    private String proImagen;
    private double proPrecioCostoPromedio;
    private double proPrecioVentaRecomendado;
    private String proUnidadMedida;

    public mip_EntidadProductosTienda() {
    }

    public mip_EntidadProductosTienda(int idListadoProductoTienda, String lptDescripcionProductoTienda, double lptStock, String lptUnidadMedida, double lptStockMinimo, String lptImagen1, String lptImagen2, String lptImagen3, double lptPrecioCompra, double lptPrecioVenta, String lpEstado, int idProducto, int idTienda, String proDescripcion, int idCategoriaProducto, String cpNombre, String proImagenurl, String proImagen, double proPrecioCostoPromedio, double proPrecioVentaRecomendado, String proUnidadMedida) {
        this.idListadoProductoTienda = idListadoProductoTienda;
        this.lptDescripcionProductoTienda = lptDescripcionProductoTienda;
        this.lptStock = lptStock;
        this.lptUnidadMedida = lptUnidadMedida;
        this.lptStockMinimo = lptStockMinimo;
        this.lptImagen1 = lptImagen1;
        this.lptImagen2 = lptImagen2;
        this.lptImagen3 = lptImagen3;
        this.lptPrecioCompra = lptPrecioCompra;
        this.lptPrecioVenta = lptPrecioVenta;
        this.lpEstado = lpEstado;
        this.idProducto = idProducto;
        this.idTienda = idTienda;
        this.proDescripcion = proDescripcion;
        this.idCategoriaProducto = idCategoriaProducto;
        this.cpNombre = cpNombre;
        this.proImagenurl = proImagenurl;
        this.proImagen = proImagen;
        this.proPrecioCostoPromedio = proPrecioCostoPromedio;
        this.proPrecioVentaRecomendado = proPrecioVentaRecomendado;
        this.proUnidadMedida = proUnidadMedida;
    }

    public int getIdListadoProductoTienda() {
        return idListadoProductoTienda;
    }

    public void setIdListadoProductoTienda(int idListadoProductoTienda) {
        this.idListadoProductoTienda = idListadoProductoTienda;
    }

    public String getLptDescripcionProductoTienda() {
        return lptDescripcionProductoTienda;
    }

    public void setLptDescripcionProductoTienda(String lptDescripcionProductoTienda) {
        this.lptDescripcionProductoTienda = lptDescripcionProductoTienda;
    }

    public double getLptStock() {
        return lptStock;
    }

    public void setLptStock(double lptStock) {
        this.lptStock = lptStock;
    }

    public String getLptUnidadMedida() {
        return lptUnidadMedida;
    }

    public void setLptUnidadMedida(String lptUnidadMedida) {
        this.lptUnidadMedida = lptUnidadMedida;
    }

    public double getLptStockMinimo() {
        return lptStockMinimo;
    }

    public void setLptStockMinimo(double lptStockMinimo) {
        this.lptStockMinimo = lptStockMinimo;
    }

    public String getLptImagen1() {
        return lptImagen1;
    }

    public void setLptImagen1(String lptImagen1) {
        this.lptImagen1 = lptImagen1;
    }

    public String getLptImagen2() {
        return lptImagen2;
    }

    public void setLptImagen2(String lptImagen2) {
        this.lptImagen2 = lptImagen2;
    }

    public String getLptImagen3() {
        return lptImagen3;
    }

    public void setLptImagen3(String lptImagen3) {
        this.lptImagen3 = lptImagen3;
    }

    public double getLptPrecioCompra() {
        return lptPrecioCompra;
    }

    public void setLptPrecioCompra(double lptPrecioCompra) {
        this.lptPrecioCompra = lptPrecioCompra;
    }

    public double getLptPrecioVenta() {
        return lptPrecioVenta;
    }

    public void setLptPrecioVenta(double lptPrecioVenta) {
        this.lptPrecioVenta = lptPrecioVenta;
    }

    public String getLpEstado() {
        return lpEstado;
    }

    public void setLpEstado(String lpEstado) {
        this.lpEstado = lpEstado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdTienda() {
        return idTienda;
    }

    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    public String getProDescripcion() {
        return proDescripcion;
    }

    public void setProDescripcion(String proDescripcion) {
        this.proDescripcion = proDescripcion;
    }

    public int getIdCategoriaProducto() {
        return idCategoriaProducto;
    }

    public void setIdCategoriaProducto(int idCategoriaProducto) {
        this.idCategoriaProducto = idCategoriaProducto;
    }

    public String getCpNombre() {
        return cpNombre;
    }

    public void setCpNombre(String cpNombre) {
        this.cpNombre = cpNombre;
    }

    public String getProImagenurl() {
        return proImagenurl;
    }

    public void setProImagenurl(String proImagenurl) {
        this.proImagenurl = proImagenurl;
    }

    public String getProImagen() {
        return proImagen;
    }

    public void setProImagen(String proImagen) {
        this.proImagen = proImagen;
    }

    public double getProPrecioCostoPromedio() {
        return proPrecioCostoPromedio;
    }

    public void setProPrecioCostoPromedio(double proPrecioCostoPromedio) {
        this.proPrecioCostoPromedio = proPrecioCostoPromedio;
    }

    public double getProPrecioVentaRecomendado() {
        return proPrecioVentaRecomendado;
    }

    public void setProPrecioVentaRecomendado(double proPrecioVentaRecomendado) {
        this.proPrecioVentaRecomendado = proPrecioVentaRecomendado;
    }

    public String getProUnidadMedida() {
        return proUnidadMedida;
    }

    public void setProUnidadMedida(String proUnidadMedida) {
        this.proUnidadMedida = proUnidadMedida;
    }

    @Override
    public String toString() {
        return "mip_EntidadProductosTienda{" +
                "idListadoProductoTienda=" + idListadoProductoTienda +
                ", lptDescripcionProductoTienda='" + lptDescripcionProductoTienda + '\'' +
                ", lptStock=" + lptStock +
                ", lptUnidadMedida='" + lptUnidadMedida + '\'' +
                ", lptStockMinimo=" + lptStockMinimo +
                ", lptImagen1='" + lptImagen1 + '\'' +
                ", lptImagen2='" + lptImagen2 + '\'' +
                ", lptImagen3='" + lptImagen3 + '\'' +
                ", lptPrecioCompra=" + lptPrecioCompra +
                ", lptPrecioVenta=" + lptPrecioVenta +
                ", lpEstado='" + lpEstado + '\'' +
                ", idProducto=" + idProducto +
                ", idTienda=" + idTienda +
                ", proDescripcion='" + proDescripcion + '\'' +
                ", idCategoriaProducto=" + idCategoriaProducto +
                ", cpNombre='" + cpNombre + '\'' +
                ", proImagenurl='" + proImagenurl + '\'' +
                ", proImagen='" + proImagen + '\'' +
                ", proPrecioCostoPromedio=" + proPrecioCostoPromedio +
                ", proPrecioVentaRecomendado=" + proPrecioVentaRecomendado +
                ", proUnidadMedida='" + proUnidadMedida + '\'' +
                '}';
    }
}
