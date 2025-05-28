package jugadores.cliente;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in).useDelimiter("\n");
        boolean salir = false;
        int opcion;

        while (!salir) {
            System.out.println("");
            System.out.println("####### MENU #######");
            System.out.println("");
            System.out.println("1. Consultar jugador");
            System.out.println("2. Insertar jugador");
            System.out.println("3. Incrementar goles de jugador");
            System.out.println("4. Borrar jugador");
            System.out.println("5. Salir");
            try {
                ClienteJugadores clienteEmpleados = new ClienteJugadores();
                System.out.println("Escribe una de las opciones anteriores:");
                String ficha;
                opcion = sn.nextInt();
                switch (opcion) {
                    case 1:
                        ficha = menuFichaJugador(sn);
                        clienteEmpleados.consultarJugador(ficha);
                        break;
                    case 2:
                        ficha = menuFichaJugador(sn);
                        String equipo = menuEquipo(sn);
                        String agente = menuAgente(sn); 
                        System.out.println("Introduce el apodo del jugador:");
                        String apodo = sn.next();
                        String posicion = menuPosicion(sn); 
                        clienteEmpleados.insertarJugador(ficha, equipo, agente, apodo, posicion);
                        break;
                    case 3:
                        ficha = menuFichaJugador(sn);
                        clienteEmpleados.incrementaGolesAjugador(ficha);
                        break;
                    case 4:
                        ficha = menuFichaJugador(sn);
                        clienteEmpleados.borrarJugador(ficha);
                        break;
                    case 5:
                        salir = true;
                        break;
                    default:
                        System.out.println("Selecciona un opción correcta:");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error en menú: " + e.getMessage());
                salir = true;
            } catch (Exception eg) {
                System.out.println("Error generico en menú: " + eg.getMessage());
                salir = true;
            }
        }
    }
    
    private static String menuFichaJugador(Scanner sn) {
        System.out.println("");
        System.out.println("Introduce la ficha del jugador (maximo 6 caracteres alfanumericos):");
        String ficha;
        boolean ok = false;
        do {
            ficha = sn.next();
            if(ficha!=null && ficha.length()<=6) ok = true;
            else System.out.println("Introduce el dato correctamente:");
        } while (!ok);
        return ficha;
    }

    private static String menuEquipo(Scanner sn) {
        System.out.println("");
        System.out.println("LISTA DE EQUIPOS:");
        System.out.println("1. F.C. BARCELONA");
        System.out.println("2. REAL MADRID");
        System.out.println("3. AT. BILBAO");
        System.out.println("4. MALAGA C.F.");
        System.out.println("5. R. SANTANDER");
        System.out.println("6. U.D. ALMERIA");
        System.out.println("7. Sin Equipo");
        System.out.println("Selecciona una opcion:");
        String equipo = "";
        boolean salir = false;
        while (!salir) {
            int opcion = sn.nextInt();
            switch (opcion) {
                case 1:
                    equipo = "F.C. BARCELONA";
                    salir = true;
                    break;
                case 2:
                    equipo = "REAL MADRID";
                    salir = true;
                    break;
                case 3:
                    equipo = "AT. BILBAO";
                    salir = true;
                    break;
                case 4:
                    equipo = "MALAGA C.F.";
                    salir = true;
                    break;
                case 5:
                    equipo = "R. SANTANDER";
                    salir = true;
                    break;
                case 6:
                    equipo = "U.D. ALMERIA";
                    salir = true;
                    break;
                case 7:
                    equipo = null;
                    salir = true;
                    break;
                default:
                    System.out.println("Selecciona un opción correcta:");
            }
        }
        return equipo;
    }
    
    private static String menuAgente(Scanner sn) {
        System.out.println("");
        System.out.println("LISTA DE AGENTES:");
        System.out.println("1. JORGE MENDES");
        System.out.println("2. VLADICA LEMIC");
        System.out.println("3. HECTOR PERIS");
        System.out.println("4. Sin Agente");
        System.out.println("Selecciona una opcion:");
        String agente = "";
        boolean salir = false;
        while (!salir) {
            int opcion = sn.nextInt();
            switch (opcion) {
                case 1:
                    agente = "JORGE MENDES";
                    salir = true;
                    break;
                case 2:
                    agente = "VLADICA LEMIC";
                    salir = true;
                    break;
                case 3:
                    agente = "HECTOR PERIS";
                    salir = true;
                    break;
                case 4:
                    agente = null;
                    salir = true;
                    break;
                default:
                    System.out.println("Selecciona un opción correcta:");
            }
        }
        return agente;
    }
    
    private static String menuPosicion(Scanner sn) {
        System.out.println("");
        System.out.println("LISTA DE POSICIONES:");
        System.out.println("1. Portero");
        System.out.println("2. Defensa");
        System.out.println("3. Centrocampista");
        System.out.println("4. Delantero");
        System.out.println("Selecciona una opcion:");
        String posicion = "";
        boolean salir = false;
        while (!salir) {
            int opcion = sn.nextInt();
            switch (opcion) {
                case 1:
                    posicion = "PT";
                    salir = true;
                    break;
                case 2:
                    posicion = "DF";
                    salir = true;
                    break;
                case 3:
                    posicion = "MC";
                    salir = true;
                    break;
                case 4:
                    posicion = "DL";
                    salir = true;
                    break;
                default:
                    System.out.println("Selecciona un opción correcta:");
            }
        }
        return posicion;
    }

}
