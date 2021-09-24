package ml.miliorini.studies.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.json.JSONObject;


public class Proxy {

    @ApiModelProperty(value = "IP Address", example = "177.54.10.2")
    private final String ip;
    @ApiModelProperty(value = "Port", example = "3128")
    private final int port;

    public Proxy(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @JsonProperty
    public String getIp() {
        return ip;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    public static Proxy fromJson(JSONObject json) {
        return new Proxy(json.getString("ip"), json.getInt("port"));
    }

    @Override
    public String toString() {
        return "Proxy{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }
}
