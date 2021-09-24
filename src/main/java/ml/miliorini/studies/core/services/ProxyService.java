package ml.miliorini.studies.core.services;

import ml.miliorini.studies.core.entities.Proxy;
import ml.miliorini.studies.core.repositories.IProxyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ProxyService implements IProxyService {

    private final IProxyRepository proxyRepository;
    private final Logger logger = LoggerFactory.getLogger(ProxyService.class);

    @Inject
    public ProxyService(IProxyRepository proxyRepository) {
        this.proxyRepository = proxyRepository;
    }

    @Override
    public List<Proxy> list() {
        return this.proxyRepository.list();
    }

    @Override
    public Proxy findWorkingProxy() {
        boolean isWorking = false;
        Proxy proxy = null;
        while (!isWorking) {
            proxy = this.proxyRepository.getRandomProxy();
            this.logger.info("Requesting with proxy {}", proxy);
            try {
                HttpClient httpClient = HttpClient.newBuilder()
                        .proxy(ProxySelector.of(new InetSocketAddress(proxy.getIp(), proxy.getPort())))
                        .build();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI("http://meuip.com/api/meuip.php"))
                        .timeout(Duration.ofSeconds(20))
                        .build();
                HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    isWorking = true;
                }
            } catch (IndexOutOfBoundsException exception) {
                this.logger.error("No more proxies available!");
                break;
            } catch (Exception exception) {
                this.logger.error("Proxy {} is not working.", proxy);
                this.proxyRepository.removeProxy(proxy);
            }
        }
        return proxy;
    }

}
