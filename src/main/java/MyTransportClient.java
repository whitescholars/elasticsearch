import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Mark on 2016/12/20.
 */
public class MyTransportClient {
    private TransportClient transportClient;
    private static MyTransportClient clientInstance;
    private MyTransportClient() {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name","elasticsearch")//我的集群名
                .put("client.transport.sniff",true)
                .build();
        try {
            transportClient = TransportClient.builder().settings(settings).build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
    /*
     * 单例
     */
    public static MyTransportClient getInstance(){
        if(clientInstance==null){
            clientInstance = new MyTransportClient();
        }
        return clientInstance;
    }

    public TransportClient getTransportClient() {
        return transportClient;
    }
}