import com.digitalpetri.modbus.master.ModbusTcpMaster;
import com.digitalpetri.modbus.master.ModbusTcpMasterConfig;
import com.digitalpetri.modbus.requests.ReadHoldingRegistersRequest;
import com.digitalpetri.modbus.responses.ReadHoldingRegistersResponse;
import io.netty.buffer.ByteBufUtil;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.CompletableFuture;

public class Main {

    public static void main(String[] args) {


        ModbusTcpMasterConfig config = new ModbusTcpMasterConfig.Builder("10.0.0.5").build();
        ModbusTcpMaster master = new ModbusTcpMaster(config);

        master.connect();

        CompletableFuture<ReadHoldingRegistersResponse> future =
                master.sendRequest(new ReadHoldingRegistersRequest(0, 10), 0);

        future.thenAccept(response -> {
            System.out.println("Response: " + ByteBufUtil.hexDump(response.getRegisters()));

            ReferenceCountUtil.release(response);
        });

    }

}
