
import java.net.URL;
import java.net.URLEncoder;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.util.Scanner;
import java.io.InputStreamReader;

import com.google.gson.*;

import java.io.BufferedReader;
// la URL del servicio web es http://localhost:8080/Servicio/rest/ws
// donde:
//	"Servicio" es el dominio del servicio web (es decir, el nombre de archivo Servicio.war)
//	"rest" se define en la etiqueta <url-pattern> de <servlet-mapping> en el archivo WEB-INF\web.xml
//	"ws" se define en la siguiente anotacin @Path de la clase Servicio



public class Cliente{
    
    static class Usuario
    {
      String email;
      String nombre;
      String apellido_paterno;
      String apellido_materno;
      String fecha_nacimiento;
      String telefono;
      String genero;
      byte[] foto;
    }
    static class Error
    {
        String message;

        Error(String message)
        {
            this.message = message;
        }
    }


    static String dominio = "localhost";
    static int puerto = 8080;
    static Gson j = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    static Scanner sc = new Scanner(System.in);

    
    public static HttpURLConnection conectar(String servicioWeb) throws Exception
    {
        URL url = new URL("http://"+dominio+":"+puerto+"/Servicio/rest/ws/" + servicioWeb);
        //System.out.println(url);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
        conexion.setDoOutput(true);
        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        return conexion;
    }

    public static void error(HttpURLConnection conexion)
    {
        try
        {
            BufferedReader br =new BufferedReader(new InputStreamReader(conexion.getErrorStream()));
            String respuesta;
            String r="";
            // el método web regresa una string en formato JSON
            while ((respuesta = br.readLine()) != null) 
                r = r+respuesta;
            Error e = (Error)j.fromJson(r,Error.class);
            throw new RuntimeException("HTTP "+conexion.getResponseCode()+":"+e.message);
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void  alta_usuario()
    {
        Usuario usuario = new Usuario();
        System.out.println("**********************");
        System.out.println("*  ALTA DE USUARIO:  *");
        System.out.println("**********************");
        
        System.out.print("Email:");
        usuario.email = sc.nextLine();
        System.out.print("Nombre:");
        usuario.nombre = sc.nextLine();
        System.out.print("Apellido paterno:");
        usuario.apellido_paterno = sc.nextLine();
        System.out.print("Apellido materno:");
        usuario.apellido_materno = sc.nextLine();
        System.out.print("Fecha de nacimiento (aa/mm/dd):");
        usuario.fecha_nacimiento = sc.nextLine();
        System.out.print("Teléfono:");
        usuario.telefono = sc.nextLine();
        System.out.print("Género (\"M\" o \"F\"):");
        usuario.genero = sc.nextLine();
        usuario.foto = null;

        
        try
        {
            // Se genera un JSON con la informacion del cliente
            String parametros = "usuario="+URLEncoder.encode(j.toJson(usuario),"UTF-8");
            //System.out.println(parametros);
            // Se obtiene una conexion al servicio web
            HttpURLConnection conexion = conectar("alta");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                error(conexion);
            else
                System.out.println("HTTP "+conexion.getResponseCode()+": OK");

                
            BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
            String respuesta;
            // el método web regresa una string en formato JSON
            while ((respuesta = br.readLine()) != null) 
                System.out.println(respuesta);
            conexion.disconnect();
        
            
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }



    public static void  consultar_usuario()
    {
        System.out.println("************************************");
        System.out.println("*  CONSULTA DE USUARIO POR EMAIL:  *");
        System.out.println("************************************");
        
        System.out.print("Email de usuario:");
        String email = sc.nextLine();

        try
        {
            String parametros = "email="+URLEncoder.encode(email,"UTF-8");
            // Se obtiene una conexion al servicio web
            HttpURLConnection conexion = conectar("consulta");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                error(conexion);
            else
                System.out.println("HTTP "+conexion.getResponseCode()+": OK");
            BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
            String respuesta="";
            String usuarioJson="";
            // el método web regresa una string en formato JSON
            while ((respuesta = br.readLine()) != null) 
                usuarioJson  = usuarioJson + respuesta;
            conexion.disconnect();
            Usuario u = (Usuario)j.fromJson(usuarioJson,Usuario.class);
            System.out.println("Nombre:"+u.nombre);
            System.out.println("Apellido paterno:"+u.apellido_paterno);
            System.out.println("Apellido materno:"+u.apellido_materno);
            System.out.println("Fecha de nacimiento (aa/mm/dd):"+u.fecha_nacimiento);
            System.out.println("Teléfono:"+u.telefono);
            System.out.println("Género (\"M\" o \"F\"):"+u.genero);
            
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }


    public static void  eliminar_usuario()
    {
        System.out.println("*******************************");
        System.out.println("*  BORRAR USUARIO POR EMAIL:  *");
        System.out.println("*******************************");
        
        System.out.print("Email de usuario a eliminar:");
        String email = sc.nextLine();

        try
        {
            String parametros = "email="+URLEncoder.encode(email,"UTF-8");
            // Se obtiene una conexion al servicio web
            HttpURLConnection conexion = conectar("borra");
            OutputStream os = conexion.getOutputStream();
            os.write(parametros.getBytes());
            os.flush();
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                error(conexion);
            else
                System.out.println("HTTP "+conexion.getResponseCode()+": OK");
        
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void  eliminar_usuarios()
    {
        System.out.println("***********************************");
        System.out.println("*  BORRADO DE TODOS LOS USUARIOS  *");
        System.out.println("***********************************");
        
        try
        {
            String parametros="";
            // Se obtiene una conexion al servicio web
            HttpURLConnection conexion = conectar("borrar_usuarios");
            OutputStream os = conexion.getOutputStream();
            //os.write(parametros.getBytes();
            os.flush();
            if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
                error(conexion);
            else
                System.out.println("HTTP "+conexion.getResponseCode()+": OK");
        
        }
        catch(Exception ex)
        {
            System.out.println(ex);
        }
    }

    public static void main(String[] args)
    {
        if(args.length>0)
            dominio =args[0];
        if(args.length>1)
            puerto =Integer.valueOf(args[1]);

        for(;;)
        {
            System.out.println("MENU:");
            System.out.println("a. Alta usuario");
            System.out.println("b. Consulta usuario");
            System.out.println("c. Borra usuario");
            System.out.println("d. Borra todos los usuarios");
            System.out.println("e. Salir");
            System.out.println("\nOpción: ");
            
            String opc = sc.nextLine();

            switch (opc)
            {
                case "a":
                    alta_usuario();
                break;
                case "b":
                    consultar_usuario();
                break;
                case "c":
                    eliminar_usuario();
                break;
                case "d":
                    eliminar_usuarios();
                break;
                case "e":
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                break;
                default:
                    System.out.println("No se reconoce esta opción, se sale del programa");
                    System.exit(0);
            }
        }
        
       
    
    }
}