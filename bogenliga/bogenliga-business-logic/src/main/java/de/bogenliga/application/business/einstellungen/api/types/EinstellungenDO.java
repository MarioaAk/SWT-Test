package de.bogenliga.application.business.einstellungen.api.types;

import java.time.OffsetDateTime;
import java.util.Objects;
import de.bogenliga.application.business.role.api.types.RoleDO;

/**
 * TODO [AL] class documentation
 *
 * @author Fabio Care, fabio_silas.care@student.reutlingen-university.de
 */
public class EinstellungenDO {

    /**
     * business parameter
     */
    private Long id;

    private String key;
    private String value;


    /**
     * Constructor with optional parameters
     */
    public EinstellungenDO() {
        // empty constructor
    }

    /**
     * Constructor with mandatory parameters
     */
    public EinstellungenDO(final Long id, final String key, final String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKey(), getValue());
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleDO)) {
            return false;
        }
        final EinstellungenDO einstellungenDO = (EinstellungenDO) o;
        return  Objects.equals(getId(), einstellungenDO.getId()) &&
                Objects.equals(getKey(), einstellungenDO.getKey()) &&
                Objects.equals(getValue(), einstellungenDO.getValue());
    }

}
