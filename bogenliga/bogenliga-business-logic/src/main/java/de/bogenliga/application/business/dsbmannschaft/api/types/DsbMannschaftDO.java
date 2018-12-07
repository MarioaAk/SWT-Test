package de.bogenliga.application.business.dsbmannschaft.api.types;

import de.bogenliga.application.common.component.types.CommonDataObject;
import de.bogenliga.application.common.component.types.DataObject;

import java.sql.Date;
import java.time.OffsetDateTime;
import java.util.Objects;

public class DsbMannschaftDO extends CommonDataObject implements DataObject {

    /**
     * business parameter
     */

    private long id;
    private long vereinId;
    private long nummer;
    private long benutzerId;
    private long veranstaltungId;


    /**
     * Constructor with optional parameters
     * @param id
     * @param vereinId
     * @param nummer
     * @param benutzerId
     * @param veranstaltungId
     * @param createdAtUtc
     * @param createdByUserId
     * @param lastModifiedAtUtc
     * @param lastModifiedByUserId
     * @param version
     */

    public DsbMannschaftDO(final Long id, final long vereinId, final long nummer, final long benutzerId,final long veranstaltungId, final OffsetDateTime createdAtUtc,
                         final Long createdByUserId, final OffsetDateTime lastModifiedAtUtc,
                         final Long lastModifiedByUserId, final Long version) {
        this.id = id;
        this.vereinId=vereinId;
        this.nummer=nummer;
        this.benutzerId=benutzerId;
        this.veranstaltungId=veranstaltungId;
        this.createdAtUtc = createdAtUtc;
        this.createdByUserId = createdByUserId;
        this.lastModifiedAtUtc = lastModifiedAtUtc;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.version = version;
    }




    /**
     * Constructor with mandatory parameters
     * @param id
     * @param vereinId
     * @param nummer
     * @param benutzerId
     * @param veranstaltungId
     * @param createdAtUtc
     * @param createdByUserId
     * @param version
     */
    public DsbMannschaftDO(final Long id, final long vereinId, final long nummer, final long benutzerId,final long veranstaltungId, final OffsetDateTime createdAtUtc,
                           final Long createdByUserId,final Long version) {
        this.id = id;
        this.vereinId=vereinId;
        this.nummer=nummer;
        this.benutzerId=benutzerId;
        this.veranstaltungId=veranstaltungId;
        this.createdAtUtc = createdAtUtc;
        this.createdByUserId = createdByUserId;
        this.version = version;
    }


    /**
     * Constructor without technical parameters
     * @param id
     * @param vereinId
     * @param nummer
     * @param benutzerId
     * @param veranstaltungId

     */
    public DsbMannschaftDO(final Long id, final long vereinId, final long nummer,
                           final long benutzerId,final long veranstaltungId) {
        this.id = id;
        this.vereinId=vereinId;
        this.nummer=nummer;
        this.benutzerId=benutzerId;
        this.veranstaltungId=veranstaltungId;

    }

    /**
     * individuel constructor
     * @param id
     * @param vereinId
     */
    public DsbMannschaftDO(final Long id, final long vereinId) {
        this.id = id;
        this.vereinId = vereinId;
    }

    /**
     * Constructor with id for deleting existing entries
     * @param id
     */
    public DsbMannschaftDO(final Long id) {
        this.id = id;
    }


    public long getId(){ return id; }

    public void setId(final long id){this.id=id;}


    public long getVereinId(){return vereinId;}

    public void setVereinId(final long vereinId){this.vereinId=vereinId;}


    public long getNummer(){return nummer;}

    public void setNummer(final long nummer){this.nummer=nummer;}


    public long getBenutzerId(){return benutzerId;}

    public void setBenutzerId(final long benutzerId){this.benutzerId=benutzerId;}


    public long getVeranstaltungId(){return veranstaltungId;}

    public void setVeranstaltungId(final long veranstaltungId){this.veranstaltungId=veranstaltungId;}


    @Override
    public int hashCode() {
        return Objects.hash(id, vereinId,nummer,benutzerId,veranstaltungId,
                createdByUserId, lastModifiedAtUtc,
                lastModifiedByUserId, version);
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DsbMannschaftDO that = (DsbMannschaftDO) o;
        return id == that.id &&
                vereinId == that.vereinId &&
                benutzerId == that.benutzerId &&
                createdByUserId == that.createdByUserId &&
                lastModifiedByUserId == that.lastModifiedByUserId &&
                version == that.version &&
                Objects.equals(nummer, that.nummer) &&
                Objects.equals(veranstaltungId, that.veranstaltungId) &&
                Objects.equals(lastModifiedAtUtc, that.lastModifiedAtUtc);
    }
}
