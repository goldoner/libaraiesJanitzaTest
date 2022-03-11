import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.AbstractModbusMaster;
import com.ghgande.j2mod.modbus.facade.ModbusSerialMaster;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.net.AbstractSerialConnection;
import com.ghgande.j2mod.modbus.procimg.Register;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import de.re.easymodbus.modbusclient.ModbusClient;
import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.NumericLocator;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {


    public static String ip;
    public static String janitzaName;
    public static Integer port;
    public static Integer baudrate;
    public static Integer register;
    public static Integer dataBits;
    public static String serialInterface;
    private static final Logger log = Logger.getLogger(Main.class);

    public static long samplingInterval = 5000; // = 5000; //[ms]
    public static int ct_ratio_a = 1;
    public static int ct_ratio_b = 1;
    public static int ct_ratio_c = 1;

    public static int vt_ratio_a = 1;
    public static int vt_ratio_b = 1;
    public static int vt_ratio_c = 1;


    public static void main(String[] args) throws InterruptedException, IOException {
        readConfFile("config.properties", "src/main/resources/config.properties");

        while (true) {

            try {

                log.info("Connecting to");

                if (janitzaName.equals("Janitza96")) {
                    log.info("Janitza96 used!");
                    logRegistersOfJanitza96();

                } else if (janitzaName.equals("Janitza503")) {
                    logRegistersOfJanitza503();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            log.info("Waiting 5 seconds");
            Thread.sleep(5000);
        }
    }


    public static void logRegisters(ModbusTCPMaster master) throws ModbusException {

        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== CURRENTS === MEASURED [mA] j2mod library");
        Register[] c = master.readMultipleRegisters(3916, 3);
        System.out.println("3916: " + c[0]);
        System.out.println("3917: " + c[1]);
        System.out.println("3918: " + c[2]);
        System.out.println("=== CURRENTS === MEAN [mA] j2mod library");
        Register[] cm = master.readMultipleRegisters(4346, 3);
        System.out.println("4346: " + cm[0]);
        System.out.println("4347: " + cm[1]);
        System.out.println("4348: " + cm[2]);
        System.out.println("=== VOLTAGE === MEASURED [V] j2mod library");
        Register[] v = master.readMultipleRegisters(3530, 3);
        System.out.println("3530: " + v[0]);
        System.out.println("3531: " + v[1]);
        System.out.println("3532: " + v[2]);
        System.out.println("=== VOLTAGE === MEAN [V] j2mod library");
        Register[] vm = master.readMultipleRegisters(3960, 3);
        System.out.println("3960: " + vm[0]);
        System.out.println("3961: " + vm[1]);
        System.out.println("3962: " + vm[2]);
        System.out.println("=== POWER ACTIVE === MEASURED [W] j2mod library");
        Register[] pa = master.readMultipleRegisters(3920, 3);
        System.out.println("3920: " + pa[0]);
        System.out.println("3921: " + pa[1]);
        System.out.println("3922: " + pa[2]);
        System.out.println("=== POWER ACTIVE === MEAN [W] j2mod library");
        Register[] pam = master.readMultipleRegisters(4350, 3);
        System.out.println("4350: " + pam[0]);
        System.out.println("4351: " + pam[1]);
        System.out.println("4352: " + pam[2]);
        System.out.println("=== POWER REACTIVE === MEASURED [var] j2mod library");
        Register[] pr = master.readMultipleRegisters(3924, 3);
        System.out.println("3924: " + pr[0]);
        System.out.println("3925: " + pr[1]);
        System.out.println("3926: " + pr[2]);
        System.out.println("=== POWER REACTIVE === MEAN [var] j2mod library");
        Register[] prm = master.readMultipleRegisters(4354, 3);
        System.out.println("4354: " + prm[0]);
        System.out.println("4355: " + prm[1]);
        System.out.println("4355: " + prm[2]);

        System.out.println("====================");
    }

    public static void logRegistersOfJanitza96() throws Exception {


        AbstractModbusMaster master = new ModbusTCPMaster(ip); // j2mod
        master.connect(); // j2mod


        ModbusClient modbusClient = new ModbusClient(ip, port); // RossmannEngineering
        modbusClient.Connect(); // RossmannEngineering


        // Mod4j Mod4j Mod4j Mod4j Mod4j Mod4j Mod4j
        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost(ip);
        ipParameters.setPort(port);
        ipParameters.setEncapsulated(false);
        ModbusFactory modbusFactory = new ModbusFactory();
        ModbusMaster modbus4j = modbusFactory.createTcpMaster(ipParameters, false);
        modbus4j.setTimeout(8000);
        modbus4j.setRetries(1);
        modbus4j.init();
        // Mod4j Mod4j Mod4j Mod4j Mod4j Mod4j Mod4j

        log.info("Rossmann Engineering Modbus is connected : " + modbusClient.isConnected());
        log.info("j2mod is connected : " + master.isConnected());
        log.info("mod4j is connected : " + modbus4j.isConnected());


        log.info("=== CURRENTS === MEASURED [mA]");
        int currentsMeasuredRegister = 3916;
        Register[] currentsMeasuredJ2Mod = master.readMultipleRegisters(currentsMeasuredRegister, 3);
        int[] currentsMeasuredRossmannEngineering = modbusClient.ReadHoldingRegisters(currentsMeasuredRegister, 3);
        log.info("Register " + (currentsMeasuredRegister) + ": [R.E : " + currentsMeasuredRossmannEngineering[0] + " ], [ j2mod : " + currentsMeasuredJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, currentsMeasuredRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (currentsMeasuredRegister + 1) + "3917: [R.E : " + currentsMeasuredRossmannEngineering[1] + " ], [ j2mod : " + currentsMeasuredJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (currentsMeasuredRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (currentsMeasuredRegister + 2) + "3918: [R.E : " + currentsMeasuredRossmannEngineering[2] + " ], [ j2mod : " + currentsMeasuredJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (currentsMeasuredRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");

        log.info("=== CURRENTS === MEAN [mA]");
        int currentsMeanRegister = 4346;
        Register[] currentsMeanJ2Mod = master.readMultipleRegisters(currentsMeanRegister, 3);
        int[] currentsMeanRossmannEngineering = modbusClient.ReadHoldingRegisters(currentsMeanRegister, 3);
        log.info("Register " + (currentsMeanRegister) + ": [R.E : " + currentsMeanRossmannEngineering[0] + " ], [ j2mod : " + currentsMeanJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, currentsMeanRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (currentsMeanRegister + 1) + ": [R.E : " + currentsMeanRossmannEngineering[1] + " ], [ j2mod : " + currentsMeanJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (currentsMeanRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (currentsMeanRegister + 2) + ": [R.E : " + currentsMeanRossmannEngineering[2] + " ], [ j2mod : " + currentsMeanJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (currentsMeanRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");

        log.info("=== VOLTAGE === MEASURED [V]");
        int voltageMeasuredRegister = 3530;
        Register[] voltageMeasuredJ2Mod = master.readMultipleRegisters(voltageMeasuredRegister, 3);
        int[] voltageMeasuredRossmannEngineering = modbusClient.ReadHoldingRegisters(voltageMeasuredRegister, 3);
        log.info("Register " + (voltageMeasuredRegister) + ": [R.E : " + voltageMeasuredRossmannEngineering[0] + " ], [ j2mod : " + voltageMeasuredJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, voltageMeasuredRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (voltageMeasuredRegister + 1) + ": [R.E : " + voltageMeasuredRossmannEngineering[1] + " ], [ j2mod : " + voltageMeasuredJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (voltageMeasuredRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (voltageMeasuredRegister + 2) + ": [R.E : " + voltageMeasuredRossmannEngineering[2] + " ], [ j2mod : " + voltageMeasuredJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (voltageMeasuredRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");


        log.info("=== VOLTAGE === MEAN [V]");
        int voltageMeanRegister = 3960;
        Register[] voltageMeanJ2Mod = master.readMultipleRegisters(voltageMeanRegister, 3);
        int[] voltageMeanRossmannEngineering = modbusClient.ReadHoldingRegisters(voltageMeanRegister, 3);
        log.info("Register " + (voltageMeanRegister) + ": [R.E : " + voltageMeanRossmannEngineering[0] + " ], [ j2mod : " + voltageMeanJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, voltageMeanRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (voltageMeanRegister + 1) + ": [R.E : " + voltageMeanRossmannEngineering[1] + " ], [ j2mod : " + voltageMeanJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (voltageMeanRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (voltageMeanRegister + 2) + ": [R.E : " + voltageMeanRossmannEngineering[2] + " ], [ j2mod : " + voltageMeanJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (voltageMeanRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");


        log.info("=== POWER ACTIVE === MEASURED [W]");
        int powerActiveMeasuredRegister = 3920;
        Register[] powerActiveMeasuredJ2Mod = master.readMultipleRegisters(powerActiveMeasuredRegister, 3);
        int[] powerActiveMeasuredRossmannEngineering = modbusClient.ReadHoldingRegisters(powerActiveMeasuredRegister, 3);
        log.info("Register " + (powerActiveMeasuredRegister) + ": [R.E : " + powerActiveMeasuredRossmannEngineering[0] + " ], [ j2mod : " + powerActiveMeasuredJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, powerActiveMeasuredRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerActiveMeasuredRegister + 1) + ": [R.E : " + powerActiveMeasuredRossmannEngineering[1] + " ], [ j2mod : " + powerActiveMeasuredJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerActiveMeasuredRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerActiveMeasuredRegister + 2) + ": [R.E : " + powerActiveMeasuredRossmannEngineering[2] + " ], [ j2mod : " + powerActiveMeasuredJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerActiveMeasuredRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");


        log.info("=== POWER ACTIVE === MEAN [W]");
        int powerActiveMeanRegister = 4350;
        Register[] powerActiveMeanJ2Mod = master.readMultipleRegisters(powerActiveMeanRegister, 3);
        int[] powerActiveMeanRossmannEngineering = modbusClient.ReadHoldingRegisters(powerActiveMeanRegister, 3);
        log.info("Register " + (powerActiveMeanRegister) + ": [R.E : " + powerActiveMeanRossmannEngineering[0] + " ], [ j2mod : " + powerActiveMeanJ2Mod[0] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, powerActiveMeanRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerActiveMeanRegister + 1) + ": [R.E : " + powerActiveMeanRossmannEngineering[1] + " ], [ j2mod : " + powerActiveMeanJ2Mod[1] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerActiveMeanRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerActiveMeanRegister + 2) + ": [R.E : " + powerActiveMeanRossmannEngineering[2] + " ], [ j2mod : " + powerActiveMeanJ2Mod[2] + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerActiveMeanRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");


        log.info("=== POWER REACTIVE === MEASURED [var]");
        int powerReactiveMeasuredRegister = 3924;
        Register[] powerReactiveMeasuredJ2Mod = master.readMultipleRegisters(powerReactiveMeasuredRegister, 3);
        int[] powerReactiveMeasuredRossmannEngineering = modbusClient.ReadHoldingRegisters(powerReactiveMeasuredRegister, 3);
        log.info("Register " + (powerReactiveMeasuredRegister) + ": [R.E : " + powerReactiveMeasuredRossmannEngineering[0] + " ], [ j2mod : " + powerReactiveMeasuredJ2Mod[0].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, powerReactiveMeasuredRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerReactiveMeasuredRegister + 1) + ": [R.E : " + powerReactiveMeasuredRossmannEngineering[1] + " ], [ j2mod : " + powerReactiveMeasuredJ2Mod[1].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerReactiveMeasuredRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerReactiveMeasuredRegister + 2) + ": [R.E : " + powerReactiveMeasuredRossmannEngineering[2] + " ], [ j2mod : " + powerReactiveMeasuredJ2Mod[2].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerReactiveMeasuredRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");

        log.info("=== POWER REACTIVE === MEAN [var]");
        int powerReactiveMeanRegister = 4354;
        Register[] powerReactiveMeanJ2Mod = master.readMultipleRegisters(powerReactiveMeanRegister, 3);
        int[] powerReactiveMeanRossmannEngineering = modbusClient.ReadHoldingRegisters(powerReactiveMeanRegister, 3);
        log.info("Register " + (powerReactiveMeanRegister) + ": [R.E : " + powerReactiveMeanRossmannEngineering[0] + " ], [ j2mod : " + powerReactiveMeanJ2Mod[0].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, powerReactiveMeanRegister, DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerReactiveMeanRegister + 1) + ": [R.E : " + powerReactiveMeanRossmannEngineering[1] + " ], [ j2mod : " + powerReactiveMeanJ2Mod[1].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerReactiveMeanRegister + 1), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("Register " + (powerReactiveMeanRegister + 2) + ": [R.E : " + powerReactiveMeanRossmannEngineering[2] + " ], [ j2mod : " + powerReactiveMeanJ2Mod[2].toShort() + " ] [ modbus4j : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, (powerReactiveMeanRegister + 2), DataType.TWO_BYTE_INT_SIGNED)) + " ]");
        log.info("====================");
    }


    public static void logRegistersOfJanitza503() throws Exception {

        // /dev/tty.usbserial-AB0NN63U

        log.info("Serial interface : " + serialInterface);
        SerialParameters serialParameters = new SerialParameters();
        serialParameters.setPortName(serialInterface);
        serialParameters.setBaudRate(baudrate);
        serialParameters.setStopbits(AbstractSerialConnection.TWO_STOP_BITS);
        serialParameters.setParity(AbstractSerialConnection.NO_PARITY);
        serialParameters.setDatabits(dataBits);
        AbstractModbusMaster master = new ModbusSerialMaster(serialParameters);
        master.connect();

//        String[] interfaces = "tty.usbserial-AB0NN63U,ttyAMA0,ttyS0,ttyUSB0,ttyUSB1,ttyUSB2,ttyUSB3,ttyUSB4".split(",");


        // /dev/tty.usbserial-AB0NN63U

//        while (!master.isConnected()) {
//
//            for (int i = 0; i < interfaces.length && !master.isConnected(); i++) {
//                serialParameters.setPortName("/dev/" + interfaces[i]);
//
//                try {
//                    master.connect();
//                    log.info("Connected on : " + serialParameters.getPortName());
//
//                    if(master.isConnected()){
//
//                        break;
//
//
//                    }
//
//
//                } catch (Exception ex) {
//                    System.out.println("tried : " + interfaces[i] + " port, failed.");
//                }
//
//            }
//
//        }


        log.info("=== CURRENTS === MEASURED [mA] j2mod library");
        int currentsMeasuredRegister = 8000;
        Register[] c = master.readMultipleRegisters(currentsMeasuredRegister, 3);
        log.info("Register " + currentsMeasuredRegister + " :  [ j2mod serial getValue() : " + c[0].getValue() + " ] ");
        log.info("Register " + (currentsMeasuredRegister + 1) + " :  [ j2mod serial getValue() : " + c[1].getValue() + " ] ");
        log.info("Register " + (currentsMeasuredRegister + 2) + " :  [ j2mod serial getValue() : " + c[2].getValue() + " ] ");


        log.info("=== CURRENTS === MEAN [mA] j2mod library");
        int currentsMeanRegister = 8157;
        Register[] cm = master.readMultipleRegisters(currentsMeanRegister, 3);
        log.info("Register " + currentsMeanRegister + " : [ j2mod serial getValue() : " + cm[0].getValue() + " ] ");
        log.info("Register " + (currentsMeanRegister + 1) + " : [ j2mod serial getValue() : " + cm[1].getValue() + " ] ");
        log.info("Register " + (currentsMeanRegister + 2) + " : [ j2mod serial getValue() : " + cm[2].getValue() + " ] ");


        log.info("=== VOLTAGE === MEASURED [V] j2mod library");
        int voltageMeasuredRegister = 8003;
        Register[] v = master.readMultipleRegisters(voltageMeasuredRegister, 3);
        log.info("Register " + voltageMeasuredRegister + " : [ j2mod serial getValue() : " + v[0].getValue() + " ] ");
        log.info("Register " + (voltageMeasuredRegister + 1) + " : [ j2mod serial getValue() : " + v[1].getValue() + " ] ");
        log.info("Register " + (voltageMeasuredRegister + 2) + " : [ j2mod serial getValue() : " + v[2].getValue() + " ] ");


        log.info("=== VOLTAGE === MEAN [V] j2mod library");
        int voltageMeanRegister = 8160;
        Register[] vm = master.readMultipleRegisters(voltageMeanRegister, 3);
        log.info("Register " + voltageMeanRegister + " : [ j2mod serial getValue() : " + vm[0].getValue() + " ] ");
        log.info("Register " + (voltageMeanRegister + 1) + " : [ j2mod serial getValue() : " + vm[1].getValue() + " ] ");
        log.info("Register " + (voltageMeanRegister + 2) + " : [ j2mod serial getValue() : " + vm[2].getValue() + " ] ");


        log.info("=== POWER ACTIVE === MEASURED [W] j2mod library");
        int powerActiveMeasuredRegister = 8009;
        Register[] pa = master.readMultipleRegisters(powerActiveMeasuredRegister, 3);
        log.info("Register " + powerActiveMeasuredRegister + " : [ j2mod serial toShort()  : " + pa[0].toShort() + " ] ");
        log.info("Register " + (powerActiveMeasuredRegister + 1) + " : [ j2mod serial toShort() : " + pa[1].toShort() + " ] ");
        log.info("Register " + (powerActiveMeasuredRegister + 2) + " : [ j2mod serial toShort() : " + pa[2].toShort() + " ] ");

        log.info("=== POWER ACTIVE === MEAN [W] j2mod library");
        int powerActiveMeanRegister = 8166;
        Register[] pam = master.readMultipleRegisters(powerActiveMeanRegister, 3);
        log.info("Register" + powerActiveMeanRegister + ": [ j2mod serial toShort()  : " + pam[0].toShort() + " ] ");
        log.info("Register" + (powerActiveMeanRegister + 1) + ": [ j2mod serial toShort()  : " + pam[1].toShort() + " ] ");
        log.info("Register" + (powerActiveMeanRegister + 2) + ": [ j2mod serial toShort()  : " + pam[2].toShort() + " ] ");


        log.info("=== POWER REACTIVE === MEASURED [var] j2mod library");
        int powerReactiveMeasuredRegister = 8015;
        Register[] pr = master.readMultipleRegisters(powerReactiveMeasuredRegister, 3);
        log.info("Register " + powerReactiveMeasuredRegister + " : [ j2mod serial toShort()  : " + pr[0].toShort() + " ] ");
        log.info("Register " + (powerReactiveMeasuredRegister + 1) + " : [ j2mod serial toShort()  : " + pr[1].toShort() + " ] ");
        log.info("Register " + (powerReactiveMeasuredRegister + 2) + " : [ j2mod serial toShort()  : " + pr[2].toShort() + " ] ");


        log.info("=== POWER REACTIVE === MEAN [var] j2mod library");
        int powerReactiveMeanRegister = 8172;
        Register[] prm = master.readMultipleRegisters(powerReactiveMeanRegister, 3);
        log.info("Register " + powerReactiveMeanRegister + " : [ j2mod serial toShort()  : " + prm[0].toShort() + " ] ");
        log.info("Register " + (powerReactiveMeanRegister + 1) + " : [ j2mod serial toShort()  : " + prm[1].toShort() + " ] ");
        log.info("Register " + (powerReactiveMeanRegister + 2) + " : [ j2mod serial toShort()  : " + prm[2].toShort() + " ] ");
        log.info("====================");
        master.disconnect();
    }

    private static void readConfFile(String path1, String path2) throws IOException {

        Properties p = new Properties();
        FileInputStream file;

        File f = new File(path1);
        if (f.exists()) {
            file = new FileInputStream(path1);
            log.info("Using : " + path1 + " configuration file");
        } else {
            file = new FileInputStream(path2);
            log.info("Using : " + path2 + " configuration file");
        }


        p.load(file);
        file.close();

        ip = p.getProperty("device.ip");
        port = Integer.parseInt(p.getProperty("device.port"));
        baudrate = Integer.parseInt(p.getProperty("serial.baudrate"));
        serialInterface = p.getProperty("serial.interface");
        dataBits = Integer.parseInt(p.getProperty("serial.databits"));
        janitzaName = (p.getProperty("janitza.name"));
        log.info("device.ip : " + p.getProperty("device.ip"));
        log.info("device.port : " + p.getProperty("device.port"));
        log.info("serial.baudrate : " + p.getProperty("serial.baudrate"));
        log.info("serial.interface : " + p.getProperty("serial.interface"));
        log.info("serial.databits : " + p.getProperty("serial.databits"));
    }


//        int flowControlIn = 0;
//        int flowControlOut = 0;
//        int stopBits = 2;
//        int parity = 0;
//
//        TestSerialPortWrapper wrapper = new TestSerialPortWrapper(serialInterface, baudrate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
//        ModbusMaster modbus4jserial = new ModbusFactory().createRtuMaster(wrapper);
//        modbus4jserial.setTimeout(800);
//        modbus4jserial.setRetries(1);
//        modbus4jserial.init();

//        for (int i = 1; i < 5; i++) {
//            long start = System.currentTimeMillis();
//            System.out.print("Testing " + i + "... ");
//            System.out.println(modbus4jserial.testSlaveNode(i));
//            modbus4jserial.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 1233, DataType.TWO_BYTE_INT_SIGNED));
//            System.out.println("Time: " + (System.currentTimeMillis() - start));
//        }


//        float time = (float) samplingInterval / 1000 / 60 / 60;
//        log.info("[j2mod library] === CURRENTS === MEASURED [A] multipled with current transformer: " + ct_ratio_a);
//        log.info("8000: " + (master.readMultipleRegisters(8000, 1)[0].getValue() * ct_ratio_a) / 1000);
//        log.info("8001: " + (master.readMultipleRegisters(8001, 1)[0].getValue() * ct_ratio_b) / 1000);
//        log.info("8002: " + (master.readMultipleRegisters(8002, 1)[0].getValue() * ct_ratio_c) / 1000);
//        log.info("[j2mod library] === CURRENTS === MEAN [A] multipled with current transformer: " + ct_ratio_a);
//        log.info("8157: " + (master.readMultipleRegisters(8157, 1)[0].getValue() * ct_ratio_a) / 1000);
//        log.info("8158: " + (master.readMultipleRegisters(8158, 1)[0].getValue() * ct_ratio_b) / 1000);
//        log.info("8159: " + (master.readMultipleRegisters(8159, 1)[0].getValue() * ct_ratio_c) / 1000);
//        log.info("[j2mod library] === VOLTAGE === MEASURED [V] divided by 10 and multiplied with voltage transformer: " + vt_ratio_a);
//        log.info("8003: " + master.readMultipleRegisters(8003, 1)[0].getValue() / 10 * vt_ratio_a);
//        log.info("8004: " + master.readMultipleRegisters(8004, 1)[0].getValue() / 10 * vt_ratio_b);
//        log.info("8005: " + master.readMultipleRegisters(8005, 1)[0].getValue() / 10 * vt_ratio_c);
//        log.info("[j2mod library] === VOLTAGE === MEAN [V] divided by 10 and multiplied with voltage transformer: " + vt_ratio_a);
//        log.info("8160: " + master.readMultipleRegisters(8160, 1)[0].getValue() / 10 * vt_ratio_a);
//        log.info("8161: " + master.readMultipleRegisters(8161, 1)[0].getValue() / 10 * vt_ratio_b);
//        log.info("8162: " + master.readMultipleRegisters(8162, 1)[0].getValue() / 10 * vt_ratio_c);
//        log.info("[j2mod library] === Active power POWER transformed in Energy [kWh] ===");
//        log.info("8166: " + master.readMultipleRegisters(8166, 1)[0].toShort() / 10 * time * ct_ratio_a * vt_ratio_a / 1000);
//        log.info("8167: " + master.readMultipleRegisters(8167, 1)[0].toShort() / 10 * time * ct_ratio_b * vt_ratio_b / 1000);
//        log.info("8168: " + master.readMultipleRegisters(8168, 1)[0].toShort() / 10 * time * ct_ratio_c * vt_ratio_c / 1000);
//        log.info("[j2mod library] === Reactive power POWER transformed in Energy [kWh] ===");
//        log.info("8172: " + master.readMultipleRegisters(8172, 1)[0].toShort() / 10 * time * ct_ratio_a * vt_ratio_a / 1000);
//        log.info("8173: " + master.readMultipleRegisters(8173, 1)[0].toShort() / 10 * time * ct_ratio_b * vt_ratio_b / 1000);
//        log.info("8174: " + master.readMultipleRegisters(8174, 1)[0].toShort() / 10 * time * ct_ratio_c * vt_ratio_c / 1000);
//        log.info("=========[j2mod library] ===========");
}
