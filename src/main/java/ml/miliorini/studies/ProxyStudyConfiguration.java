package ml.miliorini.studies;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ml.miliorini.studies.core.repositories.IProxyRepository;
import ml.miliorini.studies.core.repositories.ProxyRepository;
import ml.miliorini.studies.core.services.IProxyService;
import ml.miliorini.studies.core.services.ProxyService;
import ml.miliorini.studies.resources.ProxyResource;

import java.util.ArrayList;
import java.util.List;

class Binding {
    private final Class<?> impl;
    private final Class<?> itf;

    public Binding(Class<?> impl, Class<?> itf) {
        this.impl = impl;
        this.itf = itf;
    }

    public Class<?> getImpl() {
        return impl;
    }

    public Class<?> getItf() {
        return itf;
    }
}

public class ProxyStudyConfiguration extends Configuration {

    @JsonProperty("swagger")
    public SwaggerBundleConfiguration swaggerBundleConfiguration;

    public List<Class<?>> getSingletons() {
        final List<Class<?>> result = new ArrayList<>();
//        result.add(ProxyResource.class);
        return result;
    }

    public List<Binding> getBindings() {
        List<Binding> bindings = new ArrayList<>();
        bindings.add(new Binding(ProxyService.class, IProxyService.class));
        bindings.add(new Binding(ProxyRepository.class, IProxyRepository.class));
        return bindings;
    }
}
