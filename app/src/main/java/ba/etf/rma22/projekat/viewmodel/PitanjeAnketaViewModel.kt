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

     fun getPitanja( anketa: Anketa, onSuccess: (anketa: Anketa,pitanja : List<Pitanje>)->Unit){
        scope.launch {
            var result = PitanjeAnketaRepository.getPitanja(anketa.id)
            var uniquePitanja = result.toSet().toList()
            onSuccess.invoke(anketa,uniquePitanja)
        }

    }
}