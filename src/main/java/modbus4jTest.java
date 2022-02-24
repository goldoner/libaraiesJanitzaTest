import com.serotonin.modbus4j.ModbusFactory;
import com.serotonin.modbus4j.ModbusMaster;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.code.RegisterRange;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.NumericLocator;

public class modbus4jTest {

    public static void main(String[] args) throws ModbusInitException {


        IpParameters ipParameters = new IpParameters();
        ipParameters.setHost("10.0.0.55");
        ipParameters.setPort(502);
        ipParameters.setEncapsulated(false);
        ModbusFactory modbusFactory = new ModbusFactory();
        ModbusMaster modbus4j = modbusFactory.createTcpMaster(ipParameters, false);
        modbus4j.setTimeout(8000);
        modbus4j.setRetries(3);
        modbus4j.init();

        //        for (int i = 1; i < 5; i++) {
        //            System.out.print("Testing " + i + "... ");
        //            System.out.println(master.testSlaveNode(i));
        //        }


        for (int i = 0; i < 100; i++) {
            try {
                System.out.println("-------------------------------");


                try {
                    System.out.println("Currents  3916 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));
                    System.out.println("1 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));
                    System.out.println("1 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));
                    System.out.println("1 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.TWO_BYTE_INT_SIGNED)));
                    System.out.println("1 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));
                    System.out.println("1 : " + modbus4j.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));

                } catch (Exception ex) {
                    System.out.println("1. Exception");
                }


                System.out.println("-------------------------------");
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // try {
        // // ReadCoilsRequest request = new ReadCoilsRequest(2, 65534, 1);
        // ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master
        // .send(new ReadHoldingRegistersRequest(2, 0, 1));
        // System.out.println(response);
        // }
        // catch (Exception e) {
        // e.printStackTrace();
        // }

        // System.out.println(master.scanForSlaveNodes());

        modbus4j.destroy();
    }

//        ModbusFactory factory = new ModbusFactory();
//        IpParameters params = new IpParameters();
//
//        //        params.setHost("127.0.0.1");
//        //        params.setPort(502);
//        //        params.setEncapsulated(true);
//
//        params.setHost("10.0.0.55");
//        params.setPort(502);
//        params.setEncapsulated(false);
//
//        ModbusMaster master = factory.createTcpMaster(params, true);
//         master.setRetries(4);
//        master.setTimeout(5000);
//        master.setRetries(0);
//
//        long start = System.currentTimeMillis();
//        try {
//            master.init();
//            for (int i = 0; i < 100; i++) {
//                System.out.println(master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 11,
//                        DataType.TWO_BYTE_INT_SIGNED)));
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        } finally {
//            master.destroy();
//        }
//
//        System.out.println("Took: " + (System.currentTimeMillis() - start) + "ms");
//
//
//    }
}
