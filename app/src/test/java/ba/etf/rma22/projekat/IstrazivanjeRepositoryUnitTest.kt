/*package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository
import org.junit.Test

import org.junit.Assert.*

class IstrazivanjeRepositoryUnitTest {

    @Test
    fun getAllTest() {
        val svaIstrazivanja = IstrazivanjeRepository.getAll()
        assertEquals(svaIstrazivanja.size, 10)
    }

    @Test
    fun getUpisaniTest() {
        val upisanaIstrazivanja = IstrazivanjeRepository.getUpisani()
        assertEquals(upisanaIstrazivanja.size, 2)
    }

    @Test
    fun getIstrazivanjaByGodinaTest() {
        val istrazivanjaNaPrvojGodini = IstrazivanjeRepository.getIstrazivanjeByGodina(1)
        assertEquals(istrazivanjaNaPrvojGodini.size, 2)
    }

    @Test
    fun getIstrazivanjaByGodinaTest2() {
        val istrazivanjaNaNepostojecojGodini = IstrazivanjeRepository.getIstrazivanjeByGodina(0)
        assertEquals(istrazivanjaNaNepostojecojGodini.size, 0)
    }

}

 */