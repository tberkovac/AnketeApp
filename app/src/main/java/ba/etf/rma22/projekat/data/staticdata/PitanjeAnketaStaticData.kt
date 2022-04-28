package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.PitanjeAnketa

fun dajPitanjeAnkete() : List<PitanjeAnketa>{
    return listOf(
        PitanjeAnketa("pitanje1","Anketa 1"),
        PitanjeAnketa("pitanje2","Anketa 1"),
        PitanjeAnketa("pitanje3","Anketa 1"),
        PitanjeAnketa("pitanje4","Anketa 1"),
        PitanjeAnketa("pitanje2","Anketa 2"),
        PitanjeAnketa("pitanje3","Anketa 2"),
        PitanjeAnketa("pitanje4","Anketa 2"),
        PitanjeAnketa("pitanje1","Anketa 3"),
        PitanjeAnketa("pitanje3","Anketa 3"),
        PitanjeAnketa("pitanje6","Anketa 3"),
        PitanjeAnketa("pitanje6","Anketa 4"),
        PitanjeAnketa("pitanje3","Anketa 4"),
        PitanjeAnketa("pitanje1","Anketa 4")
    )
}