package de.bogenliga.application.business.regionen.impl.business;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import de.bogenliga.application.business.regionen.api.RegionenComponent;
import de.bogenliga.application.business.regionen.api.types.RegionenDO;
import de.bogenliga.application.business.regionen.impl.dao.RegionenDAO;
import de.bogenliga.application.business.regionen.impl.entity.RegionenBE;
import de.bogenliga.application.business.regionen.impl.mapper.RegionenMapper;
import de.bogenliga.application.common.validation.Preconditions;

/**
 * Implementation of {@link RegionenComponent}
 *
 * @author Dennis Goericke, dennis.goericke@student.reutlingen-university.de
 */
@Component
public class RegionenComponentImpl implements RegionenComponent {

    private static final String PRECONDITION_MSG_REGION = "RegionDO must not be null";
    private static final String PRECONDITION_MSG_REGION_ID = "RegionDO ID must not be negative";
    private static final String PRECONDITION_MSG_REGION_NAME = "RegionDO name must not be null";
    private static final String PRECONDITION_MSG_REGION_DSB_MITGLIED_NOT_NEG = "DsbMitglied id must not be negative";

    private static final Logger LOGGER = LoggerFactory.getLogger(RegionenComponentImpl.class);

    //no need for preconditions since we only need to implement FindAll()

    public final RegionenDAO regionenDAO;


    /**
     * Constructor Dependeny injection with {@link org.springframework.beans.factory.annotation.Autowired}
     *
     * @param regionenDAO
     */
    public RegionenComponentImpl(RegionenDAO regionenDAO) {
        this.regionenDAO = regionenDAO;
    }


    @Override
    public List<RegionenDO> findAll() {
        final List<RegionenBE> regionenBEList = regionenDAO.findAll();
        return syncListofDOs(regionenBEList.stream().map(RegionenMapper.toRegionDO).collect(Collectors.toList()));
    }


    @Override
    public List<RegionenDO> findAllByType(final String type) {
        final List<RegionenBE> regionenBEList = regionenDAO.findAllByType(type);
        return syncListofDOs(regionenBEList.stream().map(RegionenMapper.toRegionDO).collect(Collectors.toList()));
    }

    @Override
    public RegionenDO findById(long vereinId) {
        final RegionenBE regionenBE = regionenDAO.findById(vereinId);
        return syncSingle(RegionenMapper.toRegionDO.apply(regionenBE),regionenDAO.findAll());
    }

    @Override
    public RegionenDO create(RegionenDO regionenDO, long currentDsbMitglied) {
        checkRegionenDO(regionenDO, currentDsbMitglied);

        List<RegionenBE> allRegions = regionenDAO.findAll();
        syncSingle(regionenDO, allRegions);

        final RegionenBE regionenBE = RegionenMapper.toRegionBE.apply(regionenDO);
        final RegionenBE persistedRegionenBE = regionenDAO.create(regionenBE, currentDsbMitglied);

        return syncSingle(RegionenMapper.toRegionDO.apply(persistedRegionenBE),allRegions);
    }

    @Override
    public RegionenDO update(RegionenDO regionenDO, long currentDsbMitglied) {
        checkRegionenDO(regionenDO, currentDsbMitglied);
        Preconditions.checkArgument(regionenDO.getId() >= 0, PRECONDITION_MSG_REGION_ID);

        List<RegionenBE> allRegions = regionenDAO.findAll();
        syncSingle(regionenDO, allRegions);

        final RegionenBE regionenBE = RegionenMapper.toRegionBE.apply(regionenDO);
        final RegionenBE persistedRegionenBE = regionenDAO.update(regionenBE, currentDsbMitglied);

        return syncSingle(RegionenMapper.toRegionDO.apply(persistedRegionenBE), allRegions);
    }

    @Override
    public void delete(RegionenDO regionenDO, long currentDsbMitglied) {
        Preconditions.checkNotNull(regionenDO, PRECONDITION_MSG_REGION);
        Preconditions.checkArgument(regionenDO.getId() >= 0, PRECONDITION_MSG_REGION_ID);
        Preconditions.checkArgument(currentDsbMitglied >= 0, PRECONDITION_MSG_REGION_DSB_MITGLIED_NOT_NEG);

        final RegionenBE regionenBE = RegionenMapper.toRegionBE.apply(regionenDO);

        regionenDAO.delete(regionenBE, currentDsbMitglied);
    }

    private void checkRegionenDO(final RegionenDO regionenDO, final long currentDsbMitgliedId) {
        Preconditions.checkNotNull(regionenDO, PRECONDITION_MSG_REGION);
        Preconditions.checkArgument(currentDsbMitgliedId >= 0, PRECONDITION_MSG_REGION_DSB_MITGLIED_NOT_NEG);
        Preconditions.checkNotNull(regionenDO.getRegionName(), PRECONDITION_MSG_REGION_NAME);
    }

    /**
     * I am synchronizing the ID with the uebergeordnetAsName of all given RegionenDOs.
     * Therefore i am calling the syncSingle method for each region.
     * @param regionDOs all regions as DOs.
     * @return the same list of regionDOs, but all IDs and uebergeordnetAsName are matching correctly.
     */
    private List<RegionenDO> syncListofDOs(List<RegionenDO> regionDOs) {
        List<RegionenBE> allRegions = regionenDAO.findAll();
        return regionDOs.stream().map(region -> syncSingle(region, allRegions)).collect(Collectors.toList());
    }


    /**
     * I am mapping the regionUebergeordnet ID to the matching Name of the corresponding Region. And the other way
     * around: regionUebergeordnet --> ID --> getRegionById--> regionName --> regionUebergeordentAsName
     * regionUebergeordnetAsName --> regionName--> getRegionByName --> ID --> regionUebergeordent
     * @param currentRegion the RegionenDO, which you want to synchronize
     * @param regions a list of RegionenBEs to find the matching regionName, therefore the list
     *                should contain all regions of the database.
     * @return the same currentRegion, but the ID is matching to the uebergeordnetAsName.
     */
    private RegionenDO syncSingle(RegionenDO currentRegion, List<RegionenBE> regions) {
        List<RegionenBE> possibleRegions;
        //Case: The region has a superordinate name but not yet the id
        if (currentRegion.getRegionUebergeordnet() == null
                && currentRegion.getRegionUebergeordnetAsName() != null) {

            possibleRegions = regions.stream().filter(
                    region -> region.getRegionName().equals(currentRegion.getRegionUebergeordnetAsName()))
                    .collect(Collectors.toList());

            if (possibleRegions != null && !possibleRegions.isEmpty()) {
                currentRegion.setRegionUebergeordnet(possibleRegions.get(0).getRegionId());
            } else {
                LOGGER.debug("Mapping of the regionUebergeordnetAsName to the regionUebergeordnet Id failed.");
            }

            //Case: The region has a superordinate id but not its corresponding name
        } else if (currentRegion.getRegionUebergeordnet() != null
                && currentRegion.getRegionUebergeordnetAsName() == null) {

            possibleRegions = regions.stream().filter(region -> region.getRegionId() ==
                    currentRegion.getRegionUebergeordnet()).collect(
                    Collectors.toList());

            if (possibleRegions != null && !possibleRegions.isEmpty()) {
                currentRegion.setRegionUebergeordnetAsName(possibleRegions.get(0).getRegionName());
            } else {
                LOGGER.debug("Mapping of the regionUebergeordnet Id to the regionUebergeordnetAsName failed.");
            }
        }
        return currentRegion;
    }


}
