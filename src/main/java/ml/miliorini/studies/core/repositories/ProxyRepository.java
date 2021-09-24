package ml.miliorini.studies.core.repositories;

import ml.miliorini.studies.core.entities.Proxy;
import org.eclipse.jetty.util.IO;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProxyRepository implements IProxyRepository {

    private static List<Proxy> proxyList = new ArrayList<>();
    private final Logger logger = LoggerFactory.getLogger(ProxyRepository.class);

    @Override
    public List<Proxy> list() {
        if (proxyList.size() == 0) {
            proxyList = new ArrayList<>();
            try {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream("proxy-list.json");
                if (inputStream != null) {
                    String jsonStr = IO.toString(inputStream, StandardCharsets.UTF_8);
                    JSONArray proxyArray = new JSONArray(jsonStr);
                    for (int i = 0; i < proxyArray.length(); i++) {
                        proxyList.add(Proxy.fromJson(proxyArray.getJSONObject(i)));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return proxyList;
    }

    @Override
    public Proxy getRandomProxy() {
        List<Proxy> proxyList = this.list();
        return proxyList.get(new Random().nextInt(proxyList.size()));
    }

    @Override
    public void removeProxy(Proxy proxy) {
        proxyList.remove(proxy);
        this.logger.info("Proxy {} was removed from the repository. Current Proxy repository size: {}", proxy, proxyList.size());
    }
}
