package ml.miliorini.studies;

import com.google.inject.AbstractModule;
import ml.miliorini.studies.api.RequestHttp;
import ml.miliorini.studies.core.repositories.IProxyRepository;
import ml.miliorini.studies.core.repositories.RemoteProxyRepository;
import ml.miliorini.studies.core.services.IProxyService;
import ml.miliorini.studies.core.services.ProxyService;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IProxyService.class).to(ProxyService.class);
        bind(IProxyRepository.class).to(RemoteProxyRepository.class);
        bind(RequestHttp.class);
    }
}
