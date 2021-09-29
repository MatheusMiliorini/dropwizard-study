package ml.miliorini.studies;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ml.miliorini.studies.api.RequestHttp;
import ml.miliorini.studies.core.repositories.IProxyRepository;
import ml.miliorini.studies.core.repositories.RemoteProxyRepository;
import ml.miliorini.studies.core.services.IProxyService;
import ml.miliorini.studies.core.services.ProxyService;
import ml.miliorini.studies.resources.ProxyResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyStudyConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    public List<Class<?>> getSingletons() {
        final List<Class<?>> result = new ArrayList<>();
        result.add(ProxyResource.class);
        return result;
    }

    public Map<Class<?>, Class<?>> getBindings() {
        Map<Class<?>, Class<?>> bindings = new HashMap<>();
        bindings.put(ProxyService.class, IProxyService.class);
        bindings.put(RemoteProxyRepository.class, IProxyRepository.class);
        bindings.put(RequestHttp.class, RequestHttp.class);
        return bindings;
    }
}
