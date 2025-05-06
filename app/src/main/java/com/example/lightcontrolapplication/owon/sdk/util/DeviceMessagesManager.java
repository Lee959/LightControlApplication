import com.example.gatewayapplication.DeviceModel;
import com.example.gatewayapplication.GatewayListBean;
import com.example.lightcontrolapplication.owon.sdk.util.SocketMessageListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Device message manager class that handles communication with devices and gateways.
 * This class encapsulates methods for controlling and querying devices such as lights, motors, sensors, etc.
 */
public class DeviceMessagesManager {

    private static DeviceMessagesManager instance;
    private List<SocketMessageListener> listeners;

}