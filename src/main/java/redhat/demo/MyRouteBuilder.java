package redhat.demo;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

/**
 * A Camel Java DSL Router
 */
public class MyRouteBuilder extends RouteBuilder {

    /**
     * Let's configure the Camel routing rules using Java code...
     */
    public void configure() {

        restConfiguration()
        .component("undertow").port(8080).bindingMode(RestBindingMode.auto);

        rest("/sap")
                .get("/flights").to("direct:flights");

        from("direct:flights")
            .toD("olingo2://read/CarrierSet('${headers.carrierid}')/Flights")
            .log("[REST] >>> OData response from SAP using camel-olingo2 ${body}");
    }

}
