package ba.etf.rma22.projekat.viewmodel

import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.OdgovorResponse
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OdgovorViewModel {
    private  val takeAnketaViewModel = TakeAnketaViewModel()

    val scope = CoroutineScope(Job() + Dispatchers.Main)

     fun postaviOdgovorAnketa(pokusajRjesavanja : AnketaTaken, idPitanje:Int, odgovorIndex:Int, onSuccess : (promjena : Boolean, updateovaniPokusaj : AnketaTaken)->Unit) {
        scope.launch {
            val prethodniProgres = pokusajRjesavanja.progres
            val result = OdgovorRepository.postaviOdgovorAnketa(pokusajRjesavanja,idPitanje,odgovorIndex)
            val pokusajRjesavanjaNovi = takeAnketaViewModel.getPocetaAnketa(pokusajRjesavanja.anketumId)!!


            onSuccess.invoke(result != prethodniProgres, pokusajRjesavanjaNovi)
        }
    }

    suspend fun getOdgovorResponseAnkete(idAnketa : Int) : List<OdgovorResponse> {
        return OdgovorRepository.getOdgovoriAnketa(idAnketa)
    }
}