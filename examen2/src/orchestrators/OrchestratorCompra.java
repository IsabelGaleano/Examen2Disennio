package orchestrators;

import entities.Bitacora;
import entities.Cliente;
import entities.TipoReporte;
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
                System.out.println("==============REPORTES===================== \n");
                System.out.println("¿QUÉ REPORTE DESEA CONOCER?");

                out.println("1. DIAS_ENTRE_ESTADO_NUEVO_Y_COMPRA");
                out.println("2. DIAS_PROMEDIO_CANCELANDO");
                out.println("3. PENDIENTES_Y_DIAS");
                out.println("4. TIEMPO_SEGUIMIENTO_Y_POSTERIORES");
                int opcionReporte = Integer.parseInt(in.readLine());
                int idCliente;
                if (opcionReporte == 1) {
                    out.println("INGRESE EL ID DEL CLIENTE");
                    idCliente = Integer.parseInt(in.readLine());
                    out.println(retornarReporte(idCliente, TipoReporte.DIAS_ENTRE_NUEVO_Y_COMPRA));
                }else if (opcionReporte == 2) {
                    out.println("INGRESE EL ID DEL CLIENTE");
                    idCliente = Integer.parseInt(in.readLine());
                    out.println(retornarReporte(idCliente, TipoReporte.DIAS_PROMEDIO_CANCELANDO));
                }else if (opcionReporte == 3) {
                    out.println(retornarReporte(0, TipoReporte.PENDIENTES_Y_DIAS));
                }
                else if (opcionReporte == 4) {
                    out.println(retornarReporte(0, TipoReporte.TIEMPO_SEGUIMIENTO_Y_POSTERIORES));
                }
                break;
            case 3:
                defaultSalesData();
                break;
            case 4:
                out.println(mostarListadoCLientes());
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
        out.println("2. Reportes");
        out.println("3. Cargar datos por defecto");
        out.println("4. Ver lista de clientes");
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

    public static void defaultSalesData() throws Exception{
        clienteService = new ClienteService();

        Date followup= new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022");
        Date creado = new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022");
        Date modificado = new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022");


        Cliente Cliente1 = new Cliente("Stepahnny", "Mata", "NUEVO", "85656565",new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"));
        Cliente1 = clienteService.crearCliente(Cliente1);
        crearBitacora(Cliente1,"15/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente1,"15/04/2022","PENDIENTE");

        Cliente Cliente2 = new Cliente("Jose", "Calvo", "NUEVO", "57631598",new SimpleDateFormat("dd/MM/yyyy").parse("12/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("12/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("12/04/2022"));
        Cliente2 = clienteService.crearCliente(Cliente2);
        crearBitacora(Cliente2,"12/04/2022","NO_COMPRAR");

        Cliente Cliente3 = new Cliente("Manuel", "Ramirez", "NUEVO", "54934678",new SimpleDateFormat("dd/MM/yyyy").parse("13/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("13/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("13/04/2022"));
        Cliente3 = clienteService.crearCliente(Cliente3);
        crearBitacora(Cliente3,"19/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente3,"20/04/2022","NO_COMPRAR");

        Cliente Cliente4 =  new Cliente("Maria", "Arias", "NUEVO", "94761358",new SimpleDateFormat("dd/MM/yyyy").parse("16/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("16/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("16/04/2022"));
        Cliente4 = clienteService.crearCliente(Cliente4);
        crearBitacora(Cliente4,"21/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente4,"21/04/2022","COMPRÓ");

        Cliente Cliente5 =   new Cliente("Andres", "Martinez", "NUEVO", "91346820",new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("20/04/2022"));
        Cliente5 = clienteService.crearCliente(Cliente5);
        crearBitacora(Cliente5,"25/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente5,"25/04/2022","PAGO_PENDIENTE");

        Cliente Cliente6 =   new Cliente("Carlos", "Salinas", "NUEVO", "84361489",new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"));
        Cliente6 = clienteService.crearCliente(Cliente6);
        crearBitacora(Cliente6,"15/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente6,"15/04/2022","PAGO_PENDIENTE");
        crearBitacora(Cliente6,"16/04/2022","COMPRÓ");

        Cliente Cliente7 =   new Cliente("Gabrila", "Vindas", "NUEVO", "64850516",new SimpleDateFormat("dd/MM/yyyy").parse("17/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("17/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("17/04/2022"));
        Cliente7 =  clienteService.crearCliente(Cliente7);
        crearBitacora(Cliente7,"21/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente7,"21/04/2022","COMPRÓ");

        Cliente Cliente8 =   new Cliente("Manuel", "Morales", "NUEVO", "84937510",new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"));
        Cliente8 =  clienteService.crearCliente(Cliente8);
        crearBitacora(Cliente8,"16/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente8,"16/04/2022","NO_COMPRAR");

        Cliente Cliente9 =   new Cliente("Karla", "Castro", "NUEVO", "34891564",new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"));
        Cliente9 =  clienteService.crearCliente(Cliente9);
        crearBitacora(Cliente9,"18/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente9,"19/04/2022","PENDIENTE");

        Cliente Cliente10 =   new Cliente("Karina", "Roman", "NUEVO", "79341825",new SimpleDateFormat("dd/MM/yyyy").parse("18/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("18/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("18/04/2022"));
        Cliente10 = clienteService.crearCliente(Cliente10);
        crearBitacora(Cliente10,"23/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente10,"23/04/2022","PENDIENTE");

        Cliente Cliente11 =   new Cliente("Daniela", "Zamora", "NUEVO", "34891567",new SimpleDateFormat("dd/MM/yyyy").parse("19/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("19/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("19/04/2022"));
        Cliente11 = clienteService.crearCliente(Cliente11);
        crearBitacora(Cliente11,"19/04/2022","NO_COMPRAR");

        Cliente Cliente12 =   new Cliente("Daniel", "Seas", "NUEVO", "97641582",new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"), new SimpleDateFormat("dd/MM/yyyy").parse("10/04/2022"));
        Cliente12 = clienteService.crearCliente(Cliente12);

        crearBitacora(Cliente12,"18/04/2022","SEGUIMIENTO");
        crearBitacora(Cliente12,"26/04/2022","COMPRÓ");

    }

    public static String mostarListadoCLientes() throws Exception{
        clienteService = new ClienteService();
        return clienteService.getClientes();
    }

    public static void crearBitacora(Cliente cliente, String vFecha, String estado) throws Exception {
        bitacoraService = new BitacoraService();
        Date fecha= new SimpleDateFormat("dd/MM/yyyy").parse(vFecha);
        Bitacora bitacora = new Bitacora(cliente, estado, fecha);
        bitacoraService.crearBitacora(bitacora);

    }

    public static String retornarReporte(int clienteId, TipoReporte tipoReporte) throws Exception {
        bitacoraService = new BitacoraService();
        return bitacoraService.retornarReporte(clienteId, tipoReporte);
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
