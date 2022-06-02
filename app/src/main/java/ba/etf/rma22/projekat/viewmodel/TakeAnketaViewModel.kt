package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class TakeAnketaViewModel  {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun zapocniAnketu(idAnkete:Int ) : AnketaTaken {
          val mozdaZapocetaAnketa = getPocetaAnketa(idAnkete)
        if(mozdaZapocetaAnketa == null)
            return  TakeAnketaRepository.zapocniAnketu(idAnkete).body()!!
        else
            return mozdaZapocetaAnketa
    }

    suspend fun getPoceteAnkete() : List<AnketaTaken> {
        return TakeAnketaRepository.getPoceteAnkete()
    }

    suspend fun getPocetaAnketa(idAnkete : Int) : AnketaTaken? {
        val pocete = getPoceteAnkete()
        for (anketaTaken in pocete) {
            if(anketaTaken.anketumId == idAnkete)
                return anketaTaken
        }
        return null
    }
}