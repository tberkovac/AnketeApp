package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.OdgovorResponse
import ba.etf.rma22.projekat.data.repositories.OdgovorRepository
import ba.etf.rma22.projekat.view.AnketaListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KFunction4

class OdgovorViewModel (private val mContext : Context){
    private  val takeAnketaViewModel = TakeAnketaViewModel()

    val scope = CoroutineScope(Job() + Dispatchers.Main)

     fun postaviOdgovorAnketa(pokusajRjesavanja : AnketaTaken, idPitanje:Int, odgovorIndex:Int, onSuccess : (promjena : Boolean, updateovaniPokusaj : AnketaTaken)->Unit) {
        scope.launch {
            val prethodniProgres = pokusajRjesavanja.progres
            val result = OdgovorRepository.postaviOdgovorAnketa(mContext, pokusajRjesavanja.id,idPitanje,odgovorIndex)
            val pokusajRjesavanjaNovi = takeAnketaViewModel.getPocetaAnketa(mContext, pokusajRjesavanja.AnketumId)!!
            onSuccess.invoke(result != prethodniProgres, pokusajRjesavanjaNovi)
        }
    }

    suspend fun getOdgovorResponseAnkete(idAnketa : Int) : List<OdgovorResponse> {
        return OdgovorRepository.getOdgovoriAnketa(mContext, idAnketa)
    }

    fun postotakOdgovorenih(
        anketa: Anketa,
        onSuccess: KFunction4<Int, AnketaListAdapter.AnketaViewHolder, Anketa, AnketaTaken?, Unit>,
        holder: AnketaListAdapter.AnketaViewHolder,
        pokusajRjesavanja: AnketaTaken?
    ) {
        scope.launch {
            val postotak = OdgovorRepository.obracunajProgresZaAnketu(mContext, anketa.id)
            onSuccess.invoke(postotak,holder, anketa, pokusajRjesavanja)
        }
    }
}
