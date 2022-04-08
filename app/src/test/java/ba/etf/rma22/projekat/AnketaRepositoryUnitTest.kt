package ba.etf.rma22.projekat

import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import org.junit.Test

import org.junit.Assert.*

class AnketaRepositoryUnitTest {

    @Test
    fun getMyAnketeTest() {
        val mojeAnkete = AnketaRepository.getMyAnkete()

        assertEquals(mojeAnkete.size,5)
    }

    @Test
    fun getAllTest() {
        val sveAnkete = AnketaRepository.getAll()

        assertEquals(sveAnkete.size, 33)
    }

    @Test
    fun getDoneTest() {
        val sveMojeUradjeneAnkete = AnketaRepository.getDone()

        assertEquals(sveMojeUradjeneAnkete.size, 1)
    }


    @Test
    fun getFutureTest() {
        val sveMojeBuduceAnkete = AnketaRepository.getFuture()

        assertEquals(sveMojeBuduceAnkete.size, 2)
    }

    @Test
    fun getNotTakenTest() {
        val sveNeuradjeneAnkete = AnketaRepository.getNotTaken()

        assertEquals(sveNeuradjeneAnkete.size, 1)
    }

}