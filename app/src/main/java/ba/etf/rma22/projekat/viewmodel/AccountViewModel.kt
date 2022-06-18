package ba.etf.rma22.projekat.viewmodel

import android.content.Context
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AccountViewModel {
    val scope = CoroutineScope(Job() + Dispatchers.Main)

    fun postaviHash(acHash:String){
        scope.launch {
            AccountRepository.postaviHash(acHash)
        }
    }
}