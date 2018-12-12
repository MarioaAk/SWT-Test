package de.bogenliga.application.business.lizenz.impl.dao;

import java.util.Collections;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import de.bogenliga.application.business.lizenz.entity.LizenzBE;
import de.bogenliga.application.common.component.dao.BasicDAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Rahul Pöse
 * @see <a href="http://joel-costigliola.github.io/assertj/">
 * AssertJ: Fluent assertions for java</a>
 * @see <a href="https://junit.org/junit4/">
 * JUnit4</a>
 * @see <a href="https://site.mockito.org/">
 * Mockito</a>
 * @see <a href="http://www.vogella.com/tutorials/Mockito/article.html">Using Mockito with JUnit 4</a>
 */
@SuppressWarnings({"pmd-unit-tests:JUnitTestsShouldIncludeAssert", "squid:S2187"})
public class LizenzBasicDAOTest {

    private long LIZENZID = 0;
    private String LIZENZNUMMER = "WT1234567";
    private long LIZENZREGIONID = 1;
    private long LIZENZDSBMITGLIEDID = 71;
    private String LIZENZTYP = "Liga";
    private long LIZENZDISZIPLINID = 0;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    private BasicDAO basicDao;
    @InjectMocks
    private LizenzDAO underTest;


     @Test
     public void findAll() {
     // prepare test data
     final LizenzBE expectedBE = getLizenzBE();

     // configure mocks
     when(basicDao.selectEntityList(any(), any(), any())).thenReturn(Collections.singletonList(expectedBE));

     // call test method
     final List<LizenzBE> actual = underTest.findAll();

     // assert result
     assertThat(actual)
     .isNotNull()
     .isNotEmpty()
     .hasSize(1);

     assertThat(actual.get(0)).isNotNull();

     assertThat(actual.get(0).getLizenzDisziplinId())
     .isEqualTo(expectedBE.getLizenzDisziplinId());
     assertThat(actual.get(0).getLizenzDsbMitgliedId())
     .isEqualTo(expectedBE.getLizenzDsbMitgliedId());
     assertThat(actual.get(0).getLizenzId())
     .isEqualTo(expectedBE.getLizenzId());
     assertThat(actual.get(0).getLizenznummer())
     .isEqualTo(expectedBE.getLizenznummer());
     assertThat(actual.get(0).getLizenzRegionId())
     .isEqualTo(expectedBE.getLizenzRegionId());
     assertThat(actual.get(0).getLizenztyp())
     .isEqualTo(expectedBE.getLizenztyp());

     // verify invocations
     verify(basicDao).selectEntityList(any(), any(), any());
     }



     @Test
     public void findById() {
     // prepare test data
     final KampfrichterBE expectedBE = new KampfrichterBE();
     expectedBE.setKampfrichterUserId(USERID);
     expectedBE.setKampfrichterWettkampfId(WETTKAMPFID);

     // configure mocks
     when(basicDao.selectSingleEntity(any(), any(), any())).thenReturn(expectedBE);

     // call test method
     final KampfrichterBE actual = underTest.findById(USERID);

     // assert result
     assertThat(actual).isNotNull();

     assertThat(actual.getKampfrichterUserId())
     .isEqualTo(expectedBE.getKampfrichterUserId());
     assertThat(actual.getKampfrichterWettkampfId())
     .isEqualTo(expectedBE.getKampfrichterWettkampfId());


     // verify invocations
     verify(basicDao).selectSingleEntity(any(), any(), any());
     }


    @Test
    public void create() {
        // prepare test data
        final KampfrichterBE input = new KampfrichterBE();
        input.setKampfrichterUserId(USERID);
        input.setKampfrichterWettkampfId(WETTKAMPFID);

        // configure mocks
        when(basicDao.insertEntity(any(), any())).thenReturn(input);

        // call test method
        final KampfrichterBE actual = underTest.create(input, USER);

        // assert result
        assertThat(actual).isNotNull();

        assertThat(actual.getKampfrichterUserId())
                .isEqualTo(input.getKampfrichterUserId());
        assertThat(actual.getKampfrichterWettkampfId())
                .isEqualTo(input.getKampfrichterWettkampfId());

        // verify invocations
        verify(basicDao).insertEntity(any(), eq(input));
    }


    @Test
    public void update() {
        // prepare test data
        final KampfrichterBE input = new KampfrichterBE();
        input.setKampfrichterUserId(USERID);
        input.setKampfrichterWettkampfId(WETTKAMPFID);

        // configure mocks
        when(basicDao.updateEntity(any(), any(), any())).thenReturn(input);

        // call test method
        final KampfrichterBE actual = underTest.update(input, USER);

        // assert result
        assertThat(actual).isNotNull();

        assertThat(actual.getKampfrichterUserId())
                .isEqualTo(input.getKampfrichterUserId());
        assertThat(actual.getKampfrichterWettkampfId())
                .isEqualTo(input.getKampfrichterWettkampfId());

        // verify invocations
        verify(basicDao).updateEntity(any(), eq(input), any());
    }


    @Test
    public void delete() {
        // prepare test data
        final KampfrichterBE input = new KampfrichterBE();
        input.setKampfrichterUserId(USERID);
        input.setKampfrichterWettkampfId(WETTKAMPFID);

        // configure mocks

        // call test method
        underTest.delete(input, USER);

        // assert result

        // verify invocations
        verify(basicDao).deleteEntity(any(), eq(input), any());
    }
}
