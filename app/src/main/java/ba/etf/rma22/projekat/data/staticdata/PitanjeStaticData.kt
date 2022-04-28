package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.Pitanje

fun dajPitanjaStaticData() : List<Pitanje>{
    return listOf(
        Pitanje("pitanje1", "Da li je voda mokra", listOf("da","ne")),
        Pitanje("pitanje2", "Da li je RMA tezak", listOf("da", "ne")),
        Pitanje("pitanje3", "Koja boja je najljepsa", listOf("plava","zuta", "zelena", "crvena")),
        Pitanje("pitanje4", "Sta studirate", listOf("ETF","PMF","MF")),
        Pitanje("pitanje5", "Odakle ste", listOf("BiH", "CG", "SLO", "SRB")),
        Pitanje("pitanje6", "Koliko rijeka ima u Olovu", listOf("0","1","2","3","5"))
    )
}