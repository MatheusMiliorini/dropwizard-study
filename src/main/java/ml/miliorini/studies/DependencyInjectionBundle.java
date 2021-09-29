package ml.miliorini.studies;

import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import javax.inject.Singleton;
import java.util.Map;

public class DependencyInjectionBundle implements ConfiguredBundle<ProxyStudyConfiguration> {

    @Override
    public void run(ProxyStudyConfiguration configuration, Environment environment) {
        environment.jersey().register(
                new AbstractBinder() {
                    @Override
                    protected void configure() {
                        for (Class<?> singletonClass : configuration.getSingletons()) {
                            bindAsContract(singletonClass).in(Singleton.class);
                        }
                        for (Map.Entry<Class<?>, Class<?>> binding : configuration.getBindings().entrySet()) {
                            bind(binding.getKey()).to(binding.getValue());
                        }
                    }
                }
        );
    }
}
