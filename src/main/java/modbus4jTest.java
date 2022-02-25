import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class modbus4jTest {


    public static String ip;
    public static String janitzaName;
    public static Integer port;
    public static Integer baudrate;
    public static Integer register;
    public static Integer dataBits;
    public static String serialInterface;

    private static final Logger log = Logger.getLogger(modbus4jTest.class);

    public static void main(String[] args) throws ModbusInitException, InterruptedException {

        int flowControlIn = 0;
        int flowControlOut = 0;
        int stopBits = 2;
        int parity = 0;

        TestSerialPortWrapper wrapper = new TestSerialPortWrapper(serialInterface, baudrate, flowControlIn, flowControlOut, dataBits, stopBits, parity);
        ModbusMaster master = new ModbusFactory().createRtuMaster(wrapper);
        master.setTimeout(200);
        master.setRetries(1);
        master.init();

        for (int i = 1; i < 100; i++) {

            try {
                System.out.println("8000 : " + master.send(new ReadHoldingRegistersRequest(1, 8000, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("8157 : " + master.send(new ReadHoldingRegistersRequest(1, 8157, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                System.out.println("8003 : " + master.send(new ReadHoldingRegistersRequest(1, 8003, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                System.out.println("8160 : " + master.send(new ReadHoldingRegistersRequest(1, 8160, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                System.out.println("8166 : " + master.send(new ReadHoldingRegistersRequest(1, 8166, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                System.out.println("8172 : " + master.send(new ReadHoldingRegistersRequest(1, 8172, 1)));
            } catch (Exception e) {
                e.printStackTrace();
            }


            Thread.sleep(5000);

        }


        master.destroy();
    }


    private static void readConfFile(String path1, String path2) throws IOException {

        Properties p = new Properties();
        FileInputStream file;

        File f = new File(path1);
        if (f.exists()) {
            file = new FileInputStream(path1);
        } else {
            file = new FileInputStream(path2);
        }


        p.load(file);
        file.close();

        ip = p.getProperty("device.ip");
        port = Integer.parseInt(p.getProperty("device.port"));
        baudrate = Integer.parseInt(p.getProperty("serial.baudrate"));
        serialInterface = p.getProperty("serial.interface");
        register = Integer.parseInt(p.getProperty("device.register"));
        dataBits = Integer.parseInt(p.getProperty("serial.databits"));
        janitzaName = (p.getProperty("janitza.name"));
        log.info("Janitza Name from configFile : " + p.getProperty("janitza.name"));
    }

}
