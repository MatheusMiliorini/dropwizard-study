package ml.miliorini.studies.core.services;

import ml.miliorini.studies.api.RequestHttp;
import ml.miliorini.studies.core.entities.Proxy;
import ml.miliorini.studies.core.repositories.IProxyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ProxyService implements IProxyService {

    private final IProxyRepository proxyRepository;
    private final RequestHttp http;
    private final Logger logger = LoggerFactory.getLogger(ProxyService.class);

    @Inject
    public ProxyService(IProxyRepository proxyRepository, RequestHttp http) {
        this.proxyRepository = proxyRepository;
        this.http = http;
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
            isWorking = this.isProxyWorking(proxy);
            if (!isWorking) {
                this.proxyRepository.removeProxy(proxy);
            }
        }
        return proxy;
    }

    @Override
    public List<Proxy> testAllProxies() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Proxy> proxyList = proxyRepository.list();
        logger.info("Checking all {} proxies", proxyList.size());
        for (Proxy proxy : proxyList) {
            executor.submit(() -> {
                this.logger.info("Requesting with proxy {}", proxy);
                boolean isWorking = this.isProxyWorking(proxy);
                if (!isWorking) {
                    this.proxyRepository.removeProxy(proxy);
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.MINUTES)) {
                logger.error("Couldn't terminate executor in 5 minutes.");
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            logger.error("InterruptedException", e);
            executor.shutdownNow();
        }
        return proxyRepository.list();
    }

    private boolean isProxyWorking(Proxy proxy) {
        boolean isWorking = false;
        try {
            HttpResponse<String> response = http.get("http://meuip.com/api/meuip.php", proxy, 30);
            if (response.statusCode() == 200) {
                isWorking = true;
            }
        } catch (Exception exception) {
            this.logger.error("Proxy {} is not working.", proxy);
        }
        return isWorking;
    }

}
