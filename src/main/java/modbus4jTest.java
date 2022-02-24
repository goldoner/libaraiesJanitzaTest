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
        // ipParameters.setHost("99.247.60.96");
        // ipParameters.setHost("193.109.41.121");
        //      ipParameters.setHost("10.241.224.195");
        ipParameters.setHost("10.0.0.55");
        ipParameters.setPort(502);
        ipParameters.setEncapsulated(false);

        ModbusFactory modbusFactory = new ModbusFactory();
        // ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, true);
        ModbusMaster master = modbusFactory.createTcpMaster(ipParameters, false);
        master.setTimeout(8000);
        master.setRetries(3);
        master.init();

        //        for (int i = 1; i < 5; i++) {
        //            System.out.print("Testing " + i + "... ");
        //            System.out.println(master.testSlaveNode(i));
        //        }


        for (int i = 0; i < 100; i++) {
            try {
                System.out.println("-------------------------------");


                try {
                    System.out.println("1 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_FLOAT)));

                } catch (Exception ex) {
                    System.out.println("1. Exception");
                }

                try {
                    System.out.println("2 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.EIGHT_BYTE_FLOAT)));

                } catch (Exception ex) {
                    System.out.println("2. Exception");
                }

                try {
                    System.out.println("3 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.BINARY)));

                } catch (Exception ex) {
                    System.out.println("3. Exception");
                }

                try {
                    System.out.println("4 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.EIGHT_BYTE_INT_SIGNED)));


                } catch (Exception ex) {
                    System.out.println("4. Exception");
                }

                try {
                    System.out.println("5 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.EIGHT_BYTE_INT_UNSIGNED)));
                } catch (Exception ex) {
                    System.out.println("5. Exception");
                }

                try {
                    System.out.println("6 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.TWO_BYTE_BCD)));


                } catch (Exception ex) {
                    System.out.println("6. Exception");
                }

                try {
                    System.out.println("7 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.VARCHAR)));


                } catch (Exception ex) {
                    System.out.println("7. Exception");
                }


                try {

                    System.out.println("8 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.TWO_BYTE_INT_SIGNED)));

                } catch (Exception ex) {
                    System.out.println("8. Exception");
                }


                try {

                    System.out.println("9 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.TWO_BYTE_INT_UNSIGNED)));

                } catch (Exception ex) {
                    System.out.println("9. Exception");
                }


                try {
                    System.out.println("10 : " + master.getValue(new NumericLocator(1, RegisterRange.HOLDING_REGISTER, 3916, DataType.FOUR_BYTE_BCD)));


                } catch (Exception ex) {
                    System.out.println("10. Exception");
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

        master.destroy();
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
