package orchestrators;

import entities.Bitacora;
import entities.Cliente;
import services.BitacoraService;
import services.ClienteService;
import services.ProformaService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class OrchestratorCompra {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static PrintStream out = System.out;
    static ClienteService clienteService;
    static BitacoraService bitacoraService;
    static ProformaService proformaService;

    public static void main(String args[]) throws Exception {
        int opcion = -1;
        do {
            opcion = mostrarMenu();
            procesarOpcion(opcion);
        } while (opcion != 0);

    }

    public static void procesarOpcion(int pOpcion) throws Exception {
        switch (pOpcion) {
            case 1:
                Cliente tmpCliente = registrarCliente();
                System.out.println("==============VERIFICACION DE COMPRA===================== \n");
                System.out.println("¿DESEA REALIZAR UNA COMPRA?");

                out.println("1. NO COMPRAR");
                out.println("2. PEDIR PROFORMA");
                int opcion1 = Integer.parseInt(in.readLine());

                if (opcion1 == 1) {
                    compraRechazada(tmpCliente);
                }

                if (opcion1 == 2) {
                    System.out.println("SU PROFORMA ESTÁ SIENDO CREADA... /n");
                    solicitarProforma(tmpCliente);

                    System.out.println("\n==============VERIFICACION DE COMPRA===================== \n");
                    System.out.println("¿DESEA REALIZAR LA COMPRA?");
                    out.println("1. NO COMPRAR");
                    out.println("2. ESPERAR");
                    out.println("3. COMPRAR");

                    int opcion2 = Integer.parseInt(in.readLine());

                    if (opcion2 == 1) {
                        compraRechazada(tmpCliente);
                    } else if (opcion2 == 2){
                        esperarCompra(tmpCliente);


                    } else {
                        System.out.println("\n==============VERIFICACION DE COMPRA===================== \n");
                        System.out.println("¿DESEA REALIZAR EL PAGO DE LA COMPRA?");
                        out.println("1. HACER PAGO LUEGO");
                        out.println("2. REALIZAR PAGO");

                        int opcion3 = Integer.parseInt(in.readLine());
                        if (opcion3 == 1) {
                            pagoPendiente(tmpCliente);
                        } else {
                            compraPagada(tmpCliente);
                        }

                    }
                }
                break;
            case 2:

                break;
            case 0:
                out.println("Fin del programa !!");
                break;
            default:
                out.println("Opcion inválida");
        }
    }

    static int mostrarMenu() throws java.io.IOException {
        int opcion;
        opcion = 0;
        out.println("1. Registrar Cliente");
        out.println("0. Salir del sistema");
        out.println("Digite la opción");
        opcion = Integer.parseInt(in.readLine());
        return opcion;
    }

    public static Cliente registrarCliente() throws Exception {
        clienteService = new ClienteService();

        Date followup= new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");
        Date creado = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");
        Date modificado = new SimpleDateFormat("dd/MM/yyyy").parse("10/03/2022");

        Cliente cliente = new Cliente("Isabel", "Galeano", "NUEVO", "84511935",followup, creado, modificado);


        return clienteService.crearCliente(cliente);
    }

    public static void crearBitacora(Cliente cliente, String vFecha, String estado) throws Exception {
        bitacoraService = new BitacoraService();
        Date fecha= new SimpleDateFormat("dd/MM/yyyy").parse(vFecha);
        Bitacora bitacora = new Bitacora(cliente, estado, fecha);
        bitacoraService.crearBitacora(bitacora);

    }

    public static void compraRechazada(Cliente cliente) throws Exception {
        crearBitacora(cliente, "11/03/2022","NO_COMPRAR");
        System.out.println("USTED HA RECHAZADO LA COMPRA... /n");

    }

    public static void solicitarProforma(Cliente cliente) throws Exception {
        crearBitacora(cliente, "12/03/2022","SEGUIMIENTO");
        System.out.println("\n============== PROFORMA ===================== \n");
        System.out.println(crearProforma(cliente));
    }

    public static String crearProforma(Cliente cliente) throws Exception {
        proformaService = new ProformaService();
        return proformaService.enviarProforma(cliente);

    }

    public static void esperarCompra(Cliente cliente) throws Exception {
        crearBitacora(cliente, "13/03/2022","PENDIENTE");
        System.out.println("SU COMPRA SE ENCUENTRA EN ESTADO PENDIENTE... /n");

    }

    public static void pagoPendiente(Cliente cliente) throws Exception {
        crearBitacora(cliente, "14/03/2022","PAGO_PENDIENTE");
        System.out.println("SU PAGO SE ENCUENTRA EN ESTADO PENDIENTE... /n");

    }

    public static void compraPagada(Cliente cliente) throws Exception {
        crearBitacora(cliente, "14/03/2022","COMPRÓ");
        System.out.println("SU PAGO COMPRA HA SIDO REALIZAD0 CON EXITO... /n");

    }

}