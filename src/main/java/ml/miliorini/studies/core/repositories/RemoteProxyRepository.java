package ml.miliorini.studies.core.repositories;

import ml.miliorini.studies.api.RequestHttp;
import ml.miliorini.studies.core.entities.Proxy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class RemoteProxyRepository implements IProxyRepository {

    private final RequestHttp http;
    private final String proxyUrl = "https://proxylist.geonode.com/api/proxy-list?limit=50&page=1&sort_by=lastChecked&sort_type=desc&filterLastChecked=60&speed=medium&protocols=http";
    private static List<Proxy> proxyList = new ArrayList<>();
    private static final Logger logger = LoggerFactory.getLogger(RequestHttp.class);

    @Inject
    public RemoteProxyRepository(RequestHttp http) {
        this.http = http;
    }

    @Override
    public List<Proxy> list() {
        if (proxyList.size() == 0) {
            this.fetchProxies();
        }
        return proxyList;
    }

    @Override
    public void removeProxy(Proxy proxy) {
        proxyList.remove(proxy);
        logger.info("Proxy {} was removed from the repository. Current Proxy repository size: {}", proxy, proxyList.size());
    }

    private void fetchProxies() {
        logger.info("Fetching proxies from remote repository");
        List<Proxy> newProxyList = new ArrayList<>();
        HttpResponse<String> response = http.get(this.proxyUrl, null, null);
        if (response.statusCode() == 200) {
            JSONArray proxyArray = new JSONObject(response.body()).getJSONArray("data");
            for (int i = 0; i < proxyArray.length(); i++) {
                newProxyList.add(Proxy.fromJson(proxyArray.getJSONObject(i)));
            }
            proxyList = newProxyList;
            logger.info("{} proxies found", proxyList.size());
        }
    }
}
