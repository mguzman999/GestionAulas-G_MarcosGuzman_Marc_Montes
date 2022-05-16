//marcos guzman
//marc montes
//projecte final de taules MP03
package projecte_taules_marcos_marc;

//importaciones del programa
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Projecte_taules_marcos_marc {

    public static void main(String[] args) {

        //llamamos a la funcion de todo lo que puede hacer el usuari cambrer
        MenuCambrer();

    }

    //funcion que imprime el menu y gestiona la eleccion del usuario
    private static void MenuCambrer() {
        Scanner sc = new Scanner(System.in);

        //variable seleccion usuario, ruta de fichero, funcion fichero si no existe, variable salida menu
        int opcion;
        File fichero = new File("C:/Users/alumne/Desktop/projecte_m03/taules.txt");
        crearFichero(fichero);
        boolean salir = false;

        //bucle eleccion usuario
        do {

            System.out.println("\nBIENVENIDO AL MENU DEL CAMARERO\n");

            System.out.println("***********************************\n");

            System.out.println(" (1) - Listar todas las mesas");
            System.out.println(" (2) - Dar de alta una mesa");
            System.out.println(" (3) - Modificar/Editar una mesa");
            System.out.println(" (4) - Borrar una mesa");
            System.out.println(" (5) - Salir del menu");

            System.out.print("\nSelecciona una opcion: ");
            opcion = sc.nextInt();

            //condicion sobre eleccion usuario, llamadas a las funciones especificas
            if (opcion == 1) {
                listarMesa(fichero);
            } else if (opcion == 2) {
                darAltaMesa(fichero);
            } else if (opcion == 3) {
                modificarMesa(fichero);
            } else if (opcion == 4) {
                eliminarMesa(fichero);
            } else if (opcion == 5) {
                salir = true;
                System.out.println("SALIENDO DEL MENU...");
            } else {
                System.out.println("ERROR NUMERO INCORRECTO");
            }

            //condicion salida del bucle
        } while (opcion < 1 || opcion > 5 || !salir);

    }

    //funcion menu admin (en pausa)
    private static void MenuAdmin() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nBIENVENIDO AL MENU DEL ADMIN\n");

        System.out.println("***********************************\n");

        System.out.println(" (1) - Alta de los usuarios");
        System.out.println(" (2) - Listar todos los usuarios de la base de datos");
        System.out.println(" (3) - Modificar la contraseña y el rol");
        System.out.println(" (4) - Eliminar un usuario");
        System.out.println(" (5) - Salir del menu");
    }

    //funcion, devuelve la lista linea a linea del fichero + condicion
    private static void listarMesa(File fichero) {
        try {
            // Codificación ISO-8859-1 (ANSI) o UTF-8 dependiendo de cómo esté creado el fichero de texto
            Scanner lectorFichero = new Scanner(fichero, "ISO-8859-1");
            System.out.println("");
            while (lectorFichero.hasNext()) {

                System.out.println(lectorFichero.nextLine());
            }

            lectorFichero.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al abrir/leer el fichero");
        }
    }

    //    Funcion para crear y escribir dentro del fichero nuevo, si es el caso que no existe, lo cargaremos con mesas precargadas, le pasamos el fichero
    private static void crearFichero(File fichero) {
        try {
            FileWriter writer = new FileWriter(fichero);

            //se crea el archivo en la ruta especifica del objeto fichero con mesas precargadas
            writer.write("t_01;interior;10;si;7;si;si;\n");
            writer.write("t_02;exterior;20;no;20;no;si;\n");
            writer.write("t_03;interior;6;si;7;si;no;\n");
            writer.write("t_04;exterior;12;no;12;no;si;\n");
            writer.write("t_05;interior;6;si;7;si;no;\n");
            writer.write("t_06;exterior;3;no;3;no;no;\n");
            writer.write("t_07;interior;15;si;7;si;si;\n");
            writer.write("t_08;exterior;12;no;12;no;si;\n");
            writer.write("t_09;interior;19;si;7;si;no;\n");
            writer.write("t_10;exterior;5;no;5;si;si;\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }

    //funcion para añadir una nueva linea al fichero (al final del archivo), le pasamos el objeto fichero
    private static void darAltaMesa(File fichero) {
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
            FileWriter writer = new FileWriter(fichero, true);

            //escribimos en el fichero con el formato establecido por (;)
            writer.write(idMesa + ";" + descMesa + ";" + cantMesa + ";" + peqMesa + ";" + sillMesa + ";" + ventMesa + ";" + jardMesa + ";\n");

            writer.close();
        } catch (Exception e) {
            System.out.println("Ha ocurrido un error al crear/escribir en el fichero");
        }
    }

    //funcion para modificar lineas del fichero, le pasamos el objeto fichero
    private static void modificarMesa(File fichero) {
        Scanner sc = new Scanner(System.in);
        // Array para guardar todas las líneas leídas del fichero
        ArrayList<String> lineas = new ArrayList<>();

        // Abrimos el fichero de texto para leerlo en memoria
        try {
            Scanner lectorFichero = new Scanner(fichero);

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
            FileWriter writer = new FileWriter(fichero);

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

    //funcion para eliminar lineas del fichero
    //seguimos el mismo algoritmo de modificar una linea para eliminarla
    private static void eliminarMesa(File fichero) {
        Scanner sc = new Scanner(System.in);
        // Array para guardar todas las líneas leídas del fichero
        ArrayList<String> lineas = new ArrayList<>();

        // Abrimos el fichero de texto para leerlo en memoria
        //solicitamos el id de la mesa al usuario
        System.out.print("INTRODUCE EL ID DE LA MESA QUE QUIERES ELIMINAR: ");
        String idmesa = sc.next();
        try {
            Scanner lectorFichero = new Scanner(fichero);

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
            FileWriter writer = new FileWriter(fichero);

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

}
