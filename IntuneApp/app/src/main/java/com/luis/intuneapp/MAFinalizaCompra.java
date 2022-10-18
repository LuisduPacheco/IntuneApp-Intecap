package com.luis.intuneapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luis.intuneapp.basededatos.ProductoDAO;
import com.luis.intuneapp.basededatos.ProductoVO;

public class MAFinalizaCompra extends AppCompatActivity {
    String arregloProductos;
    Double precioFinal = 0.0;
    TextView textViewTotalFactura;
    Button buttonFinalizaCompra;
    EditText editTextTelefono, editTextNombre, editTextApellido, editTextDireccion, editTextNit, editTextTarjeta;
    Boolean banderaTarjeta = false;
    ProductoDAO productoDAO = new ProductoDAO();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mafinaliza_compra);
        //Inicializando los objetos y métodos
        textViewTotalFactura = findViewById(R.id.txtTotalFactura);
        buttonFinalizaCompra = findViewById(R.id.btnFacturar);
        editTextTelefono = findViewById(R.id.edtTelefono);
        editTextNombre = findViewById(R.id.edtNombreCliente);
        editTextApellido = findViewById(R.id.edtApellidoCliente);
        editTextDireccion = findViewById(R.id.edtDireccion);
        editTextNit = findViewById(R.id.edtNit);
        editTextTarjeta = findViewById(R.id.edtTarjeta);
        this.recibirDatos();
        this.mostrarPrecioFinal();
        this.clickFacturar();
        this.validarTarjeta();
    }
    //Metodo que finaliza la compra
    private void clickFacturar() {
        buttonFinalizaCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarEditText();

            }
        });
    }
    //Se recibe la cadena de String creada anteriormente en MAVerProductos
    private void recibirDatos() {
        Bundle bundle = getIntent().getExtras();
        arregloProductos = bundle.getString("productos");

    }

    //Se accede a la cantidad total de productos seleccionada por el usuario y a su precio.
    private void mostrarPrecioFinal (){
        //Se divide la cadena de Strings por el corchete final para separar los productos
        String[] productos = arregloProductos.split("]");
        for (String pvo: productos) {
            //Se separa el producto individualmente con sus atributos por medio de la 'coma'
            String[] cantidades = pvo.split(",");
            if(Double.parseDouble(cantidades[1]) != 0){
                //Se multiplican las cantidades y se guardan en una variable para obtener el precioFinal
                precioFinal += Double.parseDouble(cantidades[1])*Double.parseDouble(cantidades[2]);
            }
        }
        textViewTotalFactura.setText(String.valueOf("Q."+precioFinal));
    }


    private void actualizarCantidadProducto () {
        //Se divide la cadena de Strings por el corchete final para separar los productos
        String[] productos = arregloProductos.split("]");
        for (String pvo: productos) {
            //Se separa el producto individualmente con sus atributos por medio de la 'coma'
            String[] cantidades = pvo.split(",");
            if(Double.parseDouble(cantidades[1]) != 0){
                //Se crea un nuevo objeto VO por cada producto de la lista para operar sus cantidades
                ProductoVO productoVO = new ProductoVO();
               Integer cantidadFinal = Integer.parseInt(cantidades[4])-Integer.parseInt(cantidades[1]);
               productoVO.setCantidadProducto(cantidadFinal);
               productoVO.setIdProducto(Integer.parseInt(cantidades[3]));

               if (!productoDAO.actualizar(productoVO,getApplicationContext()) ){
                   Toast.makeText(this, "No se actualizaron los datos.", Toast.LENGTH_SHORT).show();
               }
            }
        }
    }

    public void enviarWhats(){
        String mensaje = "Gracias por su compra\nDatos de la Factura:\nNombre: "+editTextNombre.getText().toString()+"\nApellido: "+editTextApellido.getText().toString()+
                "\nDirección: "+editTextDireccion.getText().toString()+"\nNit: "+editTextNit.getText().toString()+"\n";
        mensaje += "Nombre producto:  Cantidad  Precio  Total\n";
        //Se divide la cadena de Strings por el corchete final para separar los productos
        String[] productos = arregloProductos.split("]");
        for (String pvo: productos) {
            //Se separa el producto individualmente con sus atributos por medio de la 'coma'
            String[] cantidades = pvo.split(",");
            if(Double.parseDouble(cantidades[1]) != 0){
                //Se agregan espacios para luego extraer una parte del String predefinida y luego unir los Strings y que queden alineados
                String pr = cantidades[0]+"                                                                                          ";
                pr = pr.substring(0,28);
                String cantidad = cantidades[1]+"                                                                                     ";
                cantidad = cantidad.substring(0,10);
                String precio = cantidades[2]+"                               ";
                //Se extrae el String predefinido de la posición 0 a la 10
                precio = precio.substring(0,10);
                Double precioFinal = Double.parseDouble(cantidades[1])*Double.parseDouble(cantidades[2]);
                //Se concatena el mensaje final
                mensaje += pr+cantidad+precio+precioFinal+"\n";
            }
        }
        mensaje += "Total:                                                  Q."+precioFinal;
        //Condicionales para enviar los datos por WhatsApp
        if(editTextTelefono.getText().toString().isEmpty()){ // Esta opción es en el caso de que el usuario quiera enviar los datos a un contacto guardado
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            startActivity(sendIntent);
        }else {
            //En este caso se envia el detalle de la factura al número ingresado en el campo EditTextTelefono
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_VIEW);
            String uri = "whatsapp://send?phone="+editTextTelefono.getText().toString()+"&text="+mensaje;
            sendIntent.setData(Uri.parse(uri));
            startActivity(sendIntent);
        }
    }

    //VALIDACIONES PARA FINALIZAR LA COMPRA
    public void validarEditText(){
        //Validacion de los campos vacios y también de la banderaTarjeta
        if(!editTextNombre.getText().toString().isEmpty() && !editTextApellido.getText().toString().isEmpty() &&
            !editTextDireccion.getText().toString().isEmpty() && !editTextNit.getText().toString().isEmpty() &&
            !editTextTarjeta.getText().toString().isEmpty() && !editTextTelefono.getText().toString().isEmpty()){
                if(banderaTarjeta){
                    //Si banderaTarjeta es True se actualizan los datos
                    actualizarCantidadProducto();
                    enviarWhats();
                    Toast.makeText(this, "Compra finalizada con exito, detalles enviados por Whatsapp", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(this, "No de tarjeta invalida", Toast.LENGTH_SHORT).show();
                }
        }else{
            Toast.makeText(this, "Llene todos los campos para finalizar la compra exitosamente", Toast.LENGTH_SHORT).show();
        }
    }

    public void validarTarjeta(){
        editTextTarjeta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Tomando el string de la tarjeta
                String tarjeta = editTextTarjeta.getText().toString();
                tarjeta = tarjeta.replace(" ", "");

                int contadorT = 0;
                //Se toma la longitud de la tarjeta ingresada
                if (tarjeta.length() == 16) {
                    for (int i = 0; i < 16; i++) {
                        int digito = Integer.parseInt(String.valueOf(tarjeta.charAt(i)));
                        if (i % 2 == 0) {
                            digito = digito * 2;
                            if (String.valueOf(digito).length() > 1) {
                                int valorUno = Integer.parseInt(String.valueOf(String.valueOf(digito).charAt(0)));
                                int valorDos = Integer.parseInt(String.valueOf(String.valueOf(digito).charAt(1)));
                                digito = valorUno + valorDos;
                            }
                        }
                        contadorT += digito;
                    }
                    if (contadorT % 10 == 0) {
                        banderaTarjeta = true;
                        editTextTarjeta.setTextColor(Color.parseColor("#006414"));
                        Toast.makeText(MAFinalizaCompra.this, "No.Tarjeta válido", Toast.LENGTH_SHORT).show();
                    } else {
                        banderaTarjeta = false;
                        editTextTarjeta.setTextColor(Color.parseColor("#FF0000"));
                        Toast.makeText(MAFinalizaCompra.this, "No. Tarjeta inválida", Toast.LENGTH_SHORT).show();
                    }
                } else if (tarjeta.length() > 16) {
                    banderaTarjeta = false;
                    editTextTarjeta.setTextColor(Color.parseColor("#FF0000"));
                } else if (tarjeta.length() < 16) {
                    banderaTarjeta = false;
                    editTextTarjeta.setTextColor(Color.parseColor("#FF0000"));
                }
            }
        });
    }
}