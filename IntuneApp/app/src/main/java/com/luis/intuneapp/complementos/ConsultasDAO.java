package com.luis.intuneapp.complementos;

import android.content.Context;


import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luis.intuneapp.basededatos.ProductoVO;

import org.json.JSONObject;

import java.util.ArrayList;

public interface ConsultasDAO {
    public boolean listarMostrarSW(ProductoVO pvo, Context context, Response.Listener listener, Response.ErrorListener errorListener);
    public ArrayList<ProductoVO> respuestaListarMostrar(JSONObject respuesta);
    public boolean actualizar(ProductoVO pvo, Context context);

    void onResponse(JSONObject response);

    void onErrorResponse(VolleyError error);
}
