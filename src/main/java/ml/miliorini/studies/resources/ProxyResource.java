package ml.miliorini.studies.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.miliorini.studies.core.entities.Proxy;
import ml.miliorini.studies.core.services.IProxyService;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Singleton
@Api("Proxy")
@Path("/proxy")
@Produces(MediaType.APPLICATION_JSON)
public class ProxyResource {

    private final IProxyService proxyService;

    @Inject
    public ProxyResource(IProxyService proxyService) {
        this.proxyService = proxyService;
    }

    @GET
    @Path("all")
    @ApiOperation("List all proxies currently in the repository")
    public List<Proxy> getProxies() {
        return this.proxyService.list();
    }

    @GET
    @Path("working-proxy")
    @ApiOperation("Finds a working proxy in the repository")
    public Proxy findWorkingProxy() {
        return this.proxyService.findWorkingProxy();
    }

    @GET
    @Path("test-all")
    @ApiOperation("Test all proxies")
    public List<Proxy> testAllProxies() {
        return this.proxyService.testAllProxies();
    }
}
