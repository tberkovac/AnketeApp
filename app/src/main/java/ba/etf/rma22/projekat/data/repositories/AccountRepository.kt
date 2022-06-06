package ba.etf.rma22.projekat.data.repositories

object AccountRepository {
    var acHash = "19b70587-76b0-4444-a964-fad0a04d426b"

    fun postaviHash(acHash:String):Boolean{
        this.acHash = acHash
        return true
    }

    fun getHash():String{
        return acHash
    }
}