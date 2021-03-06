package de.bogenliga.application.business.wettkampf.impl.business;


import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.sql.Date;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import de.bogenliga.application.business.wettkampf.api.types.WettkampfDO;
import de.bogenliga.application.business.wettkampf.impl.dao.WettkampfDAO;
import de.bogenliga.application.business.wettkampf.impl.entity.WettkampfBE;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Daniel Schott daniel.schott@student.reutlingen-university.de
 */
public class WettkampfComponentImplTest {

    private static final long user_Id = 13;
    private static final OffsetDateTime created_At_Utc = OffsetDateTime.now();
    private static final long version = 1234;

    private static final long wettkampf_Id = 322;
    private static final long wettkampf_Veranstaltung_Id = 0;
    private static final Date wettkampf_Datum = new Date(20190521L);
    private static final String wettkampf_Strasse = "Reutlingerstr. 6";
    private static final String wettkampf_Plz = "72764";
    private static final String wettkampf_Ortsname = "Reutlingen";
    private static final String wettkampf_Ortsinfo = "Im Keller";
    private static final String wettkampf_Beginn = "8:00";
    private static final long wettkampf_Tag = 8;
    private static final long wettkampf_Disziplin_Id = 0;
    private static final long wettkampf_Wettkampftyp_Id = 1;
    private static final long wettkampf_kampfrichter_Id = 8;
    private static final long wettkampf_Ausrichter = 8;


    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private WettkampfDAO wettkampfDAO;
    @InjectMocks
    private WettkampfComponentImpl underTest;
    @Captor
    private ArgumentCaptor<WettkampfBE> wettkampfBEArgumentCaptor;


    /***
     * Utility methods for creating business entities/data objects.
     * Also used by other test classes.
     */
    public static WettkampfBE getWettkampfBE() {
        final WettkampfBE expectedBE = new WettkampfBE();
        expectedBE.setId(wettkampf_Id);
        expectedBE.setVeranstaltungsId(wettkampf_Veranstaltung_Id);
        expectedBE.setDatum(wettkampf_Datum);
        expectedBE.setWettkampfStrasse(wettkampf_Strasse);
        expectedBE.setWettkampfPlz(wettkampf_Plz);
        expectedBE.setWettkampfOrtsname(wettkampf_Ortsname);
        expectedBE.setWettkampfOrtsinfo(wettkampf_Ortsinfo);
        expectedBE.setWettkampfBeginn(wettkampf_Beginn);
        expectedBE.setWettkampfTag(wettkampf_Tag);
        expectedBE.setWettkampfDisziplinId(wettkampf_Disziplin_Id);
        expectedBE.setWettkampfTypId(wettkampf_Wettkampftyp_Id);
        expectedBE.setKampfrichterId(wettkampf_kampfrichter_Id);
        expectedBE.setWettkampfAusrichter(wettkampf_Ausrichter);
        expectedBE.setWettkampfAusrichter(wettkampf_Ausrichter);

        return expectedBE;
    }


    public static WettkampfDO getWettkampfDO() {
        return new WettkampfDO(wettkampf_Id,
                wettkampf_Veranstaltung_Id,
                wettkampf_Datum,
                wettkampf_Strasse,
                wettkampf_Plz,
                wettkampf_Ortsname,
                wettkampf_Ortsinfo,
                wettkampf_Beginn,
                wettkampf_Tag,
                wettkampf_Disziplin_Id,
                wettkampf_Wettkampftyp_Id,
                created_At_Utc,
                user_Id,
                version,
                wettkampf_kampfrichter_Id,
                wettkampf_Ausrichter
        );
    }


    @Test
    public void findAll() {
        // prepare test data
        final WettkampfBE expectedBE = getWettkampfBE();
        final List<WettkampfBE> expectedBEList = Collections.singletonList(expectedBE);

        // configure mocks
        when(wettkampfDAO.findAll()).thenReturn(expectedBEList);


        // call test method
        final List<WettkampfDO> actual = underTest.findAll();


        // assert result
        assertThat(actual)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        assertThat(actual.get(0)).isNotNull();

        assertThat(actual.get(0).getId())
                .isEqualTo(expectedBE.getId());
        assertThat(actual.get(0).getWettkampfVeranstaltungsId())
                .isEqualTo(expectedBE.getVeranstaltungsId());
        assertThat(actual.get(0).getWettkampfDatum())
                .isEqualTo(expectedBE.getDatum());
        assertThat(actual.get(0).getWettkampfStrasse())
                .isEqualTo(expectedBE.getWettkampfStrasse());
        assertThat(actual.get(0).getWettkampfPlz())
                .isEqualTo(expectedBE.getWettkampfPlz());
        assertThat(actual.get(0).getWettkampfOrtsname())
                .isEqualTo(expectedBE.getWettkampfOrtsname());
        assertThat(actual.get(0).getWettkampfOrtsinfo())
                .isEqualTo(expectedBE.getWettkampfOrtsinfo());
        assertThat(actual.get(0).getWettkampfTag())
                .isEqualTo(expectedBE.getWettkampfTag());
        assertThat(actual.get(0).getWettkampfDisziplinId())
                .isEqualTo(expectedBE.getWettkampfDisziplinId());
        assertThat(actual.get(0).getWettkampfTypId())
                .isEqualTo(expectedBE.getWettkampfTypId());
        assertThat(actual.get(0).getKampfrichterID())
                .isEqualTo(expectedBE.getKampfrichterId());
        assertThat(actual.get(0).getWettkampfAusrichter())
                .isEqualTo(expectedBE.getWettkampfAusrichter());

        // verify invocations
        verify(wettkampfDAO).findAll();
    }


    @Test
    public void findById() {
        // prepare test data
        final WettkampfBE expectedBE = getWettkampfBE();

        // configure mocks
        when(wettkampfDAO.findById(wettkampf_Id)).thenReturn(expectedBE);

        // call test method
        final WettkampfDO actual = underTest.findById(wettkampf_Id);

        // assert result
        assertThat(actual).isNotNull();

        assertThat(actual.getId())
                .isEqualTo(expectedBE.getId());

        // verify invocations
        verify(wettkampfDAO).findById(wettkampf_Id);
    }


    @Test
    public void create() {
        // prepare test data
        final WettkampfDO input = getWettkampfDO();

        final WettkampfBE expectedBE = getWettkampfBE();

        // configure mocks
        when(wettkampfDAO.create(any(WettkampfBE.class), anyLong())).thenReturn(expectedBE);

        // call test method
        final WettkampfDO actual = underTest.create(input, user_Id);

        // assert result
        assertThat(actual).isNotNull();

        assertThat(actual.getId())
                .isEqualTo(input.getId());

        // verify invocations
        verify(wettkampfDAO).create(wettkampfBEArgumentCaptor.capture(), anyLong());

        final WettkampfBE persistedBE = wettkampfBEArgumentCaptor.getValue();

        assertThat(persistedBE).isNotNull();

        assertThat(persistedBE.getId())
                .isEqualTo(input.getId());
    }


    @Test
    public void delete() {
        // prepare test data
        final WettkampfDO input = getWettkampfDO();

        final WettkampfBE expectedBE = getWettkampfBE();

        // configure mocks

        // call test method
        underTest.delete(input, user_Id);

        // assert result

        // verify invocations
        verify(wettkampfDAO).delete(wettkampfBEArgumentCaptor.capture(), anyLong());

        final WettkampfBE persistedBE = wettkampfBEArgumentCaptor.getValue();

        assertThat(persistedBE).isNotNull();

        assertThat(persistedBE.getId())
                .isEqualTo(input.getId());
    }
}
