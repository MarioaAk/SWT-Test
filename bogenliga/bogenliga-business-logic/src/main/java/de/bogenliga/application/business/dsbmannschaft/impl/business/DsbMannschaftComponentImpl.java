package de.bogenliga.application.business.dsbmannschaft.impl.business;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import de.bogenliga.application.business.dsbmannschaft.api.DsbMannschaftComponent;
import de.bogenliga.application.business.dsbmannschaft.api.types.DsbMannschaftDO;
import de.bogenliga.application.business.dsbmannschaft.impl.entity.DsbMannschaftBE;
import de.bogenliga.application.business.dsbmitglied.impl.entity.DsbMitgliedBE;
import de.bogenliga.application.business.dsbmitglied.impl.mapper.DsbMitgliedMapper;
import de.bogenliga.application.common.errorhandling.ErrorCode;
import de.bogenliga.application.common.errorhandling.exception.BusinessException;
import de.bogenliga.application.common.validation.Preconditions;

/**
 * @author Philip Dengler
 */


public class DsbMannschaftComponentImpl implements DsbMannschaftComponent {

    private static final String PRECONDITION_MSG_DSBMANNSCHAFT= "DsbMannschaftDO must not be null";
    private static final String PRECONDITION_MSG_DSBMANNSCHAFT_ID = "DsbMannschaftDO ID must not be negative";
    private static final String PRECONDITION_MSG_DSBMANNSCHAFT_VEREIN_ID = "DsbMannschaftDO Verein ID must not be negative";
    private static final String PRECONDITION_MSG_DSBMANNSCHAFT_NUMMER = "DsbMannschaftDO Nummer must not be negative";
    private static final String PRECONDITION_MSG_DSBMANNSCHAFT_BENUTZER_ID = "DsbMannschaftDO Benutzer Id must not be negative";
    private static final String PRECONDITION_MSG_DSBMANNSCHAFT_VERANSTALTUNG_ID = "DsbMannschaftDO Veranstaltung ID must not be negative";
    private static final String PRECONDITION_MSG_CURRENT_DSBMANNSCHAFT = "Current dsbmannschaft id must not be negative";


    private final DsbMannschaftDAO dsbMannschaftDAO;


    /**
     * Constructor
     *
     * dependency injection with {@link Autowired}
     * @param dsbMannschaftDAO to access the database and return dsbmannschaft representations
     */

    @Autowired
    public DsbMannschaftComponentImpl(final DsbMannschaftDAO dsbMannnschaftDAO) {
        this.dsbMannschaftDAO = dsbMannschaftDAO;
    }




    @Override
    public List<DsbMannschaftDO> findAll(){
        final List<DsbMannschaftBe> dsbMannschaftBeList =dsbMannschaftDAO.findAll();
        return dsbMannschaftBeList.stream().map(dsbMannschaftMapper.toDsbMannschaftDo).collect(Collectors.toList());
    }


    @Override
    public DsbMannschaftDO findById(final long id){
        Preconditions.checkArgument( id>= 0, PRECONDITION_MSG_DSBMANNSCHAFT_ID);

        final DsbMannschaftBE result = dsbMannschaftDAO.findById(id);

        if(result == null){
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND_ERROR,
                    String.format("No result for ID '%s", id));
        }
        return DsbMitgliedMapper.toDsbMannschaft.apply(result);
    }



    @Override
    public DsbMannschaftDO create(final DsbMannschaftDO dsbMannschaftDO, final long currentDsbMannschaftId) {
        checkDsbMannschaftDO(dsbMannschaftDO, currentDsbMannschaftId);

        final DsbMannschaftBE dsbMannschaftBE = DsbMannschaftMapper.toDsbMannschaftBE.apply(dsbMannschaftDO);
        final DsbMannschaftdBE persistedDsbMannschaftBE = dsbMannschaftDAO.create(dsbMannschaftdBE, currentDsbMannschaftId);

        return DsbMannschaftMapper.toDsbMannschaftDO.apply(persistedDsbMannschaftBE);
    }



    @Override
    public DsbMannschaftDO update(final DsbMannschaftDO dsbMannschaftdDO, final long currentDsbMannschaftId) {
        checkDsbMannschaftDO(dsbMannschaftDO, currentDsbMannschaftId);
        Preconditions.checkArgument(dsbMannschaftdDO.getId() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_ID);

        final DsbMannschaftBE dsbMannschaftBE = DsbMannschaftMapper.toDsbMannschaftBE.apply(dsbMannschaftDO);
        final DsbMannschaftBE persistedDsbMannschaftBE = dsbMannschaftDAO.update(dsbMannschaftBE, currentDsbMannschaftId);

        return DsbMannschaftMapper.toDsbMannschaftDO.apply(persistedDsbMannschaftBE);
    }


    @Override
    public void delete(final DsbMannschaftDO dsbMannschaftDO, final long currentDsbMannschaftId) {
        Preconditions.checkNotNull(dsbMannschaftDO, PRECONDITION_MSG_DSBMANNSCHAFT);
        Preconditions.checkArgument(dsbMannschaftDO.getId() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_ID);
        Preconditions.checkArgument(currentDsbMannschaftId >= 0, PRECONDITION_MSG_CURRENT_DSBMANNSCHAFT);

        final DsbMitgliedBE dsbMitgliedBE = DsbMitgliedMapper.toDsbMannschaftBE.apply(dsbMannschaftDO);

        dsbMannschaftDAO.delete(dsbMannschaftdBE, currentDsbMannschaftId);

    }


    private void checkDsbMannschaftDO(final DsbMannschaftDO dsbMannschaftDO, final long currentDsbMannschaftId) {
        Preconditions.checkNotNull(dsbMannschaftDO, PRECONDITION_MSG_DSBMANNSCHAFT);
        Preconditions.checkArgument(currentDsbMannschaftId >= 0, PRECONDITION_MSG_CURRENT_DSBMANNSCHAFT);
        Preconditions.checkArgument(dsbMannschaftDO.getVereinsId() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_VEREIN_ID);
        Preconditions.checkArgument(dsbMannschaftDO.getMannschaftNummer() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_NUMMER);
        Preconditions.checkArgument(dsbMannschaftDO.getBenutzerId() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_BENUTZER_ID);
        Preconditions.checkArgument(dsbMannschaftDO.getVeranstalungId() >= 0, PRECONDITION_MSG_DSBMANNSCHAFT_VERANSTALTUNG_ID);

    }



}