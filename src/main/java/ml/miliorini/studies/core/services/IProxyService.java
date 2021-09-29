package ml.miliorini.studies.core.services;

import ml.miliorini.studies.core.entities.Proxy;

import java.util.List;

public interface IProxyService {

    List<Proxy> list();

    Proxy findWorkingProxy();

    List<Proxy> testAllProxies();
}
