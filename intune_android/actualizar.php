<?php
    include_once("conexion.php");

    $id_producto = $_GET['id_producto'];
    if(isset($id_producto)){
        $cantidad_producto = $_GET['cantidad_producto'];
        
        $consulta = "UPDATE producto SET cantidad_producto = '$cantidad_producto'
                                            WHERE id_producto=$id_producto";
                    if(mysqli_query($conexion, $consulta)){
                        $retorno = array('resultado'=>'OK');
                        echo json_encode($retorno);
                        mysqli_close($conexion);
                        
                    }else{
                        mysqli_close($conexion);
                        die("Error en la Inserción ".mysqli_connect_error());
                    }
    }else{
        mysqli_close($conexion);
		die("Error en la obtención de la informacion del producto");
    }
?>