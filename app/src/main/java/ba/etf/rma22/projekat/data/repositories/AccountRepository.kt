package ba.etf.rma22.projekat.data.repositories

object AccountRepository {
    private var hash = "19b70587-76b0-4444-a964-fad0a04d426b"

    fun postaviHash(acHash:String):Boolean{
        hash = acHash
        return false
    }

    fun getHash():String{
        return hash
    }
}