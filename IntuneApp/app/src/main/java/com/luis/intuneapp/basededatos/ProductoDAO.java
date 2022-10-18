package com.luis.intuneapp.basededatos;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.luis.intuneapp.complementos.Constantes;
import com.luis.intuneapp.complementos.ConsultasDAO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProductoDAO implements ConsultasDAO, Response.ErrorListener {
    @Override
    public boolean listarMostrarSW(ProductoVO pvo, Context context, Response.Listener listener, Response.ErrorListener errorListener) {
        boolean resultado = false;
        try {
            String url = Constantes.IPSERVER+"intune_android/mostrar.php";
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null, listener, errorListener);
            requestQueue.add(jsonObjectRequest);
            resultado = true;
        }catch (Exception e){
            Toast.makeText(context, "Error en la conexión"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

    @Override
    public ArrayList<ProductoVO> respuestaListarMostrar(JSONObject respuesta) {
        ArrayList<ProductoVO> arrayListVO = new ArrayList<>();
        JSONArray jsonArray = respuesta.optJSONArray("producto");
        try {
            for (int i=0; i<jsonArray.length(); i++){
                ProductoVO productoVO = new ProductoVO();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                productoVO.setIdProducto(jsonObject.optInt("id_producto"));
                productoVO.setDescripcionProducto(jsonObject.optString("descripcion_producto"));
                productoVO.setCantidadProducto(jsonObject.optInt("cantidad_producto"));
                productoVO.setPrecioProducto(jsonObject.optDouble("precio_producto"));
                productoVO.setEstadoProducto(jsonObject.optInt("estado_producto"));
                productoVO.setIdMarca(jsonObject.optInt("id_marca_fk)"));
                arrayListVO.add(productoVO);
            }
        }catch (JSONException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return arrayListVO;
    }

    @Override
    public boolean actualizar(ProductoVO pvo, Context context) {
        boolean resultado = false;
        //banderaDeUso = Constantes.ACTUALIZAR;
        try {
            String url = Constantes.IPSERVER+"intune_android/actualizar.php?cantidad_producto="+pvo.getCantidadProducto()+"&id_producto="+pvo.getIdProducto();
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,
                    null,this);
            requestQueue.add(jsonObjectRequest);
            resultado = true;
        }
        catch (Exception e){
            Toast.makeText(context, "Error en la conexion "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return resultado;
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        System.err.println(error);
    }



}
