package de.bogenliga.application.business.Passe.api.types;

import java.time.OffsetDateTime;
import de.bogenliga.application.common.component.types.CommonDataObject;

/**
 * Contains the values of the Passe business entity.
 * @author Kay Scheerer
 */
public class PasseDO extends CommonDataObject {
    private static final long serialVersionUID = 4713470931564147295L;

    private Long id;
    private Long passeMannschaftId;
    private Long passeWettkampfId;
    private Long passeMatchNr;
    private Long passeLfdnr;
    private Long passeDsbMitgliedId;

    private Integer pfeil1;
    private Integer pfeil2;
    private Integer pfeil3;
    private Integer pfeil4;
    private Integer pfeil5;
    private Integer pfeil6;


     public PasseDO(Long id, Long passeMannschaftId, Long passeWettkampfId, Long passeMatchNr, Long passeLfdnr,
        Long passeDsbMitgliedId, Integer pfeil1, Integer pfeil2, Integer pfeil3, Integer pfeil4, Integer pfeil5, Integer pfeil6, final OffsetDateTime createdAtUtc,
                    final Long createdByUserId, final OffsetDateTime lastModifiedUtc,
                    final Long lastModifiedByUserId, final Long version){
        this.id = id;
        this.passeMannschaftId = passeMannschaftId;
        this.passeWettkampfId = passeWettkampfId;
        this.passeMatchNr = passeMatchNr;
        this.passeLfdnr = passeLfdnr;
        this.passeDsbMitgliedId = passeDsbMitgliedId;
        this.pfeil1 = pfeil1;
        this.pfeil2 = pfeil2;
        this.pfeil3 = pfeil3;
        this.pfeil4 = pfeil4;
        this.pfeil5 = pfeil5;
        this.pfeil6 = pfeil6;

        this.createdAtUtc = createdAtUtc;
        this.createdByUserId = createdByUserId;
        this.lastModifiedAtUtc = lastModifiedUtc;
        this.lastModifiedByUserId = lastModifiedByUserId;
        this.version = version;
    }


    public PasseDO(Long id, Long passeMannschaftId, Long passeWettkampfId, Long passeMatchNr, Long passeLfdnr,
                   Long passeDsbMitgliedId, Integer pfeil1, Integer pfeil2, Integer pfeil3, Integer pfeil4, Integer pfeil5, Integer pfeil6) {
        this.id = id;
        this.passeMannschaftId = passeMannschaftId;
        this.passeWettkampfId = passeWettkampfId;
        this.passeMatchNr = passeMatchNr;
        this.passeLfdnr = passeLfdnr;
        this.passeDsbMitgliedId = passeDsbMitgliedId;
        this.pfeil1 = pfeil1;
        this.pfeil2 = pfeil2;
        this.pfeil3 = pfeil3;
        this.pfeil4 = pfeil4;
        this.pfeil5 = pfeil5;
        this.pfeil6 = pfeil6;
    }


    public Integer getPfeil1() {
        return pfeil1;
    }


    public void setPfeil1(Integer pfeil1) {
        this.pfeil1 = pfeil1;
    }


    public Integer getPfeil2() {
        return pfeil2;
    }


    public void setPfeil2(Integer pfeil2) {
        this.pfeil2 = pfeil2;
    }


    public Integer getPfeil3() {
        return pfeil3;
    }


    public void setPfeil3(Integer pfeil3) {
        this.pfeil3 = pfeil3;
    }


    public Integer getPfeil4() {
        return pfeil4;
    }


    public void setPfeil4(Integer pfeil4) {
        this.pfeil4 = pfeil4;
    }


    public Integer getPfeil5() {
        return pfeil5;
    }


    public void setPfeil5(Integer pfeil5) {
        this.pfeil5 = pfeil5;
    }


    public Integer getPfeil6() {
        return pfeil6;
    }


    public void setPfeil6(Integer pfeil6) {
        this.pfeil6 = pfeil6;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public Long getPasseMannschaftId() {
        return passeMannschaftId;
    }


    public void setPasseMannschaftId(Long passeMannschaftId) {
        this.passeMannschaftId = passeMannschaftId;
    }


    public Long getPasseWettkampfId() {
        return passeWettkampfId;
    }


    public void setPasseWettkampfId(Long passeWettkampfId) {
        this.passeWettkampfId = passeWettkampfId;
    }


    public Long getPasseMatchNr() {
        return passeMatchNr;
    }


    public void setPasseMatchNr(Long passeMatchNr) {
        this.passeMatchNr = passeMatchNr;
    }


    public Long getPasseLfdnr() {
        return passeLfdnr;
    }


    public void setPasseLfdnr(Long passeLfdnr) {
        this.passeLfdnr = passeLfdnr;
    }


    public Long getPasseDsbMitgliedId() {
        return passeDsbMitgliedId;
    }


    public void setPasseDsbMitgliedId(Long passeDsbMitgliedId) {
        this.passeDsbMitgliedId = passeDsbMitgliedId;
    }

}