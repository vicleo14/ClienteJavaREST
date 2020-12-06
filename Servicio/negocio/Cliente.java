
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

    /*static Gson j = new GsonBuilder()
		.registerTypeAdapter(byte[].class,new AdaptadorGsonBase64())
		.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
		.create();*/
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

    public static void  alta_usuario()
    {
        Usuario usuario = new Usuario();
        System.out.println("ALTA DE USUARIO:");
        
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
                throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
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
        finally
        {}
        


    }
    public static void main(String[] args)
    {
        
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
                    System.out.println("B");
                break;
                case "c":
                    System.out.println("C");
                break;
                case "d":
                    System.out.println("D");
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
 /*
        try{
        
        
        

        
        String parametros ="a=100&b=200";
        OutputStream os = conexion.getOutputStream();
        os.write(parametros.getBytes());
        os.flush();
        if (conexion.getResponseCode() != HttpURLConnection.HTTP_OK)
            throw new RuntimeException("Codigo de error HTTP: " + conexion.getResponseCode());
        BufferedReader br = new BufferedReader(new InputStreamReader((conexion.getInputStream())));
        String respuesta;
// el método web regresa una string en formato JSON
    while ((respuesta = br.readLine()) != null) 
        System.out.println(respuesta);
    conexion.disconnect();
        }
    catch(Exception ex)
    {}*/