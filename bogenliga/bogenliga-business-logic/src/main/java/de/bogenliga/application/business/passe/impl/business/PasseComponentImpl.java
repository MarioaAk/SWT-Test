package de.bogenliga.application.business.passe.impl.business;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import de.bogenliga.application.business.passe.api.PasseComponent;
import de.bogenliga.application.business.passe.api.types.PasseDO;
import de.bogenliga.application.business.passe.impl.dao.PasseDAO;
import de.bogenliga.application.business.passe.impl.entity.PasseBE;
import de.bogenliga.application.business.passe.impl.mapper.PasseMapper;
import de.bogenliga.application.common.validation.Preconditions;

/**
 * @author Kay Scheerer
 */
@Component
public class PasseComponentImpl implements PasseComponent {

    private static final String PRECONDITION_MSG_TEMPLATE = "Match: %s must not be null and must not be negative";
    public static final String PRECONDITION_MSG_MATCH_NR = String.format(PRECONDITION_MSG_TEMPLATE, "matchNr");
    public static final String PRECONDITION_MSG_WETTKAMPF_ID = String.format(PRECONDITION_MSG_TEMPLATE, "wettkampfId");
    public static final String PRECONDITION_MSG_MANNSCHAFT_ID = String.format(PRECONDITION_MSG_TEMPLATE,
            "mannschaftId");
    public static final String PRECONDITION_MSG_LFD_NR = String.format(PRECONDITION_MSG_TEMPLATE, "lfdNr");
    public static final String PRECONDITION_MSG_DSB_MITGLIED_ID = String.format(PRECONDITION_MSG_TEMPLATE,
            "dsbMitgliedId");

    private final PasseDAO passeDAO;


    /**
     * Constructor
     * <p>
     * dependency injection with {@link Autowired}
     *
     * @param passeDAO to access the database and return passe representations
     */
    @Autowired
    public PasseComponentImpl(final PasseDAO passeDAO) {
        this.passeDAO = passeDAO;
    }


    private final String PRECONDITION_MSG_TEMPLATE_NULL = "Passe: %s must not be null";

    private final String PRECONDITION_MSG_TEMPLATE_NEGATIVE = "Passe: %s must not be negative";


    public void checkPreconditions(final Long id, String iDIdentifier) {
        Preconditions.checkNotNull(id, String.format(PRECONDITION_MSG_TEMPLATE_NULL, iDIdentifier));
        Preconditions.checkArgument(id >= 0, String.format(PRECONDITION_MSG_TEMPLATE_NEGATIVE, iDIdentifier));
    }


    /**
     * Return all passe entries.
     *
     * @return list of all passe in the database; empty list, if no passe is found
     */
    @Override
    public List<PasseDO> findAll() {
        final List<PasseBE> passeBEList = passeDAO.findAll();
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());

    }


    /**
     * Return all passe from one Wettkampf
     *
     * @param wettkampfId
     *
     * @return list of all passe from one Wettkampf in the database; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByWettkampfId(Long wettkampfId) {
        checkPreconditions(wettkampfId, "wettkampf_Id");
        final List<PasseBE> passeBEList = passeDAO.findByWettkampfId(wettkampfId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return all passe from one Wettkampf
     *
     * @param teamId of the mannschaft
     *
     * @return list of all passe from one Wettkampf in the database; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByTeamId(Long teamId) {
        checkPreconditions(teamId, "mannschaft_Id");
        final List<PasseBE> passeBEList = passeDAO.findByTeamId(teamId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return all passe from one Wettkampf
     *
     * @param memberId of the member
     *
     * @return list of all passe from one Wettkampf in the database; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByMemberId(Long memberId) {
        checkPreconditions(memberId, "member_Id");
        final List<PasseBE> passeBEList = passeDAO.findByMemberId(memberId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return a passe entry with the given ids.
     *
     * @param dsbMemberId  of the mannschaftsmitglied,
     * @param mannschaftId of the mannschaft
     *
     * @return list of passe from one mitglied in a mannschaft ; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByMemberMannschaftId(Long dsbMemberId, Long mannschaftId) {
        checkPreconditions(dsbMemberId, "dsbMember_Id");
        checkPreconditions(mannschaftId, "mannschaft_Id");
        final List<PasseBE> passeBEList = passeDAO.findByMemberMannschaftId(dsbMemberId, mannschaftId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return a passe entry with the given ids.
     *
     * @param dsbMemberId of the mannschaftsmitglied,
     * @param matchId     of the mannschaft
     *
     * @return list of passe from one mitglied in a mannschaft ; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByMitgliedMatchId(Long dsbMemberId, Long matchId) {
        checkPreconditions(dsbMemberId, "dsbMember_Id");
        checkPreconditions(matchId, "match_Id");
        final List<PasseBE> passeBEList = passeDAO.findByMitgliedMatchId(dsbMemberId, matchId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return a passe entry with the given ids.
     *
     * @param mannschaftId of the mannschaftsmitglied,
     * @param matchId      of the match
     *
     * @return list of passe from one mannschaft in a match; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByMannschaftMatchId(Long mannschaftId, Long matchId) {
        checkPreconditions(matchId, "match_Id");
        checkPreconditions(mannschaftId, "mannschaft_Id");
        final List<PasseBE> passeBEList = passeDAO.findByMannschaftMatchId(mannschaftId, matchId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     * Return a passe entry with the given ids.
     *
     * @param matchId of the match
     *
     * @return list of passe from one mannschaft in a match; empty list, if no passe are found
     */
    @Override
    public List<PasseDO> findByMatchId(Long matchId) {
        checkPreconditions(matchId, "match_Id");
        final List<PasseBE> passeBEList = passeDAO.findByMatchId(matchId);
        return passeBEList.stream().map(PasseMapper.toPasseDO).collect(Collectors.toList());
    }


    /**
     *  Finds a passe by its ID
     * @param passeId
     * @return a DO witht he given ID
     */
    public PasseDO findByPasseId(Long passeId) {
        checkPreconditions(passeId, "passeId");
        final PasseBE passeBE = passeDAO.findByPasseId(passeId);
        return PasseMapper.toPasseDO.apply(passeBE);
    }


    /**
     *
     * @param wettkampfId
     * @param matchNr
     * @param mannschaftId
     * @param passeLfdNr
     * @param dsbMitgliedId
     * @return a passe with the given PK
     */
    public PasseDO findByPk(Long wettkampfId, Long matchNr, Long mannschaftId, Long passeLfdNr, Long dsbMitgliedId) {
        checkPreconditions(wettkampfId, "wettkampfId");
        checkPreconditions(matchNr, "matchNr");
        checkPreconditions(mannschaftId, "mannschaftId");
        checkPreconditions(passeLfdNr, "passeLfdNr");
        checkPreconditions(dsbMitgliedId, "dsbMitgliedId");
        final PasseBE passeBE = passeDAO.findByPk(wettkampfId, matchNr, mannschaftId, passeLfdNr, dsbMitgliedId);
        return PasseMapper.toPasseDO.apply(passeBE);
    }


    /**
     * Create a new passe in the database.
     *
     * @param passeDO       new passeDO
     * @param currentUserId the current user creating
     *
     * @return persisted version of the passe
     */
    @Override
    public PasseDO create(PasseDO passeDO, final Long currentUserId) {
        checkPasseDO(passeDO);
        checkPreconditions(currentUserId, "currentUserId");
        final PasseBE passeBE = PasseMapper.toPasseBE.apply(passeDO);

        final PasseBE persistedPasseBE = passeDAO.create(passeBE, currentUserId);
        return PasseMapper.toPasseDO.apply(persistedPasseBE);
    }


    /**
     * Update an existing passe. The passe is identified by the id's set in passeDO.
     *
     * @param passeDO         existing passeDO to update
     * @param currentMemberId id of the member currently updating the passe
     *
     * @return persisted version of the passe
     */
    @Override
    public PasseDO update(PasseDO passeDO, Long currentMemberId) {
        checkPasseDO(passeDO);
        checkPreconditions(currentMemberId, "currentMemberId");
        final PasseBE passeBE = PasseMapper.toPasseBE.apply(passeDO);

        final PasseBE persistedPasseBE = passeDAO.update(passeBE, currentMemberId);
        return PasseMapper.toPasseDO.apply(persistedPasseBE);
    }


    /**
     * checks if fields of a DO are null or negative
     * @param passeDO the DO to check
     */
    public void checkPasseDO(PasseDO passeDO) {
        checkPreconditions(passeDO.getPasseMannschaftId(), "passeMannschaftId");
        checkPreconditions(passeDO.getPasseWettkampfId(), "passeMannschaftId");
        checkPreconditions(passeDO.getPasseMatchNr(), "passeMannschaftId");
        checkPreconditions(passeDO.getPasseMatchId(), "passeMannschaftId");
        checkPreconditions(passeDO.getPasseLfdnr(), "passeMannschaftId");
        checkPreconditions(passeDO.getPasseDsbMitgliedId(), "passeMannschaftId");
        checkPreconditions(new Long(passeDO.getPfeil1()), "Pfeil1");
        checkPreconditions(new Long(passeDO.getPfeil1()), "Pfeil2");
    }


    /**
     * Delete an existing passe. The passe is identified by the id's set in passeDO.
     *
     * @param passeDO         passe to delete
     * @param currentMemberId id of the member currently updating the passe
     */
    @Override
    public void delete(PasseDO passeDO, Long currentMemberId) {
        checkPreconditions(currentMemberId, "currentMemberId");
        final PasseBE passeBE = PasseMapper.toPasseBE.apply(passeDO);
        passeDAO.delete(passeBE, currentMemberId);
    }
}