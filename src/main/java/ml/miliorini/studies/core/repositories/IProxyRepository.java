package ml.miliorini.studies.core.repositories;

import ml.miliorini.studies.core.entities.Proxy;

import java.util.List;

public interface IProxyRepository {

    List<Proxy> list();

    Proxy getRandomProxy();

    void removeProxy(Proxy proxy);
}
