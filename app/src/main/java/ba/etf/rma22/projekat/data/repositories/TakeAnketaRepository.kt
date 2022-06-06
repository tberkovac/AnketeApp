package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.data.models.AnketaTaken

object TakeAnketaRepository {

    suspend fun zapocniAnketu(idAnkete: Int): AnketaTaken? {
        val result =  ApiConfig.retrofit.takeAnketa(AccountRepository.getHash(), idAnkete)

        if(result.body()== null)
            return null
        return result.body()
    }

    suspend fun getPoceteAnkete() : List<AnketaTaken>? {
        val result = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
        if(result.isEmpty())
            return null
        return result
    }

    suspend fun getPokusaj(idAnketaTaken: Int) : AnketaTaken {
        var sviPokusaji = ApiConfig.retrofit.getTakenAnkete(AccountRepository.getHash())
        sviPokusaji = sviPokusaji.filter { it.id == idAnketaTaken }
        return sviPokusaji[0]
    }
}