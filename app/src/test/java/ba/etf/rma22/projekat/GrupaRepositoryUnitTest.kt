package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import org.hamcrest.Matchers.hasItem
import org.hamcrest.CoreMatchers.`is` as Is
import org.hamcrest.Matchers.not
import org.hamcrest.beans.HasPropertyWithValue.hasProperty


import org.junit.Test

import org.junit.Assert.*

class GrupaRepositoryUnitTest {

    @Test
    fun getGroupsByIstrazivanjeTest1() {
        val grupeLaganoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("lagano istrazivanje")
        assertEquals(grupeLaganoIstrazivanje.size, 3)
        assertThat(grupeLaganoIstrazivanje, hasItem<Anketa>(hasProperty("naziv", Is("grupica 1"))))
        assertThat(grupeLaganoIstrazivanje, hasItem<Anketa>(hasProperty("naziv", Is("grupica 2"))))
        assertThat(grupeLaganoIstrazivanje, hasItem<Anketa>(hasProperty("naziv", Is("grupica 3"))))
        assertThat(grupeLaganoIstrazivanje, not(hasItem<Anketa>(hasProperty("naziv", Is("grupica 4")))))
    }

    @Test
    fun getGroupsByIstrazivanjeTest2() {
        val grupeLaganoIstrazivanje2 = GrupaRepository.getGroupsByIstrazivanje("lagano istrazivanje2")
        assertEquals(grupeLaganoIstrazivanje2.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest3() {
        val grupeZadovoljavajuceIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("zadovoljavajuce istrazivanje")
        assertEquals(grupeZadovoljavajuceIstrazivanje.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest4() {
        val grupeZadovoljavajuceIstrazivanje2 = GrupaRepository.getGroupsByIstrazivanje("zadovoljavajuce istrazivanje2")
        assertEquals(grupeZadovoljavajuceIstrazivanje2.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest5() {
        val grupeSrednjeTeskoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("srednje tesko istrazivanje")
        assertEquals(grupeSrednjeTeskoIstrazivanje.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest6() {
        val grupeSrednjeTeskoIstrazivanje2 = GrupaRepository.getGroupsByIstrazivanje("srednje tesko istrazivanje2")
        assertEquals(grupeSrednjeTeskoIstrazivanje2.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest7() {
        val grupeZanimljivoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("zanimljivo istrazivanje")
        assertEquals(grupeZanimljivoIstrazivanje.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest8() {
        val grupeZanimljivoIstrazivanje2 = GrupaRepository.getGroupsByIstrazivanje("zanimljivo istrazivanje2")
        assertEquals(grupeZanimljivoIstrazivanje2.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest9() {
        val grupeTeskoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("tesko istrazivanje")
        assertEquals(grupeTeskoIstrazivanje.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest10() {
        val grupeTeskoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("tesko istrazivanje")
        assertEquals(grupeTeskoIstrazivanje.size, 3)
    }

    @Test
    fun getGroupsByIstrazivanjeTest11() {
        var grupeTeskoIstrazivanje = GrupaRepository.getGroupsByIstrazivanje("tesko istrazivanje4")
        assertEquals(grupeTeskoIstrazivanje.size, 0)
        grupeTeskoIstrazivanje = grupeTeskoIstrazivanje.plus(Grupa("Testna","Testno istrazivanje"))
        assertEquals(grupeTeskoIstrazivanje.size, 1)
    }


}

