    <?php 
        $host = "intecap-2022.mysql.database.azure.com";
        $usuario = "grupo3@intecap-2022.mysql.database.azure.com";
        $contrasenia = "grupo32022.";
        $bd = "dbintune";
        $conexion = mysqli_init();
    //Realizar la conexion
    mysqli_real_connect($conexion,$host,$usuario,$contrasenia,$bd);
    mysqli_set_charset($conexion,"utf8");

    //Prueba de coenxion
    if(!mysqli_connect_error($conexion)){
        //echo "Conexion exitosa... <br>";
    }else{
        die("Error de conexion". mysqli_connect_error());
    }

    ?>
