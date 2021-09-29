package ml.miliorini.studies.health;

import ml.miliorini.studies.api.RequestHttp;
import ml.miliorini.studies.core.repositories.RemoteProxyRepository;
import ru.vyarus.dropwizard.guice.module.installer.feature.health.NamedHealthCheck;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.http.HttpResponse;

@Singleton
public class ProxyApiHeathCheck extends NamedHealthCheck {

    private final RequestHttp http;
    private final RemoteProxyRepository proxyRepository;

    @Inject
    public ProxyApiHeathCheck(RequestHttp http, RemoteProxyRepository proxyRepository) {
        this.http = http;
        this.proxyRepository = proxyRepository;
    }

    @Override
    protected Result check() throws Exception {
        HttpResponse<String> response = http.get(proxyRepository.getProxyUrl(), null, null);
        if (response.statusCode() == 200) {
            return Result.healthy();
        } else {
            return Result.unhealthy("Proxy returned status " + response.statusCode());
        }
    }

    @Override
    public String getName() {
        return "Proxy Health Check";
    }
}
