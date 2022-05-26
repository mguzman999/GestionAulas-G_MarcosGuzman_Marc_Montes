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
        // Verificamos si el archivo existe, si no llamamos a la funcion que lo crea
        // Creamos un objeto de tipo file para asignarle un archivo
        File archivoUsers = new File("C:/Users/alumne/Desktop/projecte_m03/users.dat");
        File ficheroMesas = new File("C:/Users/alumne/Desktop/projecte_m03/taules.txt");

        // Mediante la funcion isFile() comprobamos si existe o no los archvios, si 
        // no existen se llaman a las respectivas funciones para crearlas
        if (!archivoUsers.isFile()) {
            crearArchivoUsuarios(archivoUsers);
        }
        if (!ficheroMesas.isFile()) {
            crearFicheroMesas(ficheroMesas);
        }

        // Llamamos a la funcion del MenuLogin
        MenuLogin(archivoUsers, ficheroMesas);

    }

    /* PARTE MENU LOGIN DE LOS USUARIOS*/
    
    // Funcion que sera el menu login para todos los usuarios
    private static void MenuLogin(File archivoUsers, File ficheroMesas) {
        Scanner sc = new Scanner(System.in);
        
        // Declaramos las variables que usaremos en esta funcion
        int opcion;
        boolean salir = false;
        boolean aux = false;
        int intentos = 3;
        
        do {
            System.out.println("\nBIENVENIDO AL LOGIN DEL USUARIO\n");

            System.out.println("***********************************\n");

            System.out.println(" (1) - Entrar al menu login");
            System.out.println(" (2) - Salir");
            System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    // Pedimos los datos de login del usuario
                    System.out.print("Introduzca su usuario: ");
                    String usuario = sc.next();
                    System.out.print("Introduzca su contraseña: ");
                    String passwd = sc.next();
                    
                    // Guardamos en una variable aux lo que devuelve la funcion de login
                    aux = loginUsuarios(archivoUsers, ficheroMesas, usuario, passwd);
                    if (!aux) { // si es falso se descuentan intentos
                        intentos--;
                        System.out.println("INTENTO FALLIDO DE LOGEO EN EL SISTEMA, TE QUEDAN: " + intentos + " INTENTOS");
                        if (intentos == 0) { // si los intentos llegan a 0 se cambiar el valor de la variable salir a true y se sale del bucle do while padre terminando el programa
                            System.out.println("INTENTOS VENCIDOS, LLAMANDO A SEGURIDAD...");
                            salir = true;
                        }
                    } else{ // si el usuario termina iniciando sesion correctamente al salir de su sesion se le reinician los intentos otra vez.
                        intentos = 3;
                    }
                    break;
                case 2:
                    salir = true;
                    System.out.println("Saliendo del programa... ");
                    break;
                default:
                    System.out.println("Has introducido una opcion incorrecta.");
                    break;
            }

            // Condicion salida del bucle
        } while (!salir);
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
            switch (opcion) {
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

    // Funcion para crear y escribir dentro del fichero nuevo, si es el caso que no existe, lo cargaremos con una linea en blanco
    private static void crearFicheroMesas(File ficheroMesas) {
        try {
            FileWriter writer = new FileWriter(ficheroMesas);
            //Se crea el fichero de mesas con una linea vacia
            writer.write("");

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
            // mientras el fichero contenga una linea siguiente este imprimira el contenido de esta linea
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
        boolean opc = false;
        
        //pedimos los datos de la mesa al usuario
        System.out.print("INTRODUCE EL ID DE LA MESA: ");
        String idMesa = sc.next();

        System.out.print("INTRODUCE LA DESCRIPCION DE LA MESA: ");
        String descMesa = sc.next();

        System.out.print("INTRODUCE LA CAPACIDAD MAXIMA DE PERSONAS DE LA MESA: ");
        String cantMesa = sc.next();

        String peqMesa;
        do { // Realizamos pruebas de errores que solo se pueda introducir si o no
            System.out.print("INTRODUCE (SI - NO) SI HAY SILLAS PARA LOS PEQUES: ");
            peqMesa = sc.next();
            if (peqMesa.equalsIgnoreCase("si") || peqMesa.equalsIgnoreCase("no")) {
                opc = true;
            } else {
                System.out.println("TEXTO INCORRECTO INTRODUCE (SI) O (NO)");
            }
        } while (!opc);

        System.out.print("INTRODUCE EL NUMERO DE SILLAS DE ADULTOS DE LAS MESA: ");
        String sillMesa = sc.next();

        opc = false;
        String ventMesa;
        do { // Realizamos pruebas de errores que solo se pueda introducir si o no
            System.out.print("INTRODUCE (SI - NO) SI HAY VENTILADOR EN LA MESA: ");
            ventMesa = sc.next();
            if (ventMesa.equalsIgnoreCase("si") || ventMesa.equalsIgnoreCase("no")) {
                opc = true;
            } else {
                System.out.println("TEXTO INCORRECTO INTRODUCE (SI) O (NO)");
            }
        } while (!opc);

        opc = false;
        String jardMesa;
        do { // Realizamos pruebas de errores que solo se pueda introducir si o no
            System.out.print("INTRODUCE (SI - NO) SI LA MESA ESTA EN EL JARDIN: ");
            jardMesa = sc.next();
            if (jardMesa.equalsIgnoreCase("si") || jardMesa.equalsIgnoreCase("no")) {
                opc = true;
            } else {
                System.out.println("TEXTO INCORRECTO INTRODUCE (SI) O (NO)");
            }
        } while (!opc);

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
            // mientras el fichero que le pasamos tenga lineas siguientes se añaden al arraylist de lineas
            while (lectorFichero.hasNext()) {
                lineas.add(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }

        // Pediremos los datos de la mesa que queremos modificar
        System.out.print("INTRODUCE EL ID DE LA MESA QUE QUIERES MODIFICAR: ");
        String idMesa = sc.next();
        
        for (String linea : lineas) {
            //utilizaremos una funcion de java para separa por posiciones las lineas
            String frase = linea;
            String[] parts = frase.split(";");
            //dependiedo de las posiciones del array creado sabemos que campos consultar/modificar
            if (parts[0].equalsIgnoreCase(idMesa)){
                boolean salir = false;
                int opcion;
                do { // mostramos las opciones de la mesa que el usuario puede modificar
                    System.out.println("INTRODUCE EL CAMPO QUE QUIERES MODIFICAR DE LA MESA: ");
                    System.out.println("1 - ID DE LA MESA (NO DISPONIBLE POR EL MOMENTO - SOLO ADMIN)");
                    System.out.println("2 - DESCRIPCION DE LA MESA");
                    System.out.println("3 - CAPACIDAD MAXIMA DE LA MESA");
                    System.out.println("4 - EXISTEN SILLAS ADAPTADAS PARA LOS NIÑOS PEQUEÑOS EN LA MESA");
                    System.out.println("5 - NUMERO DE SILLAS DE LA MESA");
                    System.out.println("6 - DISPONIBILIDAD DE VENTILADOR DE LA MESA");
                    System.out.println("7 - UBICACION DE LA MESA");

                    System.out.print("SELECCIONA UNA OPCION: ");
                    opcion = sc.nextInt();

                    switch (opcion) { // segun la opcion que escoga el usuario se modificara una cosa u otra
                        case 1:
                            System.out.println("NO DISPONIBLE POR EL MOMENTO CONTACTA CON EL ADMINISTRADOR");
                            break;
                        case 2:
                            System.out.print("INTRODUCE LA NUEVA DESCRIPCION DE LA MESA: ");
                            String descMesa = sc.next();
                            modMesa(ficheroMesas, descMesa, idMesa, lineas, 1);
                            break;
                        case 3:
                            System.out.print("INTRODUCE LA NUEVA CAPACIDAD MAXIMA DE LA MESA: ");
                            String capMax = sc.next();
                            modMesa(ficheroMesas, capMax, idMesa, lineas, 2);
                            break;
                        case 4:
                            boolean opc = false;
                            String sillasPeques;
                            do { // realizamos pruebas de errores para que el usuario no escriba algo que no debe
                                System.out.print("EXISTEN SILLAS ADAPTADAS PARA LOS NIÑOS PEQUEÑOS EN LA MESA? ");
                                sillasPeques = sc.next();
                                if (sillasPeques.equalsIgnoreCase("si") || sillasPeques.equalsIgnoreCase("no")) {
                                    opc = true;
                                } else {
                                    System.out.println("TEXTO INCORRECTO INTRODUCE (SI) O (NO)");
                                }
                            } while (!opc);
                            modMesa(ficheroMesas, sillasPeques, idMesa, lineas, 3);
                            break;
                        case 5:
                            System.out.print("INTRODUCE EL NUEVO NUMERO DE SILLAS DE LA MESA: ");
                            String sillas = sc.next();
                            modMesa(ficheroMesas, sillas, idMesa, lineas, 4);
                            break;
                        case 6:
                            opc = false;
                            String vent;
                            do { // realizamos pruebas de errores para que el usuario no escriba algo que no debe
                                System.out.print("EXISTEN VENTILADORES EN LA MESA? ");
                                vent = sc.next();
                                if (vent.equalsIgnoreCase("si") || vent.equalsIgnoreCase("no")) {
                                    opc = true;
                                } else {
                                    System.out.println("TEXTO INCORRECTO INTRODUCE (SI) O (NO)");
                                }
                            } while (!opc);
                            modMesa(ficheroMesas, vent, idMesa, lineas, 5);
                            break;
                        case 7:
                            opc = false;
                            String jardin;
                            do { // realizamos pruebas de errores para que el usuario no escriba algo que no debe
                                System.out.print("INTRODUCE LA UBICACION DE LA MESA: ");
                                jardin = sc.next();
                                if (jardin.equalsIgnoreCase("INTERIOR") || jardin.equalsIgnoreCase("EXTERIOR")) {
                                    opc = true;
                                } else {
                                    System.out.println("TEXTO INCORRECTO INTRODUCE (INTERIOR) O (EXTERIOR)");
                                }
                            } while (!opc);
                            modMesa(ficheroMesas, jardin, idMesa, lineas, 6);
                            break;
                        default:
                            System.out.println("ERROR NUMERO INCORRECTO");
                            break;
                    }
                }while (opcion < 2 && opcion > 7 && !salir);
            }else System.out.println("EL ID DE LA MESA NO EXISTE");
        }
    }
    
    // Funcion para ahorrar lineas en la funcion de modificarMesa
    private static void modMesa(File ficheroMesas, String datosMesas, String idMesa, ArrayList<String> lineas, int aux) {
        try {
            FileWriter writer = new FileWriter(ficheroMesas);

            for (String linea : lineas) {

                //utilizaremos una funcion de java para separa por posiciones las lineas
                String frase = linea;
                String[] parts = frase.split(";");

                //dependiedo de las posiciones del array creado sabemos que campos consultar/modificar
                if (parts[0].equalsIgnoreCase(idMesa)) {

                    parts[aux] = datosMesas.toLowerCase();

                    //damos formato a la frase
                    String fraseformat = parts[0] + ";" + parts[1] + ";" + parts[2] + ";" + parts[3] + ";" + parts[4] + ";" + parts[5] + ";" + parts[6] + "; \n";

                    //escribimos la nueva linea en el archivo fichero
                    writer.write(fraseformat);
                    System.out.println("LA MESA HA SIDO MODIFICADA \n");

                    // Mostramos como seran los nuevos datos de la mesa
                    System.out.println("");
                    System.out.println("EL ID DE LA MESA ES: " + parts[0]);
                    System.out.println("LA DESCRIPCION DE LA MESA ES: " + parts[1]);
                    System.out.println("LA CAPACIDAD MAXIMA DE LA MESA ES DE: " + parts[2]);
                    System.out.println("EXISTEN SILLAS ADAPTADAS PARA LOS PEQUES? " + parts[3]);
                    System.out.println("NUMERO DE SILLAS QUE DISPONE LA MESA: " + parts[4]);
                    System.out.println("HAY VENTILADOR EN LA MESA? " + parts[5]);
                    System.out.println("LA MESA ES DE EXTERIOR O INTERIOR? " + parts[6]);
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
        String idMesa = sc.next();
        try {
            Scanner lectorFichero = new Scanner(ficheroMesas);

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

            // Realizamos un bucle for each para recorrer todo el arraylist de lineas
            for (String linea : lineas) {

                String frase = linea;
                String[] parts = frase.split(";");

                //mietras no cumpla la condicion, escribiremos las lineas en el fichero
                if (!parts[0].equalsIgnoreCase(idMesa)) {
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

        do { // realizamos un bucle do while para el menu del admin en el que el admin escogera una opcion de la funcion que quiera realizar
            System.out.println("\nBIENVENIDO AL MENU DEL ADMIN\n");

            System.out.println("***********************************\n");

            System.out.println(" (1) - Alta de los usuarios");
            System.out.println(" (2) - Listar todos los usuarios de la base de datos");
            System.out.println(" (3) - Modificar la contraseña y el rol");
            System.out.println(" (4) - Eliminar un usuario");
            System.out.println(" (5) - Salir del menu de admin");

            System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();

            switch (opcion) { // dependiendo la opcion que escoga el administrador se llama a una funcion u otra
                case 1:
                    altaUsuarios(archivoUsers);
                    break;
                case 2:
                    listarUsuarios(archivoUsers);
                    break;
                case 3:
                    modificarPasswdYRol(archivoUsers);
                    break;
                case 4:
                    eliminarUsuario(archivoUsers);
                    break;
                case 5: // si selecciona la opcion 5 que es salir, se saldra del menu del admin ya que cambia el valor del booleano salir a true
                    salir = true;
                    System.out.println("SALIENDO DEL MENU DE ADMIN...");
                    break;
                default:
                    System.out.println("ERROR NUMERO INCORRECTO");
                    break;
            }
        } while (opcion < 1 || opcion > 5 || !salir);
    }

    // Funcion para crear y escribir dentro del fichero nuevo, si es el caso que no existe, lo cargaremos con usuarios precargadas
    private static void crearArchivoUsuarios(File archivoUsers) {
        //crearemos el nuevo archivo con datos ya precargados, el del admin y nosotros, LOS PROGRAMADORES!!!   :)
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers));

            // Creamos un nuevo array de Empleados
            // Por defecto todas las posiciones del array valen null
            Empleados[] personal = new Empleados[100];

            // Creamos un nuevo empleado en la 1a posición del array
            personal[0] = new Empleados();
            personal[0].usuario = "admin";
            personal[0].contraseña = "root";
            personal[0].rol = "admin";

            // Creamos un nuevo empleado en la 2a posición del array
            personal[1] = new Empleados();
            personal[1].usuario = "marc";
            personal[1].contraseña = "marc";
            personal[1].rol = "cambrer";

            // Creamos un nuevo empleado en 3a 1a posición del array
            personal[2] = new Empleados();
            personal[2].usuario = "marcos";
            personal[2].contraseña = "marcos";
            personal[2].rol = "cambrer";

            // Con writeObject escribimos directamente todo el array de Empleados
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

        boolean opc = false;
        Empleados[] personal = new Empleados[100];
        
        // Cargamos el fichero de usuario en memoria (en un array de empleados)
        try {
            // A partir de aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(archivoUsers));

            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            personal = (Empleados[]) fichero.readObject();

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer el fichero");
        }

        // Recorremos el bucle con un for each en el que averiguaremos la posicion final del ultimo empleado
        int  i = 0;
        for (Empleados empleado : personal){
            if(empleado == null){
                break;
            } else{
                i++;
            }
        }

        // Guardamos el fichero con los anteriores y nuevos usuarios añadidos
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers,true));

            // Añadimos un nuevo usuario al array de personal, manteniendo los que habia anteriormente
            System.out.print("Introduzca el nombre nuevo del usuario: ");
            String newUser = sc.next();
            System.out.print("Introduzca la contraseña del nuevo usuario: ");
            String newPasswd = sc.next();
            String newRol;
            do {
                System.out.print("Introduzca el rol del nuevo usuario: ");
                newRol = sc.next();
                if (newRol.equalsIgnoreCase("admin") || newRol.equalsIgnoreCase("cambrer")) {
                    opc = true;
                } else {
                    System.out.println("EL ROL SOLO PUEDE SER ADMIN O CAMBRER");
                }
            } while (!opc);

            // Añadimos al array en la ultima posicion del empleado para no sobreescribirlo ni perder empleados anteriores
            personal[i].usuario = newUser;
            personal[i].contraseña = newPasswd;
            personal[i].rol = newRol;
            // Con writeObject escribimos directamente el empleado
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

    // Funcion para modificar la contraseña y rol de un usuario especifico
    private static void modificarPasswdYRol(File archivoUsers) {
        Scanner sc = new Scanner(System.in);
        
        Empleados[] personal = null;
        
        // Cargamos el fichero de usuario en memoria (en un array de empleados)
        try {
            // A partir de aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(archivoUsers));

            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            personal = (Empleados[]) fichero.readObject();

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer el fichero");
        }
        
        // ACTUALIZAMOS LOS DATOS DEL USUARIO
        System.out.print("Introduce el nombre del empleado a actualizar: ");
        String usuarioActualizar = sc.next();
        
        boolean salir = false;
        int opcion;
        
        do { // dependiendo la opcion que escoga el usuario se puede modificar un cambio u otro
            System.out.println("INTRODUCE QUE CAMPO QUIERES MODIFICAR DEL USUARIO: ");
            System.out.println("1 - CONTRASEÑA");
            System.out.println("2 - ROL");
            System.out.print("SELECCIONA UNA OPCION: ");
            opcion = sc.nextInt();
            
            switch(opcion){
                case 1: // cambiamos la contraseña del usuario
                    for (Empleados empleado : personal) {
                        if (empleado != null && empleado.usuario.equalsIgnoreCase(usuarioActualizar)) {
                            System.out.print("Introduzca la nueva contraseña: ");
                            empleado.contraseña = sc.next();
                        }
                    }
                    salir = true;
                    break;
                case 2: // cambiamos el rol del usuario
                    for (Empleados empleado : personal) {
                        if (empleado != null && empleado.usuario.equalsIgnoreCase(usuarioActualizar)) {
                            System.out.print("Introduzca el nuevo rol: ");
                            empleado.rol = sc.next();
                        }
                    }
                    salir = true;
                    break;
                default:
                    System.out.println("NUMERO INCORRECTO!");
                    break;
            }
        } while (opcion < 1 && opcion > 2 && !salir);
        
        // GUARDAR FICHERO
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers));

            // Con writeObject escribimos directamente todo el array de Empleados
            fichero.writeObject(personal);

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al guardar el fichero");
        }
    }

    // Funcion para eliminar un usuario mediante lo que indique el usuario 
    private static void eliminarUsuario(File archivoUsers) {
        // LEER FICHERO EN MEMORIA
        // Creamos la variable que contendrá el array de Empleados en memoria
        Empleados[] personal = null;
        try {
            // A partir de aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(archivoUsers));

            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            personal = (Empleados[]) fichero.readObject();

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer el fichero");
        }
        
        // BORRAR DATOS
        // Buscaremos por la clave primaria o varios campos, en este caso por Nombre y borraremos el registro
        Scanner lector = new Scanner(System.in);
        System.out.print("Introduce el nombre del empleado a despedir: ");
        String nombreBorrar = lector.nextLine();

        for (Empleados empleado : personal) {
            if (empleado != null && empleado.usuario.equalsIgnoreCase(nombreBorrar)) {
                empleado.usuario = "";
                empleado.contraseña = "";
                empleado.rol = "";
                empleado = null;
                System.out.println("Se ha despedido al siguiente empleado sin problemas: " + nombreBorrar);
            }
        }
        
        // GUARDAR FICHERO
        try {
            // A partir de aquí accederemos al fichero a guardar mediante la variable fichero
            ObjectOutputStream fichero = new ObjectOutputStream(new FileOutputStream(archivoUsers));

            // Con writeObject escribimos directamente todo el array de Empleados
            fichero.writeObject(personal);

            // Cerramos el fichero
            fichero.close();

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al guardar el fichero");
        }
    }
    
    // Funcion comprobar que el usuario y contraseña introducidos son correctos, en caso que sean correctos se llama a un menu dependiendo del rol que sea el usuario
    private static boolean loginUsuarios(File archivoUsers, File ficheroMesas, String usuario, String passwd) {
        boolean okey = false;
        
        try {
            // A partir de aquí accederemos al fichero a leer mediante la variable fichero
            ObjectInputStream fichero = new ObjectInputStream(new FileInputStream(archivoUsers));

            // Creamos un nuevo array de Empleados
            // Y rellenamos con lo recuperado de leer el fichero mediante readObject
            // readObject recibe todo un array de Empleados y por eso lo casteamos (Empleado[])
            Empleados[] personal_recuperado = (Empleados[]) fichero.readObject();

            ArrayList<String> users = new ArrayList<>();
            ArrayList<String> pass_user = new ArrayList<>();
            ArrayList<String> rol_user = new ArrayList<>();
            
            for (Empleados empleado : personal_recuperado) {
                if (empleado != null) {
                    users.add(empleado.usuario);
                    pass_user.add(empleado.contraseña);
                    rol_user.add(empleado.rol);
                }
            }
            
            for (int i = 0; i < users.size() && !okey; i++) {
                if (users.get(i).equalsIgnoreCase(usuario)) {
                    System.out.println("USUARIO ENCONTRADO, COMPROBANDO CONTRASEÑA...");
                    if (pass_user.get(i).equalsIgnoreCase(passwd)) {
                        System.out.println("CONTRASEÑA CORRECTA, COMPROBANDO ROL...");
                        if (rol_user.get(i).equalsIgnoreCase("admin")) {
                            System.out.println("ROL CORRECTO PARA EL USUARIO ACCEDIENDO AL SISTEMA");
                            okey = true;
                            MenuAdmin(archivoUsers);
                        } else if (rol_user.get(i).equalsIgnoreCase("cambrer")) {
                            System.out.println("ROL CORRECTO PARA EL USUARIO ACCEDIENDO AL SISTEMA");
                            okey = true;
                            MenuCamarero(ficheroMesas);
                        } else {
                            okey = false;
                        }
                    } else {
                        okey = false;
                    }
                } else {
                    okey = false;
                }
            }

        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al leer el fichero");
        }
        
        return okey;
    }
    
}