package ba.etf.rma22.projekat.data

import java.util.*


fun dajAnketeStatic(): List<Anketa>{

    val cal: Calendar = Calendar.getInstance()
    cal.set(2022,3,10)
    val date: Date = cal.time
    cal.set(2022, 5, 10)
    val date2: Date = cal.time
    cal.set(2022, 5, 19)
    val date3: Date = cal.time
    cal.set(2022,3,20)
    val date4: Date = cal.time

    return listOf(
        Anketa("Anketa 1","Istrazivanje 1", date , date2, null,10,
        "prva grupica", 0.4f), //moze se jos uvijek radit treba bit zelena
        Anketa("Anketa 2","Istrazivanje 2", date2 , date3, null,10,
            "druga grupica", 0.6f), //treva biti zuto
        Anketa("Anketa 3","Istrazivanje 3", date2 , date3, date2,10,
            "treca grupica", 0.6f), //treba biti ispunjena tj plava
        Anketa("Anketa 4","Istrazivanje 4", date , date4, null,10,
            "cetvrta grupica", 0.4f), //treba biti zuto
        Anketa("Anketa 5","Istrazivanje 5", date2 , date3, date2,10,
            "peta grupica", 0.6f), //treba biti ispunjena tj plava
        Anketa("Anketa 6","Istrazivanje 6", date , date4, null,10,
            "sesta grupica", 0.4f), //treba biti zuto
        Anketa("Anketa 7","Istrazivanje 7", date2 , date3, date2,10,
            "sedma grupica", 0.6f), //treba biti ispunjena tj plava
        Anketa("Anketa 8","Istrazivanje 8", date , date4, null,10,
            "osma grupica", 0.4f), //treba biti zuto
    )
}