package ba.etf.rma22.projekat.viewmodel

import android.util.Log
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeIGrupaRepository

class IstrazivanjeIGrupaViewModel {
    suspend fun getAllIstrazivanja() : List<Istrazivanje> {
        return IstrazivanjeIGrupaRepository.getIstrazivanja()
    }

    suspend fun getAllIstrazivanjaByGodina(godina : Int) : List<Istrazivanje> {
        Log.v("Pozvana ", "getAllIstrazivanjaByGodina")
        val zaVratit = IstrazivanjeIGrupaRepository.getIstrazivanja().filter { istrazivanje -> istrazivanje.godina == godina  }
        Log.v("Istrazivanja su", zaVratit.toString())
        return zaVratit
    }

    suspend fun getGrupeByIstrazivanje(idIstrazivanje: Int) : List<Grupa> {
        return IstrazivanjeIGrupaRepository.getGrupeByIstrazivanje(idIstrazivanje)
    }

    suspend fun upisiUGrupu(idGrupa:Int):Boolean {
        return IstrazivanjeIGrupaRepository.upisiUGrupu(idGrupa)
    }

    suspend fun getUpisaneGrupe():List<Grupa> {
        return IstrazivanjeIGrupaRepository.getUpisaneGrupe()
    }

    suspend fun getIstrazivanjeByGroupId(idGrupa: Int) : Istrazivanje {
        return IstrazivanjeIGrupaRepository.getIstrazivanjeByGrupaId(idGrupa)
    }
}