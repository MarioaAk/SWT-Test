package de.bogenliga.application.services.v1.mannschaftsmitglied.service;

import de.bogenliga.application.business.dsbmannschaft.api.types.DsbMannschaftDO;
import de.bogenliga.application.business.dsbmitglied.api.types.DsbMitgliedDO;
import de.bogenliga.application.business.mannschaftsmitglied.api.MannschaftsmitgliedComponent;
import de.bogenliga.application.business.mannschaftsmitglied.api.types.MannschaftsmitgliedDO;
import de.bogenliga.application.common.service.ServiceFacade;
import de.bogenliga.application.common.service.UserProvider;
import de.bogenliga.application.common.validation.Preconditions;
import de.bogenliga.application.services.v1.dsbmitglied.mapper.DsbMitgliedDTOMapper;
import de.bogenliga.application.services.v1.dsbmitglied.model.DsbMitgliedDTO;
import de.bogenliga.application.services.v1.mannschaftsmitglied.mapper.MannschaftsMitgliedDTOMapper;
import de.bogenliga.application.services.v1.mannschaftsmitglied.model.MannschaftsMitgliedDTO;
import de.bogenliga.application.springconfiguration.security.permissions.RequiresPermission;
import de.bogenliga.application.springconfiguration.security.types.UserPermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("v1/mannschaftsmitglied")
public class MannschaftsMitgliedService implements ServiceFacade {


    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED = "MannschaftsMitglied must not be null";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_MANNSCHAFTS_ID = "MannschaftsMitglied ID must not be null";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_ID = "MannschaftsMitglied DSB MITGLIED ID must not be null";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_EINGESETZT = "MannschaftsMitglied DSB MITGLIED EINGESETZT must not be null";

    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_NEGATIVE = "MannschaftsMitglied must not be negative";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_MANNSCHAFTS_ID_NEGATIVE = "MannschaftsMitglied ID must not be negative";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_ID_NEGATIVE = "MannschaftsMitglied DSB MITGLIED ID must not be negative";
    private static final String PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_EINGESETZT_NEGATIVE = "MannschaftsMitglied DSB MITGLIED EINGESETZT must not be negative";





    private static final Logger LOG = LoggerFactory.getLogger(MannschaftsMitgliedService.class);



    /*
     * Business components
     *
     * dependency injection with {@link Autowired}
     */

    private final MannschaftsmitgliedComponent mannschaftsMitgliedComponent;


    @Autowired
    public MannschaftsMitgliedService(MannschaftsmitgliedComponent mannschaftsMitgliedComponent){
        this.mannschaftsMitgliedComponent=mannschaftsMitgliedComponent;

    }

    @RequestMapping(method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermission(UserPermission.CAN_READ_SYSTEMDATEN)
    public List<MannschaftsMitgliedDTO> findAll() {
        final List<MannschaftsmitgliedDO> MannschaftmitgliedDOList = mannschaftsMitgliedComponent.findAll();
        return MannschaftmitgliedDOList.stream().map(MannschaftsMitgliedDTOMapper.toDTO).collect(Collectors.toList());
    }


    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermission(UserPermission.CAN_READ_SYSTEMDATEN)
    public MannschaftsMitgliedDTO findById(@PathVariable("id") final long id) {
        Preconditions.checkArgument(id > 0, "ID must not be negative.");

        LOG.debug("Receive 'findById' request with ID '{}'", id);

        final MannschaftsmitgliedDO mannschaftsmitgliedDO = mannschaftsMitgliedComponent.findById(id);
        return MannschaftsMitgliedDTOMapper.toDTO.apply(mannschaftsmitgliedDO);
    }



    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermission(UserPermission.CAN_READ_SYSTEMDATEN)
    public MannschaftsMitgliedDTO findByMemberAndTeamId(@PathVariable("mannschaftsId") final long mannschaftsId, @PathVariable("dsbMitgliedId")final long mitgliedId) {
        Preconditions.checkArgument(mannschaftsId > 0, "ID must not be negative.");
        Preconditions.checkArgument(mitgliedId > 0, "ID must not be negative.");

        LOG.debug("Receive 'findById' request with ID '{}'", mannschaftsId);

        final MannschaftsmitgliedDO dsbMannschaftDO = mannschaftsMitgliedComponent.findByMemberAndTeamId(mannschaftsId,mitgliedId);
        return MannschaftsMitgliedDTOMapper.toDTO.apply(dsbMannschaftDO);
    }



    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermission(UserPermission.CAN_MODIFY_SYSTEMDATEN)
    public MannschaftsMitgliedDTO create(@RequestBody final MannschaftsMitgliedDTO mannschaftsMitgliedDTO, final Principal principal) {

        checkPreconditions(mannschaftsMitgliedDTO);

        LOG.debug("Receive 'create' request with mannschaftsId '{}', dsbMitgliedId '{}', DsbMitgliedEingesetzt '{}',",

                mannschaftsMitgliedDTO.getMannschaftsId(),
                mannschaftsMitgliedDTO.getDsbMitgliedId(),
                mannschaftsMitgliedDTO.isDsbMitgliedEingesetzt());

        final MannschaftsmitgliedDO newMannschaftsmitgliedDO = MannschaftsMitgliedDTOMapper.toDO.apply(mannschaftsMitgliedDTO);
        final long currentMemberId = UserProvider.getCurrentUserId(principal);


        final MannschaftsmitgliedDO savedMannschaftsmitgliedDO = mannschaftsMitgliedComponent.create(newMannschaftsmitgliedDO, currentMemberId);
        return MannschaftsMitgliedDTOMapper.toDTO.apply(savedMannschaftsmitgliedDO);
    }


     @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermission(UserPermission.CAN_MODIFY_SYSTEMDATEN)
    public MannschaftsMitgliedDTO update(@RequestBody final MannschaftsMitgliedDTO mannschaftsMitgliedDTO, final Principal principal) {
        checkPreconditions(mannschaftsMitgliedDTO);
        Preconditions.checkArgument(mannschaftsMitgliedDTO.getMannschaftsId() >= 0, PRECONDITION_MSG_MANNSCHAFTSMITGLIED_MANNSCHAFTS_ID);

        LOG.debug("Receive 'create' request with mannschaftsId '{}', dsbMitgliedId '{}', DsbMitgliedEingesetzt '{}',",

                mannschaftsMitgliedDTO.getMannschaftsId(),
                mannschaftsMitgliedDTO.getDsbMitgliedId(),
                mannschaftsMitgliedDTO.isDsbMitgliedEingesetzt());

        final MannschaftsmitgliedDO newMannschaftsMitgliedDO = MannschaftsMitgliedDTOMapper.toDO.apply(mannschaftsMitgliedDTO);
        final long userId = UserProvider.getCurrentUserId(principal);

        final MannschaftsmitgliedDO updatedMannschaftsmitgliedDO = mannschaftsMitgliedComponent.update(newMannschaftsMitgliedDO, userId);
        return MannschaftsMitgliedDTOMapper.toDTO.apply(updatedMannschaftsmitgliedDO);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @RequiresPermission(UserPermission.CAN_MODIFY_SYSTEMDATEN)
    public void delete(@PathVariable("mannschaftsId") final long mannschaftsId,
                       @PathVariable("mitgliedId") final long mitgliedId,final Principal principal) {
        Preconditions.checkArgument(mannschaftsId >= 0, "mannschaftsId must not be negative.");
        Preconditions.checkArgument(mitgliedId>= 0, "mitgliedId must not be negativ");

        LOG.debug("Receive 'delete' request with id '{}'", mannschaftsId);

        // allow value == null, the value will be ignored
        final MannschaftsmitgliedDO mannschaftsMitgliedDO = new MannschaftsmitgliedDO(mannschaftsId,mitgliedId);
        final long currentUserId = UserProvider.getCurrentUserId(principal);

        mannschaftsMitgliedComponent.delete(mannschaftsMitgliedDO, currentUserId);
    }

    private void checkPreconditions(@RequestBody final MannschaftsMitgliedDTO mannschaftsMitgliedDTO) {
        Preconditions.checkNotNull(mannschaftsMitgliedDTO, PRECONDITION_MSG_MANNSCHAFTSMITGLIED);
        Preconditions.checkNotNull(mannschaftsMitgliedDTO.getMannschaftsId(), PRECONDITION_MSG_MANNSCHAFTSMITGLIED_MANNSCHAFTS_ID);
        Preconditions.checkNotNull(mannschaftsMitgliedDTO.getDsbMitgliedId(), PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_ID);





        Preconditions.checkArgument(mannschaftsMitgliedDTO.getMannschaftsId()>= 0,
                PRECONDITION_MSG_MANNSCHAFTSMITGLIED_MANNSCHAFTS_ID_NEGATIVE);
        Preconditions.checkArgument(mannschaftsMitgliedDTO.getDsbMitgliedId() >= 0,
                PRECONDITION_MSG_MANNSCHAFTSMITGLIED_DSB_MITGLIED_ID_NEGATIVE);



    }


}