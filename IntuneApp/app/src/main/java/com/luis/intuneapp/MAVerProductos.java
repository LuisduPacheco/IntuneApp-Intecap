package com.luis.intuneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.luis.intuneapp.basededatos.ProductoDAO;
import com.luis.intuneapp.basededatos.ProductoVO;

import org.json.JSONObject;

import java.util.ArrayList;

public class MAVerProductos extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {

    private ArrayList<Integer> productosC = new ArrayList<>();
    private Button buttonComprar;
    ArrayList<ProductoVO> list = new ArrayList<>();

    private ProductoVO pvo = new ProductoVO();
    private ProductoDAO pdao = new ProductoDAO();
    ListView listViewMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maver_productos);

        //Asignación de objetos
        listViewMain = findViewById(R.id.listProductos);
        pdao.listarMostrarSW(pvo, getApplicationContext(),this, this);
        buttonComprar = findViewById(R.id.btnComprarSeleccion);
        this.clickButtonComprar();

    }

    public void consultaProductos(JSONObject response){
        //Mediante la BD se agregan los productos a una lista y aparte se le agrega un contador especifico
        if(pdao.respuestaListarMostrar(response) != null){
            for(ProductoVO prod: pdao.respuestaListarMostrar(response)){
                if(prod.getEstadoProducto() != 0 && prod.getCantidadProducto() != 0){
                    productosC.add(0);
                    list.add(new ProductoVO(prod.getDescripcionProducto(), prod.getCantidadProducto(), prod.getPrecioProducto(), prod.getIdProducto()));
                }
            }
            //Se inicializa el adaptador de la Lista mandando como parametro los productos y contadores
            AdapterListMain adapterListMain = new AdapterListMain(this, R.layout.recyclerview_item, list, productosC);
            listViewMain.setAdapter(adapterListMain);

        }
    }

    //Apertura la siguiente Activity FinalizarCompra
    private void clickButtonComprar() {
        buttonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirFinalizaCompra();
                finish();
            }
        });
    }

    private void abrirFinalizaCompra(){
        Intent intent =new Intent(getApplicationContext(), MAFinalizaCompra.class);
        intent.putExtra("productos", crearArregloProductos());
        startActivity(intent);
    }

    /*Con todos los datos de los productos se crea una cadena de String la cual mediante ', (comas)'
    separa los atributos del producto y mediante el corchete final se separan los productos para luego acceder a ellos y
    volverlos listas.*/

    //Metodo que retorna el String y se manda a FinalizarCompra
    private String crearArregloProductos(){
        String arregloP = "";
        int contador = 0;
        for (ProductoVO pvo: list) {
            arregloP += pvo.getDescripcionProducto()+","+productosC.get(contador)+","+pvo.getPrecioProducto()+","+pvo.getIdProducto()+","+
                        pvo.getCantidadProducto()+"]";
            contador ++;

        }
        return arregloP;
    }

    @Override
    public void onResponse(JSONObject response) {
        consultaProductos(response);
    }



    @Override
    public void onErrorResponse(VolleyError error) {
        System.err.println(error);
    }

}