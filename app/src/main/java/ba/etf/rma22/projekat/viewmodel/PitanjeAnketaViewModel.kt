package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import android.util.Log
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PitanjeAnketaViewModel {

    val scope = CoroutineScope(Job() + Dispatchers.Main)

     fun getPitanja(context:Context, anketa: Anketa, onSuccess: (anketa: Anketa,pitanja : List<Pitanje>)->Unit){
        scope.launch {
            Log.v("ANKETAID", anketa.toString())
            var result = PitanjeAnketaRepository.getPitanja(context, anketa.id)
            Log.v("NEFILTRIRANAPITANJA", result.toString())
            var uniquePitanja = result.toSet().toList()
            Log.v("filtriranapitanja", uniquePitanja.toString())
            onSuccess.invoke(anketa,uniquePitanja)
        }

    }
}