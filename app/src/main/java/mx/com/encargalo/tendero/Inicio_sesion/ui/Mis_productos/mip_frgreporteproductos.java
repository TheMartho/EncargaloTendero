package mx.com.encargalo.tendero.Inicio_sesion.ui.Mis_productos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import mx.com.encargalo.R;
import mx.com.encargalo.tendero.Adapter.mip_adapproductostienda;
import mx.com.encargalo.tendero.Entidad.mip_EntidadProductosTienda;
import mx.com.encargalo.tendero.Util.Util;

public class mip_frgreporteproductos extends Fragment {

    Spinner mip_repspncategoria; //+@OHC20.11.2022
    RecyclerView mip_reprclvproductos; //+@OHC20.11.2022
    Button mip_repbtnexportar; //+@OHC20.11.2022

    ArrayList<mip_EntidadProductosTienda> mit_arlproductostienda; //+@OHC20.11.2022
    ProgressDialog mip_pdproductostienda; //+@OHC20.11.2022
    RequestQueue mip_rqproductostienda; //+@OHC20.11.2022
    JsonObjectRequest mip_jorproductostienda; //+@OHC20.11.2022

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mip_frgreporteproductos, container, false);

        mip_repspncategoria = view.findViewById(R.id.mip_repspncategoria);
        mip_reprclvproductos = view.findViewById(R.id.mip_reprclvproductos);

        mip_repbtnexportar = view.findViewById(R.id.mip_repbtnexportar);

        mit_arlproductostienda = new ArrayList<>(); //+@OHC20.11.2022
        mit_arlproductostienda.clear();
        mip_reprclvproductos.setLayoutManager(new LinearLayoutManager(this.getContext())); //+@OHC04.11.2022
        mip_reprclvproductos.setHasFixedSize(true); //+@OHC04.11.2022

        SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_TEMPORALES, Context.MODE_PRIVATE);
        SharedPreferences preferencias_prod = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias_prod.edit();
        editor.clear().apply();
        mip_rqproductostienda = Volley.newRequestQueue(requireContext()); //+@OHC04.11.2022

        mip_repspncategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                    cargarDatos("", preferencias.getString("idTienda", ""), view);
                } else {
                    cargarDatos(mip_repspncategoria.getSelectedItem().toString(), preferencias.getString("idTienda", ""), view);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }); //+}@OHC08.11.2022

        mip_repbtnexportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExportarDatos();
            }
        });

        return view;
    }

    private void cargarDatos(String p_categoria, String p_idTienda, View view) {
        mip_pdproductostienda = new ProgressDialog(getContext());
        mip_pdproductostienda.setMessage("Consultando Productos");
        mip_pdproductostienda.show();

        String url = Util.RUTA + "c_productos_por_tienda_mitienda.php?p_categoria=" + p_categoria + "&p_idTienda=" + p_idTienda;
        url = url.replace(" ", "%20");
        mip_jorproductostienda = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mit_arlproductostienda.clear();
                mip_pdproductostienda.hide();
                mip_EntidadProductosTienda mit_etmisproductos;
                JSONArray json_misproductos = response.optJSONArray("productos_tienda");
                try {
                    for (int i = 0; i < json_misproductos.length(); i++) {

                        mit_etmisproductos = new mip_EntidadProductosTienda();
                        JSONObject rt_jojsonObject_misproductos;
                        rt_jojsonObject_misproductos = json_misproductos.getJSONObject(i);

                        mit_etmisproductos.setIdListadoProductoTienda(rt_jojsonObject_misproductos.optInt("idListadoProductoTienda"));
                        mit_etmisproductos.setLptDescripcionProductoTienda(rt_jojsonObject_misproductos.optString("lptDescripcionProductoTienda"));
                        mit_etmisproductos.setLptStock(rt_jojsonObject_misproductos.optInt("lptStock"));
                        mit_etmisproductos.setLptUnidadMedida(rt_jojsonObject_misproductos.optString("lptUnidadMedida"));
                        mit_etmisproductos.setLptStockMinimo(rt_jojsonObject_misproductos.optInt("lptStockMinimo"));
                        mit_etmisproductos.setLptImagen1(rt_jojsonObject_misproductos.optString("lptImagen1"));
                        mit_etmisproductos.setLptImagen2(rt_jojsonObject_misproductos.optString("lptImagen2"));
                        mit_etmisproductos.setLptImagen3(rt_jojsonObject_misproductos.optString("lptImagen3"));
                        mit_etmisproductos.setLptPrecioCompra(rt_jojsonObject_misproductos.optDouble("lptPrecioCompra"));
                        mit_etmisproductos.setLptPrecioVenta(rt_jojsonObject_misproductos.optDouble("lptPrecioVenta"));
                        mit_etmisproductos.setIdProducto(rt_jojsonObject_misproductos.optInt("idProducto"));
                        mit_etmisproductos.setIdCategoriaProducto(rt_jojsonObject_misproductos.optInt("idCategoriaProducto"));
                        mit_etmisproductos.setCpNombre(rt_jojsonObject_misproductos.optString("cpNombre"));

                        mit_arlproductostienda.add(mit_etmisproductos);
                    }
                    mip_pdproductostienda.hide();


                    mip_adapproductostienda adapter = new mip_adapproductostienda(getContext(), mit_arlproductostienda,
                            (item, vista) -> {
                                //CONTENIDO
                                SharedPreferences preferencias = requireActivity().getSharedPreferences(Util.ARCHIVO_PREFRENCIAS_PRODUCTO, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferencias.edit();
                                editor.putString("idListadoProductoTienda", String.valueOf(item.getIdListadoProductoTienda()));
                                editor.putString("lptDescripcionProductoTienda", String.valueOf(item.getLptDescripcionProductoTienda()));
                                editor.putString("lptStock", String.valueOf(item.getLptStock()));
                                editor.putString("lptUnidadMedida", String.valueOf(item.getLptUnidadMedida()));
                                editor.putString("lptStockMinimo", String.valueOf(item.getLptStockMinimo()));
                                editor.putString("lptImagen1", String.valueOf(item.getLptImagen1()));
                                editor.putString("lptImagen1_tmp", String.valueOf(item.getLptImagen1()));
                                editor.putString("lptImagen2", String.valueOf(item.getLptImagen2()));
                                editor.putString("lptImagen2_tmp", String.valueOf(item.getLptImagen2()));
                                editor.putString("lptImagen3", String.valueOf(item.getLptImagen3()));
                                editor.putString("lptImagen3_tmp", String.valueOf(item.getLptImagen3()));
                                editor.putString("lptPrecioCompra", String.valueOf(item.getLptPrecioCompra()));
                                editor.putString("lptPrecioVenta", String.valueOf(item.getLptPrecioVenta()));
                                editor.putString("idProducto", String.valueOf(item.getIdProducto()));
                                editor.putString("idCategoriaProducto", String.valueOf(item.getIdCategoriaProducto()));
                                editor.putString("cpNombre", String.valueOf(item.getCpNombre()));
                                editor.apply();
                                Navigation.findNavController(vista).navigate(R.id.action_mip_frgreporteproductos_to_mip_frgdetalleproducto);
                            });

                    mip_reprclvproductos.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    mip_pdproductostienda.hide();
                }
            }//+}@OHC20.11.2022
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mip_pdproductostienda.hide();

                if (error.toString().equals("com.android.volley.ParseError: org.json.JSONException: Value [] of type org.json.JSONArray cannot be converted to JSONObject")) {

                    mit_arlproductostienda.clear();
                    mip_adapproductostienda adapter = new mip_adapproductostienda(getContext(), mit_arlproductostienda, (item, vista1) -> {
                    });
                    mip_reprclvproductos.setAdapter(adapter);

                    Toast.makeText(getContext(), "No tiene productos registrados!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error al consultar " + error.toString(), Toast.LENGTH_SHORT).show();
                }
            } //+}@OHC20.11.2022
        });
        mip_rqproductostienda.add(mip_jorproductostienda);
    }

    private void ExportarDatos() {

        if (mit_arlproductostienda.size() == 0)
            Toast.makeText(getContext(), "No tiene productos registrados!", Toast.LENGTH_SHORT).show();

        else {
            Workbook workbook = new HSSFWorkbook();
            Cell cell = null;
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
            cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            Sheet sheet = null;
            sheet = workbook.createSheet("REPORTE PRODUCTOS");

            Row row = null;

            row = sheet.createRow(0);
            cell = row.createCell(0);
            cell.setCellValue("Código Producto"); //idProducto
            cell.setCellStyle(cellStyle);

            sheet.createRow(1);
            cell = row.createCell(1);
            cell.setCellValue("Descripción"); //lptDescripcionProductoTienda
            cell.setCellStyle(cellStyle);

            sheet.createRow(2);
            cell = row.createCell(2);
            cell.setCellValue("Categoría"); //cpNombre
            cell.setCellStyle(cellStyle);

            sheet.createRow(3);
            cell = row.createCell(3);
            cell.setCellValue("Precio Compra"); //lptPrecioCompra
            cell.setCellStyle(cellStyle);

            sheet.createRow(4);
            cell = row.createCell(4);
            cell.setCellValue("Precio Venta"); //lptPrecioVenta
            cell.setCellStyle(cellStyle);

            sheet.createRow(5);
            cell = row.createCell(5);
            cell.setCellValue("Stock"); //lptStock
            cell.setCellStyle(cellStyle);

            sheet.createRow(6);
            cell = row.createCell(6);
            cell.setCellValue("Stock Mínimo"); //lptStockMinimo
            cell.setCellStyle(cellStyle);

//            mip_EntidadProductosTienda mit_etreportemisproductos;
            int i = 1;
            for (mip_EntidadProductosTienda mit_etreportemisproductos : mit_arlproductostienda) {

                row = sheet.createRow(i);

                cell = row.createCell(0);
                cell.setCellValue(mit_etreportemisproductos.getIdProducto());

                cell = row.createCell(1);
                cell.setCellValue(mit_etreportemisproductos.getLptDescripcionProductoTienda());

                cell = row.createCell(2);
                cell.setCellValue(mit_etreportemisproductos.getCpNombre());

                cell = row.createCell(3);
                cell.setCellValue(mit_etreportemisproductos.getLptPrecioCompra());

                cell = row.createCell(4);
                cell.setCellValue(mit_etreportemisproductos.getLptPrecioVenta());

                cell = row.createCell(5);
                cell.setCellValue(mit_etreportemisproductos.getLptStock());

                cell = row.createCell(6);
                cell.setCellValue(mit_etreportemisproductos.getLptStockMinimo());

                i++;
            }

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Reporte_Productos.xls");
            FileOutputStream outputStream = null;

            try {
                outputStream = new FileOutputStream(file);
                workbook.write(outputStream);
                Toast.makeText(getContext().getApplicationContext(), "Exportado Correctamente", Toast.LENGTH_LONG).show();
            } catch (java.io.IOException e) {
                e.printStackTrace();

                Toast.makeText(getContext().getApplicationContext(), "Error al Exportar", Toast.LENGTH_LONG).show();
                try {
                    outputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

}