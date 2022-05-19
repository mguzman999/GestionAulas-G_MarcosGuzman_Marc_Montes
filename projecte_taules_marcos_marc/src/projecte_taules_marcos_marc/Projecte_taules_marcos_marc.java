// Marcos Guzman
// Marc Montes
// Projecte final de taules MP03
package projecte_taules_marcos_marc;

// Importaciones del programa
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Projecte_taules_marcos_marc {

    public static void main(String[] args) {
        //creamos un objeto de tipo fila para asignarle un archivo
        File archivoUsers = new File("C:/Users/alumne/Desktop/projecte_m03/users.dat");
        File ficheroMesas = new File("C:/Users/alumne/Desktop/projecte_m03/taules.txt");

        if(!archivoUsers.isFile()){
            crearArchivoUsuarios(archivoUsers);
        }
        if(!ficheroMesas.isFile()){
            crearFicheroMesas(ficheroMesas);
        }
        
        MenuLogin(archivoUsers);

    }

    /* PARTE MENU LOGIN DE LOS USUARIOS*/
    
    // Funcion que sera el menu login para todos los usuarios
    private static void MenuLogin(File archivoUsers){
        Scanner sc = new Scanner(System.in);
        int intentos = 0;
        int opcion;
        boolean salir = false;
        
        do {

            System.out.println("\nBIENVENIDO AL LOGIN DEL USUARIO\n");

            System.out.println("***********************************\n");

            System.out.println(" (1) - Entrar al menu login");
            System.out.println(" (2) - Salir");
            System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();
            
            switch (opcion){
                case 1:
                    do{
                        System.out.print("Introduzca su usuario: ");
                        String usuario = sc.next();
                        System.out.print("Introduzca su contraseña: ");
                        String passwd = sc.next();
                        MenuAdmin(archivoUsers);
                        intentos++;
                    }while(intentos < 1);
                    break;
                case 2:
                    salir = true;
                    break;
                default:
                    System.out.println("Has introducido una opcion incorrecta.");
                    break;
            }
            
            //condicion salida del bucle
        }while (!salir);
    }
    
    /* PARTE MENU DE LOS CAMAREROS Y FUNCIONES*/
    
    // Funcion que imprime el menu del camarero y gestiona la eleccion del usuario
    private static void MenuCamarero(File ficheroMesas) {
        Scanner sc = new Scanner(System.in);

        //variable seleccion usuario, ruta de fichero, funcion fichero si no existe, variable salida menu
        int opcion;
        
        boolean salir = false;

        //bucle eleccion usuario
        do {

            System.out.println("\nBIENVENIDO AL MENU DEL CAMARERO\n");

            System.out.println("***********************************\n");

            System.out.println(" (1) - Listar todas las mesas");
            System.out.println(" (2) - Dar de alta una mesa");
            System.out.println(" (3) - Modificar/Editar una mesa");
            System.out.println(" (4) - Borrar una mesa");
            System.out.println(" (5) - Salir del menu de camarero");

            System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();

            //condicion sobre eleccion usuario, llamadas a las funciones especificas
            
            switch (opcion){
                case 1:
                    listarMesa(ficheroMesas);
                    break;
                case 2:
                    darAltaMesa(ficheroMesas);
                    break;
                case 3:
                    modificarMesa(ficheroMesas);
                    break;
                case 4:
                    eliminarMesa(ficheroMesas);
                    break;
                case 5:
                    salir = true;
                    System.out.println("SALIENDO DEL MENU DE CAMARERO...");
                    break;
                default:
                    System.out.println("ERROR NUMERO INCORRECTO");
                    break;
            }

            //condicion salida del bucle
        } while (opcion < 1 || opcion > 5 || !salir);

    }

    // Funcion para crear y escribir dentro del fichero nuevo, si es el caso que no existe, lo cargaremos con mesas precargadas, le pasamos el fichero
    private static void crearFicheroMesas(File ficheroMesas) {
        try {
            FileWriter writer = new FileWriter(ficheroMesas);

            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }
    
    // Funcion, devuelve la lista linea a linea del fichero + condicion
    private static void listarMesa(File ficheroMesas) {
        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(ficheroMesas, "ISO-8859-1");
            System.out.println("");
            while (lectorFichero.hasNext()) {

                System.out.println(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }
    }

    // Funcion para añadir una nueva linea al fichero (al final del archivo), le pasamos el objeto fichero
    private static void darAltaMesa(File ficheroMesas) {
        Scanner sc = new Scanner(System.in);

        //pedimos los datos al usuario
        System.out.print("INTRODUCE EL ID DE LA MESA: ");
        String idMesa = sc.next();

        System.out.print("INTRODUCE LA DESCRIPCION DE LA MESA: ");
        String descMesa = sc.next();

        System.out.print("INTRODUCE LA CAPACIDAD MAXIMA DE PERSONAS DE LA MESA: ");
        String cantMesa = sc.next();

        System.out.print("INTRODUCE (SI - NO) SI HAY SILLAS PARA LOS PEQUES: ");
        String peqMesa = sc.next();

        System.out.print("INTRODUCE EL NUMERO DE SILLAS DE ADULTOS DE LAS MESA: ");
        String sillMesa = sc.next();

        System.out.print("INTRODUCE (SI - NO) SI HAY VENTILADOR EN LA MESA: ");
        String ventMesa = sc.next();

        System.out.print("INTRODUCE (SI - NO) SI LA MESA ESTA EN EL JARDIN: ");
        String jardMesa = sc.next();

        try {

            // El true al final indica que escribiremos al final del fichero añadiendo texto
            FileWriter writer = new FileWriter(ficheroMesas, true);

            //escribimos en el fichero con el formato establecido por (;)
            writer.write(idMesa + ";" + descMesa + ";" + cantMesa + ";" + peqMesa + ";" + sillMesa + ";" + ventMesa + ";" + jardMesa + ";\n");

            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }

    // Funcion para modificar lineas del fichero, le pasamos el objeto fichero
    private static void modificarMesa(File ficheroMesas) {
        Scanner sc = new Scanner(System.in);
        // Array para guardar todas las líneas leídas del fichero
        ArrayList<String> lineas = new ArrayList<>();

        // Abrimos el fichero de texto para leerlo en memoria
        try {
            Scanner lectorFichero = new Scanner(ficheroMesas);

            int i = 0;

            while (lectorFichero.hasNext()) {
                lineas.add(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }

        // Abrimos el fichero de texto para sobreescribirlo
        // Pediremos los datos de la mesa que queremos modificar
        System.out.print("INTRODUCE EL ID DE LA MESA QUE QUIERES MODIFICAR: ");
        String idmesa = sc.next();

        System.out.print("INTRODUCE LA NUEVA DESCRIPCION DE LA MESA: ");
        String descmesa = sc.next();

        try {
            FileWriter writer = new FileWriter(ficheroMesas);

            for (String linea : lineas) {

                //utilizaremos una funcion de java para separa por posiciones las lineas
                String frase = linea;
                String[] parts = frase.split(";");

                //dependiedo de las posiciones del array creado sabemos que campos consultar/modificar
                if (parts[0].equalsIgnoreCase(idmesa)) {

                    parts[1] = descmesa;

                    //damos formato a la frase
                    String fraseformat = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4] + ";" + parts[5] + ";" + parts[6] + "; \n";

                    //escribimos la nueva linea en el archivo fichero
                    writer.write(fraseformat);
                    System.out.println("LA MESA HA SIDO MODIFICADA \n");

                    System.out.println("");
                    System.out.println("EL ID DE LA MESA ES: " + parts[0]);
                    System.out.println("LA DESCRIPCION DE LA MESA ES: " + parts[1]);
                    System.out.println("LA CAPACIDAD MAXIMA DE LA MESA ES DE: " + parts[2]);
                    System.out.println("EXISTEN SILLAS ADAPTADAS PARA LOS PEQUES: " + parts[3]);
                    System.out.println("NUMERO DE SILLAS QUE DISPONE LA MESA: " + parts[4]);
                    System.out.println("HAY VENTILADOR EN LA MESA? " + parts[5]);
                    System.out.println("LA MESA PERTENECE AL JARDIN? " + parts[6]);
                    System.out.println("");

                    //escribimos las demas lineas que no cumplen la condicion
                } else {
                    writer.write(linea + "\n");

                }

            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/sobreescribir el fichero");
        }

    }

    // Funcion para eliminar lineas del fichero seguimos el mismo algoritmo de modificar una linea para eliminarla
    private static void eliminarMesa(File ficheroMesas) {
        Scanner sc = new Scanner(System.in);
        // Array para guardar todas las líneas leídas del fichero
        ArrayList<String> lineas = new ArrayList<>();

        // Abrimos el fichero de texto para leerlo en memoria
        //solicitamos el id de la mesa al usuario
        System.out.print("INTRODUCE EL ID DE LA MESA QUE QUIERES ELIMINAR: ");
        String idmesa = sc.next();
        try {
            Scanner lectorFichero = new Scanner(ficheroMesas);

            int i = 0;

            while (lectorFichero.hasNext()) {
                lineas.add(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }

        // Abrimos el fichero de texto para sobreescribirlo
        // Eliminaremos el id de la mesa que el usuario introduzca
        try {
            FileWriter writer = new FileWriter(ficheroMesas);

            for (String linea : lineas) {

                String frase = linea;
                String[] parts = frase.split(";");

                //mietras no cumpla la condicion, escribiremos las lineas en el fichero
                if (!parts[0].equalsIgnoreCase(idmesa)) {
                    writer.write(linea + "\n");

                }
            }

            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/sobreescribir el fichero");
        }
    }

    /* PARTE MENU DE LOS ADMINS Y FUNCIONES*/
    
    // Funcion que imprime el menu del administrador y gestiona la eleccion del usuario
    private static void MenuAdmin(File archivoUsers) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opcion;
        
        do {
        System.out.println("\nBIENVENIDO AL MENU DEL ADMIN\n");

        System.out.println("***********************************\n");

        System.out.println(" (1) - Alta de los usuarios");
        System.out.println(" (2) - Listar todos los usuarios de la base de datos");
        System.out.println(" (3) - Modificar la contraseña y el rol");
        System.out.println(" (4) - Eliminar un usuario");
        System.out.println(" (5) - Salir del menu de admin");
        
        System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();
        
        switch (opcion){
                case 1:
                    altaUsuarios(archivoUsers);
                    break;
                case 2:
                    listarUsuarios(archivoUsers);
                    break;
                case 3:
                    modificarPasswdYRol();
                    break;
                case 4:
                    eliminarUsuario();
                    break;
                case 5:
                    salir = true;
                    System.out.println("SALIENDO DEL MENU DE ADMIN...");
                    break;
                default:
                    System.out.println("ERROR NUMERO INCORRECTO");
                    break;
            }
        }while(opcion < 1 || opcion > 5 || !salir);
    }
    
    // Funcion para crear y escribir dentro del fichero nuevo, si es el caso que no existe, lo cargaremos con mesas precargadas, le pasamos el fichero
    private static void crearArchivoUsuarios(File archivoUsers) {
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers));

            Empleados personal = new Empleados();
            
            // Creamos un nuevo empleado en la 1a posición del array
            personal.usuario = "admin";
            personal.contraseña = "root";
            personal.rol = "admin";
            
            // Con writeObject escribimos directamente el empleado
            fichero.writeObject(personal);
            
            // Cerramos el fichero
            fichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al guardar el fichero");
        }
    }
    
    // Funcion crear archivo binario usuarios y agregar datos a este
    private static void altaUsuarios(File archivoUsers) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Introduzca el nombre de usuario del nuevo usuario: ");
        String newUser = sc.next();
        System.out.print("Introduzca la contraseña del nuevo usuario: ");
        String newPasswd = sc.next();
        System.out.print("Introduzca el rol del nuevo usuario: ");
        String newRol = sc.next();
        
        
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers));

            Empleados personal = new Empleados();
            
            
            
            // Creamos un nuevo empleado en la 1a posición del array
            personal.usuario = newUser;
            personal.contraseña = newPasswd;
            personal.rol = newRol;
            
            // Con writeObject escribimos directamente el empleado
            fichero.writeObject(personal);

            // Con writeObject escribimos directamente todo el array de Empleados
            fichero.writeObject(personal);

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al guardar el fichero");
        }
    }

    // Funcion para leer todos los usuarios de la base de datos
    private static void listarUsuarios(File archivoUsers) {
        try {
            // A partir de aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(archivoUsers));

            // Creamos un nuevo array de Empleados
            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            Empleados[] personal_recuperado = (Empleados[]) fichero.readObject();

            // Recorremos todo el array de Empleados
            for (Empleados empleado : personal_recuperado) {
                // Tenemos en cuenta que algunas posiciones del array valen null
                // En ese caso no leas la información del empleado
                if (empleado != null) {
                    System.out.println("USUARIO: " + empleado.usuario);
                    System.out.println("CONTRASEÑA: " + empleado.contraseña);
                    System.out.println("ROL: " + empleado.rol);
                    System.out.println("--------------------");
                }
            }

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer el fichero");
        }
    }
    
    // Funcion para leer todos los usuarios de la base de datos
    private static void modificarPasswdYRol() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Funcion para leer todos los usuarios de la base de datos
    private static void eliminarUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
