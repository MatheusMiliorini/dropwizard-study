package ml.miliorini.studies.core.repositories;

import ml.miliorini.studies.core.entities.Proxy;

import java.util.List;
import java.util.Random;

public interface IProxyRepository {

    List<Proxy> list();

    default Proxy getRandomProxy() {
        List<Proxy> proxyList = this.list();
        return proxyList.get(new Random().nextInt(proxyList.size()));
    }

    void removeProxy(Proxy proxy);
}
