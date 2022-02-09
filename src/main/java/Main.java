import com.ghgande.j2mod.modbus.ModbusException;
import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {


    public static String ip;
    public static Integer port;
    public static Integer register;


    public static void main(String[] args) throws InterruptedException, IOException {
        readConfFile("config.properties", "src/main/resources/config.properties");


        while (true) {

            ModbusTCPMaster master = null;
            try {

                master = new ModbusTCPMaster(ip);
                master.connect();
                logRegisters(master);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (master != null) {
                    master.disconnect();
                }
            }

            Thread.sleep(5000);
        }
    }


    public static void logRegisters(ModbusTCPMaster master) throws ModbusException {

        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== === === === === === === === === === === ===");
        System.out.println("=== CURRENTS === MEASURED [mA]");
        Register[] c = master.readMultipleRegisters(3916, 3);
        System.out.println("3916: " + c[0]);
        System.out.println("3917: " + c[1]);
        System.out.println("3918: " + c[2]);
        System.out.println("=== CURRENTS === MEAN [mA]");
        Register[] cm = master.readMultipleRegisters(4346, 3);
        System.out.println("4346: " + cm[0]);
        System.out.println("4347: " + cm[1]);
        System.out.println("4348: " + cm[2]);
        System.out.println("=== VOLTAGE === MEASURED [V]");
        Register[] v = master.readMultipleRegisters(3530, 3);
        System.out.println("3530: " + v[0]);
        System.out.println("3531: " + v[1]);
        System.out.println("3532: " + v[2]);
        System.out.println("=== VOLTAGE === MEAN [V]");
        Register[] vm = master.readMultipleRegisters(3960, 3);
        System.out.println("3960: " + vm[0]);
        System.out.println("3961: " + vm[1]);
        System.out.println("3962: " + vm[2]);
        System.out.println("=== POWER ACTIVE === MEASURED [W]");
        Register[] pa = master.readMultipleRegisters(3920, 3);
        System.out.println("3920: " + pa[0]);
        System.out.println("3921: " + pa[1]);
        System.out.println("3922: " + pa[2]);
        System.out.println("=== POWER ACTIVE === MEAN [W]");
        Register[] pam = master.readMultipleRegisters(4350, 3);
        System.out.println("4350: " + pam[0]);
        System.out.println("4351: " + pam[1]);
        System.out.println("4352: " + pam[2]);
        System.out.println("=== POWER REACTIVE === MEASURED [var]");
        Register[] pr = master.readMultipleRegisters(3924, 3);
        System.out.println("3924: " + pr[0]);
        System.out.println("3925: " + pr[1]);
        System.out.println("3926: " + pr[2]);
        System.out.println("=== POWER REACTIVE === MEAN [var]");
        Register[] prm = master.readMultipleRegisters(4354, 3);
        System.out.println("4354: " + prm[0]);
        System.out.println("4355: " + prm[1]);
        System.out.println("4355: " + prm[2]);

        System.out.println("====================");
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
        register = Integer.parseInt(p.getProperty("device.register"));


        System.out.println(p.getProperty("device.ip"));
        System.out.println(p.getProperty("device.port"));
        System.out.println(p.getProperty("device.register"));
    }
}
