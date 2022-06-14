package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.repositories.TakeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction1

class TakeAnketaViewModel  {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

    suspend fun zapocniAnketu(context: Context, idAnkete:Int ) : AnketaTaken {
          val mozdaZapocetaAnketa = getPocetaAnketa(context, idAnkete)
        if(mozdaZapocetaAnketa == null)
            return  TakeAnketaRepository.zapocniAnketu(idAnkete)!!
        else
            return mozdaZapocetaAnketa
    }

    fun getPoceteAnkete(context: Context, onSuccess: KFunction1<List<AnketaTaken>, Unit>){
         scope.launch {
             val result =  TakeAnketaRepository.getPoceteAnkete(context)
             //OBRATIT PAZNJU
             if (result != null) {
                 onSuccess.invoke(result)
             }
         }
    }

    suspend fun getPoceteAnkete(context: Context) : List<AnketaTaken>?{
        return TakeAnketaRepository.getPoceteAnkete(context)
    }

    suspend fun getPocetaAnketa(context: Context, idAnkete : Int) : AnketaTaken? {
        val pocete = getPoceteAnkete(context) ?: return null
        for (anketaTaken in pocete) {
            if(anketaTaken.AnketumId == idAnkete)
                return anketaTaken
        }
        return null
    }

    fun getPocetaAnketa(context: Context, idAnkete : Int, onSuccess: (AnketaTaken?) -> Unit) {
        scope.launch {
            val pocete = getPoceteAnkete(context)
            var trazena : AnketaTaken? = null
            if(pocete == null) onSuccess.invoke(trazena)
            for (anketaTaken in pocete!!) {
                if(anketaTaken.AnketumId == idAnkete){
                    trazena =  anketaTaken
                }
            }
            onSuccess.invoke(trazena)
        }

    }
}