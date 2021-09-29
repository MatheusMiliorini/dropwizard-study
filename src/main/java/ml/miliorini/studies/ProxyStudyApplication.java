package ml.miliorini.studies;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.federecio.dropwizard.swagger.SwaggerBundle;
import io.federecio.dropwizard.swagger.SwaggerBundleConfiguration;
import ru.vyarus.dropwizard.guice.GuiceBundle;

public class ProxyStudyApplication extends Application<ProxyStudyConfiguration> {

    public static void main(final String[] args) throws Exception {
        new ProxyStudyApplication().run(args);
    }

    @Override
    public void initialize(final Bootstrap<ProxyStudyConfiguration> bootstrap) {
        bootstrap.addBundle(GuiceBundle.builder()
                .enableAutoConfig(getClass().getPackage().getName())
                .modules(new AppModule())
                .build());

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
    }

}
