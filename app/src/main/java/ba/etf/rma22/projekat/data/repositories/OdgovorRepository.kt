package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.OdgovorResponse
import ba.etf.rma22.projekat.data.models.SendOdgovor
import kotlin.Exception

object OdgovorRepository {

    suspend fun getOdgovoriAnketa(idAnkete:Int):List<OdgovorResponse> {
        val pokusajRjesavanja = TakeAnketaRepository.getPoceteAnkete()?.find { it.AnketumId == idAnkete }
        var listaUnesenihOdgovora = listOf<OdgovorResponse>()
        if(pokusajRjesavanja != null){
            listaUnesenihOdgovora = ApiConfig.retrofit.getOdgovoriAnketa(AccountRepository.getHash(), pokusajRjesavanja!!.id)
        }
        return listaUnesenihOdgovora
    }

    suspend fun postaviOdgovorAnketa(idAnketaTaken: Int, idPitanje:Int, indexOdgovora:Int) : Int {
        try {
            val pokusajRjesavanja = TakeAnketaRepository.getPokusaj(idAnketaTaken)
            val prethodniProgres = pokusajRjesavanja.progres
            val buduciProgres =
                obracunajBuduciProgresZaAnketuZaokruzeni(pokusajRjesavanja.AnketumId)
            val response = ApiConfig.retrofit.postaviOdgovorAnketa(
                AccountRepository.getHash(), pokusajRjesavanja.id,
                SendOdgovor(indexOdgovora, idPitanje, buduciProgres)
            )
            if (response.code() == 200)
                return buduciProgres
            return prethodniProgres
        }catch (e : Exception){
            return -1
        }
    }

     suspend fun obracunajProgresZaAnketu(idAnekete: Int) : Int{
        val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size
        return ((odgovoriNaAnketiOdgovoreni.toDouble()/brojPitanjaAnkete)*100).toInt()
     }

    suspend fun obracunajBuduciProgresZaAnketuZaokruzeni(idAnekete: Int) : Int{
        var brojOdgovorenihPitanja = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size

        val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

        if(!jeLiSveOdgovoreno)
            brojOdgovorenihPitanja += 1
        return zaokruziProgres((brojOdgovorenihPitanja.toDouble()/brojPitanjaAnkete).toFloat())
    }

    suspend fun obracunajBuduciProgresZaAnketu(idAnekete: Int) : Int{
        var brojOdgovorenihPitanja = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size

        val jeLiSveOdgovoreno = brojOdgovorenihPitanja == brojPitanjaAnkete

        if(!jeLiSveOdgovoreno)
            brojOdgovorenihPitanja += 1
        return ((brojOdgovorenihPitanja.toDouble()/brojPitanjaAnkete)*100).toInt()
    }

    suspend fun obracunajProgresZaAnketuZaokruzeni(idAnekete: Int) : Int{
        val odgovoriNaAnketiOdgovoreni = getOdgovoriAnketa(idAnekete).size
        val brojPitanjaAnkete = PitanjeAnketaRepository.getPitanja(idAnekete).size
        return zaokruziProgres((odgovoriNaAnketiOdgovoreni.toDouble()/brojPitanjaAnkete).toFloat())
    }

    private fun zaokruziProgres(progres: Float): Int {
        var rez : Int = (progres*10).toInt()
        if(rez % 2 != 0) rez += 1
        rez *= 10
        return rez
    }
}