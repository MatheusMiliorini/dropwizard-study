package ml.miliorini.studies;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ml.miliorini.studies.resources.ProxyResource;

public class ProxyStudyApplication extends Application<ProxyStudyConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProxyStudyApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<ProxyStudyConfiguration> bootstrap) {
        bootstrap.addBundle(new SwaggerBundle<>() {
            @Override
            protected SwaggerBundleConfiguration getSwaggerBundleConfiguration(ProxyStudyConfiguration configuration) {
                return configuration.swaggerBundleConfiguration;
            }
        });
    }

    @Override
    public void run(final ProxyStudyConfiguration configuration,
                    final Environment environment) {
        // Dependency Injection
        final DependencyInjectionBundle dependencyInjectionBundle = new DependencyInjectionBundle();
        dependencyInjectionBundle.run(configuration, environment);
        // Resources register
        environment.jersey().register(ProxyResource.class);
    }

}
