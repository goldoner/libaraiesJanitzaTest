/*
 *
 * Copyright (c) 2018, 4ng and/or its affiliates. All rights reserved.
 * 4ENERGY PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */

import com.ghgande.j2mod.modbus.facade.ModbusTCPMaster;
import com.ghgande.j2mod.modbus.procimg.Register;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 *
 */
public class MasterReadTest {

    public static String ip;
    public static Integer port;
    public static Integer register;

    private static void readConfFile(String path) throws IOException {

        Properties p = new Properties();

        FileInputStream file = new FileInputStream(path);
        p.load(file);
        file.close();

        ip = p.getProperty("device.ip");
        port = Integer.parseInt(p.getProperty("device.port"));
        register = Integer.parseInt(p.getProperty("device.register"));


        System.out.println(p.getProperty("device.ip"));
        System.out.println(p.getProperty("device.port"));
        System.out.println(p.getProperty("device.register"));
    }

    public static void main(String[] args) throws IOException {
        readConfFile("config.properties");
        ModbusTCPMaster master = new ModbusTCPMaster(ip, port, 3000, true, true);
        try {
            master.connect();
            for (int i = 0; i < 10; i++) {
                int ref = 100;
                Register[] regs = master.readMultipleRegisters(1, ref, 10);
                for (Register reg : regs) {
                    System.out.printf("Reg: %d Val: %d\n", ref, reg.getValue());
                    ref++;
                }
            }
        } catch (Exception e) {
            System.out.printf("ERROR - %s\n", e.getMessage());
        } finally {
            master.disconnect();
        }
    }
}