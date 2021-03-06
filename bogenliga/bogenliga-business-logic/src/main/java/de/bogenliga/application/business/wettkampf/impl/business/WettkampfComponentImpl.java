package de.bogenliga.application.business.wettkampf.impl.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.bogenliga.application.business.wettkampf.api.WettkampfComponent;
import de.bogenliga.application.business.wettkampf.api.types.WettkampfDO;
import de.bogenliga.application.business.wettkampf.impl.dao.WettkampfDAO;
import de.bogenliga.application.business.wettkampf.impl.entity.WettkampfBE;
import de.bogenliga.application.business.wettkampf.impl.mapper.WettkampfMapper;
import de.bogenliga.application.common.errorhandling.ErrorCode;
import de.bogenliga.application.common.errorhandling.exception.BusinessException;
import de.bogenliga.application.common.validation.Preconditions;

/**
 * Implementation of {@link WettkampfComponent}
 *
 * Autor: Marvin Holm, Daniel Schott
 */
@Component
public class WettkampfComponentImpl implements WettkampfComponent {

    private static final String PRECONDITION_MSG_WETTKAMPF_ID = "wettkampfID must not be null and must not be negative";
    private static final String PRECONDITION_MSG_WETTKAMPF_VERANSTALTUNGS_ID = "wettkampfVeranstaltungsID must not be null and must not be negative";
    private static final String PRECONDITION_MSG_WETTKAMPF_DATUM = "wettkampfDatum must not be null";
    private static final String PRECONDITION_MSG_WETTKAMPF_BEGINN = "wettkampfBeginn must not be null";
    private static final String PRECONDITION_MSG_WETTKAMPF_TAG = "wettkampfTag must not be null";
    private static final String PRECONDITION_MSG_WETTKAMPF_DISZIPLIN_ID = "wettkampfDisziplinID must not be null and must not be negative";
    private static final String PRECONDITION_MSG_WETTKAMPF_WETTKAMPFTYP_ID = "wettkampfTypID must not be null and must not be negative";


    private final WettkampfDAO wettkampfDAO;


    /**
     * Constructor
     * <p>
     * dependency injection with {@link Autowired}
     *
     * @param wettkampfDAO to access the database and return dsbmitglied representations
     */
    @Autowired
    public WettkampfComponentImpl(final WettkampfDAO wettkampfDAO) {
        this.wettkampfDAO = wettkampfDAO;
        System.out.println("created DAO object");
    }


    @Override
    public List<WettkampfDO> findAll() {
        final List<WettkampfBE> wettkampfBEList = wettkampfDAO.findAll();
        return wettkampfBEList.stream().map(WettkampfMapper.toWettkampfDO).collect(Collectors.toList());
    }

    // Do we need this method for anything or does it purely exist because it has to implement the interfaces method?
    @Override
    public List<WettkampfDO> findByAusrichter(long id) {
        return new ArrayList<>();
    }


    @Override
    public WettkampfDO findById(final long id) {
        Preconditions.checkArgument(id >= 0, PRECONDITION_MSG_WETTKAMPF_ID);

        final WettkampfBE result = wettkampfDAO.findById(id);

        if (result == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND_ERROR,
                    String.format("No result found for ID '%s'", id));
        }

        return WettkampfMapper.toWettkampfDO.apply(result);
    }


    @Override
    public List<WettkampfDO> findAllWettkaempfeByMannschaftsId(long id) {
        Preconditions.checkArgument(id >= 0, PRECONDITION_MSG_WETTKAMPF_ID);

        final List<WettkampfBE> wettkampfBEList = wettkampfDAO.findAllWettkaempfeByMannschaftsId(id);
        return wettkampfBEList.stream().map(WettkampfMapper.toWettkampfDO).collect(Collectors.toList());
    }


    @Override
    public List<WettkampfDO> findAllByVeranstaltungId(long veranstaltungId) {
        Preconditions.checkArgument(veranstaltungId >= 0, PRECONDITION_MSG_WETTKAMPF_VERANSTALTUNGS_ID);
        final List<WettkampfBE> wettkampfBEList = this.wettkampfDAO.findAllByVeranstaltungId(veranstaltungId);
        return wettkampfBEList.stream().map(WettkampfMapper.toWettkampfDO).collect(Collectors.toList());
    }


    @Override
    public WettkampfDO create(final WettkampfDO wettkampfDO, final long currentWettkampfID) {
        checkDsbMitgliedDO(wettkampfDO, currentWettkampfID);

        final WettkampfBE wettkampfBE = WettkampfMapper.toWettkampfBE.apply(wettkampfDO);
        System.out.println("\n\n");
        System.out.println(wettkampfBE.toString());
        final WettkampfBE persistedWettkampfBe = wettkampfDAO.create(wettkampfBE, currentWettkampfID);

        return WettkampfMapper.toWettkampfDO.apply(persistedWettkampfBe);
    }


    @Override
    public WettkampfDO update(final WettkampfDO wettkampfDO, final long currentWettkampfID) {
        checkDsbMitgliedDO(wettkampfDO, currentWettkampfID);
        Preconditions.checkArgument(wettkampfDO.getId() >= 0, PRECONDITION_MSG_WETTKAMPF_ID);

        final WettkampfBE wettkampfBE = WettkampfMapper.toWettkampfBE.apply(wettkampfDO);
        System.out.println("\n\n");
        System.out.println(wettkampfBE.toString());
        final WettkampfBE persistedWettkampfBe = wettkampfDAO.update(wettkampfBE, currentWettkampfID);

        return WettkampfMapper.toWettkampfDO.apply(persistedWettkampfBe);
    }


    @Override
    public void delete(final WettkampfDO wettkampfDO, final long currentWettkampfID) {
        Preconditions.checkNotNull(wettkampfDO, PRECONDITION_MSG_WETTKAMPF_ID);
        Preconditions.checkArgument(wettkampfDO.getId() >= 0, PRECONDITION_MSG_WETTKAMPF_ID);
        Preconditions.checkArgument(currentWettkampfID >= 0, PRECONDITION_MSG_WETTKAMPF_ID);

        final WettkampfBE wettkampfBE = WettkampfMapper.toWettkampfBE.apply(wettkampfDO);

        wettkampfDAO.delete(wettkampfBE, currentWettkampfID);

    }


    private void checkDsbMitgliedDO(final WettkampfDO wettkampfDO, final long currentWettkampfID) {
        Preconditions.checkNotNull(wettkampfDO, PRECONDITION_MSG_WETTKAMPF_ID);

        Preconditions.checkNotNull(wettkampfDO.getWettkampfVeranstaltungsId(),
                PRECONDITION_MSG_WETTKAMPF_VERANSTALTUNGS_ID);
        Preconditions.checkArgument(wettkampfDO.getWettkampfVeranstaltungsId() >= 0,
                PRECONDITION_MSG_WETTKAMPF_VERANSTALTUNGS_ID);
        Preconditions.checkNotNull(wettkampfDO.getWettkampfDatum(), PRECONDITION_MSG_WETTKAMPF_DATUM);
        Preconditions.checkNotNull(wettkampfDO.getWettkampfBeginn(), PRECONDITION_MSG_WETTKAMPF_BEGINN);
        Preconditions.checkNotNull(wettkampfDO.getWettkampfTag(), PRECONDITION_MSG_WETTKAMPF_TAG);
        Preconditions.checkNotNull(wettkampfDO.getWettkampfDisziplinId(), PRECONDITION_MSG_WETTKAMPF_DISZIPLIN_ID);
        Preconditions.checkArgument(wettkampfDO.getWettkampfDisziplinId() >= 0, PRECONDITION_MSG_WETTKAMPF_ID);
        Preconditions.checkNotNull(wettkampfDO.getWettkampfTypId(), PRECONDITION_MSG_WETTKAMPF_WETTKAMPFTYP_ID);
        Preconditions.checkArgument(wettkampfDO.getWettkampfTypId() >= 0, PRECONDITION_MSG_WETTKAMPF_ID);

    }

}
