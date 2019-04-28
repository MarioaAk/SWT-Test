package de.bogenliga.application.business.match.impl.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import de.bogenliga.application.business.match.impl.entity.MatchBE;
import de.bogenliga.application.common.component.dao.BasicDAO;
import de.bogenliga.application.common.component.dao.BusinessEntityConfiguration;
import de.bogenliga.application.common.component.dao.DataAccessObject;

/**
 * @author Dominik Halle, HSRT MKI SS19 - SWT2
 */
@Repository
public class MatchDAO implements DataAccessObject {
    // define logger context
    private static final Logger LOGGER = LoggerFactory.getLogger(MatchDAO.class);

    // table name in the DB
    private static final String TABLE = "match";


    // business entity parameters
    private static final String MATCH_BE_ID = "id";
    private static final String MATCH_BE_NR = "nr";
    private static final String MATCH_BE_WETTKAMPF_ID = "wettkampfId";
    private static final String MATCH_BE_MANNSCHAFT_ID = "mannschaftId";
    private static final String MATCH_BE_SCHEIBENNUMMER = "scheibenNummer";
    private static final String MATCH_BE_BEGEGNUNG = "begegnung";
    private static final String MATCH_BE_MATCHPUNKTE = "matchpunkte";
    private static final String MATCH_BE_SATZPUNKTE = "satzpunkte";

    // table columns
    private static final String MATCH_TABLE_ID = "match_id";
    private static final String MATCH_TABLE_NR = "match_nr";
    private static final String MATCH_TABLE_WETTKAMPF_ID = "match_wettkampf_id";
    private static final String MATCH_TABLE_MANNSCHAFT_ID = "match_mannschaft_id";
    private static final String MATCH_TABLE_SCHEIBENNUMMER = "match_scheibennummer";
    private static final String MATCH_TABLE_BEGEGNUNG = "match_begegnung";
    private static final String MATCH_TABLE_MATCHPUNKTE = "match_matchpunkte";
    private static final String MATCH_TABLE_SATZPUNKTE = "match_satzpunkte";

    // wrap all specific config parameters
    private static final BusinessEntityConfiguration<MatchBE> MATCH = new BusinessEntityConfiguration<>(
            MatchBE.class, TABLE, getColumnsToFieldsMap(), LOGGER);

    private final BasicDAO basicDao;


    /**
     * Initialize the transaction manager to provide a database connection
     *
     * @param basicDao to handle the commonly used database operations
     */
    @Autowired
    public MatchDAO(final BasicDAO basicDao) {
        this.basicDao = basicDao;
    }


    // table column label mapping to the business entity parameter names
    private static Map<String, String> getColumnsToFieldsMap() {
        final Map<String, String> columnsToFieldsMap = new HashMap<>();

        columnsToFieldsMap.put(MATCH_TABLE_ID, MATCH_BE_ID);
        columnsToFieldsMap.put(MATCH_TABLE_NR, MATCH_BE_NR);
        columnsToFieldsMap.put(MATCH_TABLE_MANNSCHAFT_ID, MATCH_BE_MANNSCHAFT_ID);
        columnsToFieldsMap.put(MATCH_TABLE_WETTKAMPF_ID, MATCH_BE_WETTKAMPF_ID);
        columnsToFieldsMap.put(MATCH_TABLE_BEGEGNUNG, MATCH_BE_BEGEGNUNG);
        columnsToFieldsMap.put(MATCH_TABLE_SCHEIBENNUMMER, MATCH_BE_SCHEIBENNUMMER);
        columnsToFieldsMap.put(MATCH_TABLE_MATCHPUNKTE, MATCH_BE_MATCHPUNKTE);
        columnsToFieldsMap.put(MATCH_TABLE_SATZPUNKTE, MATCH_BE_SATZPUNKTE);

        // add technical columns
        columnsToFieldsMap.putAll(BasicDAO.getTechnicalColumnsToFieldsMap());

        return columnsToFieldsMap;
    }


    /**
     * SQL queries
     */
    private static final String FIND_ALL =
            "SELECT * "
                    + " FROM " + TABLE
                    + " ORDER BY " + MATCH_TABLE_ID;

    private static final String FIND_BY_ID =
            "SELECT * "
                    + " FROM " + TABLE
                    + " WHERE " + MATCH_TABLE_ID
                    + "=?"
                    + " ORDER BY " + MATCH_TABLE_ID;

    private static final String FIND_BY_PK =
            "SELECT * "
                    + " FROM " + TABLE
                    + " WHERE " + MATCH_TABLE_NR + "=?"
                    + " AND " + MATCH_TABLE_WETTKAMPF_ID + "=?"
                    + " AND " + MATCH_TABLE_MANNSCHAFT_ID + "=?"
                    + " AND " + MATCH_TABLE_BEGEGNUNG + "=?"
                    + " AND " + MATCH_TABLE_SCHEIBENNUMMER + "=?"
                    + " ORDER BY " + MATCH_TABLE_NR;

    private static final String FIND_BY_MANNSCHAFT_ID =
            "SELECT * "
                    + " FROM " + TABLE
                    + " WHERE " + MATCH_TABLE_MANNSCHAFT_ID
                    + "=?";

    private static final String FIND_BY_WETTKAMPF_ID =
            "SELECT * "
                    + " FROM " + TABLE
                    + " WHERE " + MATCH_TABLE_WETTKAMPF_ID
                    + "=?";


    /**
     * Create a new match entry
     *
     * @param matchBE
     * @param currentUserId
     *
     * @return Business Entity corresponding to the created kampfrichter entry (should only be created by Veranstalter
     * and  entities
     */
    public MatchBE create(final MatchBE matchBE, final Long currentUserId) {
        basicDao.setCreationAttributes(matchBE, currentUserId);

        return basicDao.insertEntity(MATCH, matchBE);
    }


    /**
     * Return a specific match.
     *
     * @return match with given id
     */
    public MatchBE findById(final Long matchId) {
        return basicDao.selectSingleEntity(MATCH, FIND_BY_ID, matchId);
    }


    /**
     * Return a specific match.
     *
     * @return match with given combined primary key attributes
     */
    public MatchBE findByPk(Long nr, Long wettkampfId, Long mannschaftId, Long begegnung, Long scheibenNummer) {
        return basicDao.selectSingleEntity(
                MATCH, FIND_BY_PK,
                nr, wettkampfId, mannschaftId,
                begegnung, scheibenNummer
        );
    }

    /**
     * Return all match entries.
     *
     * @return list of all match in the database; empty list, if no match is found
     */
    public List<MatchBE> findAll() {
        return basicDao.selectEntityList(MATCH, FIND_ALL);
    }


    /**
     * Return all match from one Wettkampf
     *
     * @param wettkampfId
     *
     * @return list of all match from one Wettkampf in the database; empty list, if no match are found
     */
    public List<MatchBE> findByWettkampfId(Long wettkampfId) {
        return basicDao.selectEntityList(MATCH, FIND_BY_WETTKAMPF_ID, wettkampfId);
    }


    /**
     * Return all match entries from one mannschaft.
     *
     * @param mannschaftId
     *
     * @return list of all match from one team in the database; empty list, if no match are found
     */
    public List<MatchBE> findByMannschaftId(Long mannschaftId) {
        return basicDao.selectEntityList(MATCH, FIND_BY_MANNSCHAFT_ID, mannschaftId);
    }


    /**
     * Update an existing match entry
     *
     * @param matchBE
     * @param currentUserId
     *
     * @return Business Entity corresponding to the updated match entry
     */
    public MatchBE update(final MatchBE matchBE, final Long currentUserId) {
        basicDao.setModificationAttributes(matchBE, currentUserId);

        return basicDao.updateEntity(MATCH, matchBE, MATCH_BE_NR);
    }


    /**
     * Delete existing match
     *
     * @param matchBE
     * @param currentUserId
     */
    public void delete(final MatchBE matchBE, final Long currentUserId) {
        basicDao.setModificationAttributes(matchBE, currentUserId);

        basicDao.deleteEntity(MATCH, matchBE, MATCH_BE_NR);
    }

}