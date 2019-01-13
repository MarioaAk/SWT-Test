package de.bogenliga.application.services.v1.setzliste.service;

import de.bogenliga.application.business.Setzliste.api.SetzlisteComponent;
import de.bogenliga.application.common.service.ServiceFacade;
import de.bogenliga.application.springconfiguration.security.permissions.RequiresPermission;
import de.bogenliga.application.springconfiguration.security.types.UserPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

/**
 * I´m a REST resource and handle setzliste pdf requests over the HTTP protocol.
 *
 * @author Dennis Eitle
 * @see <a href="https://en.wikipedia.org/wiki/Create,_read,_update_and_delete">Wikipedia - CRUD</a>
 * @see <a href="https://en.wikipedia.org/wiki/Representational_state_transfer">Wikipedia - REST</a>
 * @see <a href="https://en.wikipedia.org/wiki/Hypertext_Transfer_Protocol">Wikipedia - HTTP</a>
 * @see <a href="https://en.wikipedia.org/wiki/Design_by_contract">Wikipedia - Design by contract</a>
 * @see <a href="https://spring.io/guides/gs/actuator-service/">
 * Building a RESTful Web Service with Spring Boot Actuator</a>
 * @see <a href="https://www.baeldung.com/building-a-restful-web-service-with-spring-and-java-based-dsbMitglied">
 * Build a REST API with Spring 4 and Java Config</a>
 * @see <a href="https://www.baeldung.com/spring-autowire">Guide to Spring @Autowired</a>
 */
@RestController
@CrossOrigin(maxAge = 0)
@RequestMapping("v1/setzliste")
public class SetzlisteService implements ServiceFacade {

    private static final Logger LOG = LoggerFactory.getLogger(SetzlisteService.class);

    /*
     * Business components
     *
     * dependency injection with {@link Autowired}
     */
    private final SetzlisteComponent setzlisteComponent;


    /**
     * Constructor with dependency injection
     *
     */
    @Autowired
    public SetzlisteService(final SetzlisteComponent setzlisteComponent) {
        this.setzlisteComponent = setzlisteComponent;
    }


    /**
     * I return the setzliste pdf.
     *
     * Usage:
     * <pre>{@code Request: GET /v1/setzliste/app.bogenliga.frontend.autorefresh.active}</pre>
     * <pre>{@code Response:
     *  {
     *    "id": "app.bogenliga.frontend.autorefresh.active",
     *    "value": "true"
     *  }
     * }
     * </pre>
     *
     * @return application/pdf
     */
     @CrossOrigin(maxAge = 0)
    @RequestMapping(method = RequestMethod.GET)
    @RequiresPermission(UserPermission.CAN_READ_SYSTEMDATEN)
    public @ResponseBody ResponseEntity<InputStreamResource> getTableByVarsV2(@RequestParam("wettkampfid") int wettkampfid, @RequestParam("wettkampftag") int wettkampftag) {
        LOG.debug("wettkampfid: " + wettkampfid);
        LOG.debug("wettkampftag: " + wettkampftag);
        final String fileName = setzlisteComponent.getTable(wettkampfid, wettkampftag);
        Resource resource = new ClassPathResource(fileName);
        long r = 0;
        InputStream is=null;

        try {
            is = resource.getInputStream();
            r = resource.contentLength();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().contentLength(r).cacheControl(CacheControl.noCache())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(new InputStreamResource(is));
    }
}
