package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

class TakeAnketaViewModel  {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun zapocniAnketu(idAnkete:Int ) : AnketaTaken {
        var mozdaZapocetaAnketa : AnketaTaken?
        mozdaZapocetaAnketa= getPocetaAnketa(idAnkete)
        if(mozdaZapocetaAnketa == null && TakeAnketaRepository.isOnline())
            return  TakeAnketaRepository.zapocniAnketu(idAnkete)!!
        else
            return mozdaZapocetaAnketa!!
    }

    fun getPoceteAnkete(onSuccess: (List<AnketaTaken>) -> Unit){
         scope.launch {
             val result =  TakeAnketaRepository.getPoceteAnkete()
             Log.v("*Poceteanketesu", result.toString())
             if (result != null) {
                 onSuccess.invoke(result)
             }
             
         }
    }

    fun obrisiPokusajeDB(){
        scope.launch {
            TakeAnketaRepository.deleteAllPokusajiDB()
        }
    }

    suspend fun getPoceteAnkete() : List<AnketaTaken>?{
        return TakeAnketaRepository.getPoceteAnkete()
    }

    suspend fun getPocetaAnketa(idAnkete : Int) : AnketaTaken? {
        val pocete = getPoceteAnkete() ?: return null
        for (anketaTaken in pocete) {
            if(anketaTaken.AnketumId == idAnkete)
                return anketaTaken
        }
        return null
    }

    fun getPocetaAnketa( idAnkete : Int, onSuccess: (AnketaTaken?) -> Unit) {
        scope.launch {
            val pocete = getPoceteAnkete()
            var trazena : AnketaTaken? = null
            if(pocete == null || pocete.isEmpty()) {
                onSuccess.invoke(trazena)
            }else {
                for (anketaTaken in pocete!!) {
                    if (anketaTaken.AnketumId == idAnkete) {
                        trazena = anketaTaken
                    }
                }
                onSuccess.invoke(trazena)
            }
        }

    }
}