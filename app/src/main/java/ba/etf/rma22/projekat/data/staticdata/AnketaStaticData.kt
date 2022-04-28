package ba.etf.rma22.projekat.data

import ba.etf.rma22.projekat.data.models.Anketa
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


object AnketaStaticData {

    private var date0 : Date
    private var date : Date
    private var date2 : Date
    private var date3 : Date
    private var date4 : Date
    private var date5 : Date
    private var date6 : Date


    init {
        var cal: Calendar = Calendar.getInstance()

        cal.set(2021,7,20)
        date0 = cal.time
        cal.set(2022,3,10)
        date = cal.time
        cal.set(2022, 5, 10)
        date2 = cal.time
        cal.set(2022, 5, 19)
         date3 = cal.time
        cal.set(2022,5,20)
         date4 = cal.time
        cal.set(2022,6,20)
         date5 = cal.time
        cal.set(2022,7,20)
         date6 = cal.time
    }

    var sveAnkete: MutableList<Anketa> = mutableListOf(
        Anketa(
            "Anketa 1", "lagano istrazivanje", date, date2, null, 10,
            "grupica 1", 0.1f
        ), //moze se jos uvijek radit treba bit zelena
        Anketa(
            "Anketa 2", "lagano istrazivanje", date5, date6, null, 10,
            "grupica 1", 0.2f
        ), //moci ce se raditi u buducnosti, treba bit zuta
        Anketa(
            "Anketa 3", "lagano istrazivanje", date, date2, date, 10,
            "grupica 1", 1f
        ), //anketa uradjena, treba bit plava
        Anketa(
            "Anketa 4", "lagano istrazivanje", date0, date, null, 10,
            "grupica 1", 0.4f
        ), //istekla, treba bit crvena
        Anketa(
            "Anketa 5", "lagano istrazivanje", date, date2, null, 10,
            "grupica 2", 0.3f
        ), //moze se jos uvijek radit treba bit zelena
        Anketa(
            "Anketa 6", "lagano istrazivanje", date, date2, null, 10,
            "grupica 3", 0.4f
        ), //moze se jos uvijek radit treba bit zelena

        Anketa(
            "Anketa 7", "lagano istrazivanje2", date2, date3, null, 10,
            "grupica 1", 0.4f
        ), // treba bit zuto
        Anketa(
            "Anketa 8", "lagano istrazivanje2", date2, date3, null, 10,
            "grupica 2", 0.3f
        ), //treba biti zuto
        Anketa(
            "Anketa 9", "lagano istrazivanje2", date2, date3, null, 10,
            "grupica 3", 0.7f
        ), //treba biti zuto


        Anketa(
            "Anketa 10", "zadovoljavajuce istrazivanje", date2, date3, null, 10,
            "grupica 1", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 11", "zadovoljavajuce istrazivanje", date2, date3, null, 10,
            "grupica 2", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 12", "zadovoljavajuce istrazivanje", date2, date3, null, 10,
            "grupica 3", 0.1f
        ), //treba biti zuto


        Anketa(
            "Anketa 13", "zadovoljavajuce istrazivanje2", date2, date3, null, 10,
            "grupica 1", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 14", "zadovoljavajuce istrazivanje2", date2, date3, null, 10,
            "grupica 2", 0.2f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 15", "zadovoljavajuce istrazivanje2", date2, date3, null, 10,
            "grupica 3", 0.3f
        ), //treba biti zuto

        Anketa(
            "Anketa 16", "srednje tesko istrazivanje", date2, date3, null, 10,
            "grupica 1", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 17", "srednje tesko istrazivanje", date3, date6, null, 10,
            "grupica 2", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 18", "srednje tesko istrazivanje", date3, date6, null, 10,
            "grupica 3", 0.1f
        ), //treba biti zuto

        Anketa(
            "Anketa 19", "srednje tesko istrazivanje2", date2, date3, null, 10,
            "grupica 1", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 20", "srednje tesko istrazivanje2", date3, date6, null, 10,
            "grupica 2", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 21", "srednje tesko istrazivanje2", date3, date6, null, 10,
            "grupica 3", 0.4f
        ), //treba biti zuto

        Anketa(
            "Anketa 22", "zanimljivo istrazivanje", date2, date3, null, 10,
            "grupica 1", 0.9f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 23", "zanimljivo istrazivanje", date2, date3, null, 10,
            "grupica 2", 0.9f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 24", "zanimljivo istrazivanje", date2, date3, null, 10,
            "grupica 3", 0.2f
        ), //treba biti ispunjena tj plava

        Anketa(
            "Anketa 25", "zanimljivo istrazivanje2", date2, date3, null, 10,
            "grupica 1", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 26", "zanimljivo istrazivanje2", date2, date3, null, 10,
            "grupica 2", 0.6f
        ), //treba biti ispunjena tj plava
        Anketa(
            "Anketa 27", "zanimljivo istrazivanje2", date2, date3, null, 10,
            "grupica 3", 0.6f
        ), //treba biti ispunjena tj plava


        Anketa(
            "Anketa 28", "tesko istrazivanje", date5, date6, null, 10,
            "grupica 1", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 29", "tesko istrazivanje", date5, date6, null, 10,
            "grupica 2", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 30", "tesko istrazivanje", date5, date6, null, 10,
            "grupica 3", 0.4f
        ), //treba biti zuto

        Anketa(
            "Anketa 31", "tesko istrazivanje2", date5, date6, null, 10,
            "grupica 1", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 32", "tesko istrazivanje2", date5, date6, null, 10,
            "grupica 2", 0.4f
        ), //treba biti zuto
        Anketa(
            "Anketa 33", "tesko istrazivanje2", date5, date6, null, 10,
            "grupica 3", 0.4f
        ), //treba biti zuto
    )

    fun dajAnketeStatic(): MutableList<Anketa> {
        return sveAnkete
    }

}