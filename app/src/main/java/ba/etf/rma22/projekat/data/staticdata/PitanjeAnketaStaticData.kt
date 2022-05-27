/*package ba.etf.rma22.projekat.data.staticdata

import ba.etf.rma22.projekat.data.models.PitanjeAnketa

fun dajPitanjeAnkete() : List<PitanjeAnketa>{
    return listOf(
        PitanjeAnketa("pitanje1","Anketa 1","lagano istrazivanje"),
        PitanjeAnketa("pitanje2","Anketa 1","lagano istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 1","lagano istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 1","lagano istrazivanje"),

        PitanjeAnketa("pitanje2","Anketa 2", "lagano istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 2", "lagano istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 2", "lagano istrazivanje"),

        PitanjeAnketa("pitanje1","Anketa 3","lagano istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 3","lagano istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 3","lagano istrazivanje"),

        PitanjeAnketa("pitanje6","Anketa 4","lagano istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 4","lagano istrazivanje"),
        PitanjeAnketa("pitanje10","Anketa 4","lagano istrazivanje"),

        PitanjeAnketa("pitanje9","Anketa 5","lagano istrazivanje"),
        PitanjeAnketa("pitanje8","Anketa 5","lagano istrazivanje"),
        PitanjeAnketa("pitanje7","Anketa 5","lagano istrazivanje"),

        PitanjeAnketa("pitanje1","Anketa 6","lagano istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 6","lagano istrazivanje"),

    //7 8 i 9

        PitanjeAnketa("pitanje1","Anketa 7","lagano istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 7","lagano istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 7","lagano istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 7","lagano istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 8", "lagano istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 8", "lagano istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 8", "lagano istrazivanje2"),
        PitanjeAnketa("pitanje1","Anketa 9","lagano istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 9","lagano istrazivanje2"),
        PitanjeAnketa("pitanje6","Anketa 9","lagano istrazivanje2"),

        //10 11 i 12
        PitanjeAnketa("pitanje10","Anketa 10","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje9","Anketa 10","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje8","Anketa 10","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje7","Anketa 11","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 11", "zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje5","Anketa 11", "zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 12", "zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 12","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje2","Anketa 12","zadovoljavajuce istrazivanje"),
        PitanjeAnketa("pitanje1","Anketa 12","zadovoljavajuce istrazivanje"),

        //13 14 15
        PitanjeAnketa("pitanje10","Anketa 13","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje9","Anketa 13","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje8","Anketa 13","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje7","Anketa 14","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje6","Anketa 14", "zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje5","Anketa 14", "zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 14", "zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 15","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 15","zadovoljavajuce istrazivanje2"),
        PitanjeAnketa("pitanje1","Anketa 15","zadovoljavajuce istrazivanje2"),

        //16 17 18
        PitanjeAnketa("pitanje10","Anketa 16","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje9","Anketa 16","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje8","Anketa 16","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje7","Anketa 17","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 17", "srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje5","Anketa 17", "srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 18", "srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 18","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje2","Anketa 18","srednje tesko istrazivanje"),
        PitanjeAnketa("pitanje1","Anketa 18","srednje tesko istrazivanje"),

        //19 20 i 21
        PitanjeAnketa("pitanje10","Anketa 19","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje9","Anketa 19","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje8","Anketa 19","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje7","Anketa 20","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje6","Anketa 20", "srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje5","Anketa 20", "srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 21", "srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 21","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 21","srednje tesko istrazivanje2"),
        PitanjeAnketa("pitanje1","Anketa 21","srednje tesko istrazivanje2"),

        //22 23 24
        PitanjeAnketa("pitanje10","Anketa 22","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje9","Anketa 22","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje8","Anketa 22","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje7","Anketa 22","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 23", "zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje5","Anketa 23", "zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 23", "zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 24","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje2","Anketa 24","zanimljivo istrazivanje"),
        PitanjeAnketa("pitanje1","Anketa 24","zanimljivo istrazivanje"),

        //25 26 27
        PitanjeAnketa("pitanje10","Anketa 25","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje9","Anketa 25","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje8","Anketa 25","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje7","Anketa 25","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje6","Anketa 26", "zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje5","Anketa 26", "zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 26", "zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 27","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 27","zanimljivo istrazivanje2"),
        PitanjeAnketa("pitanje1","Anketa 27","zanimljivo istrazivanje2"),

        //28 29 30
        PitanjeAnketa("pitanje10","Anketa 28","tesko istrazivanje"),
        PitanjeAnketa("pitanje9","Anketa 28","tesko istrazivanje"),
        PitanjeAnketa("pitanje8","Anketa 28","tesko istrazivanje"),
        PitanjeAnketa("pitanje7","Anketa 29","tesko istrazivanje"),
        PitanjeAnketa("pitanje6","Anketa 29", "tesko istrazivanje"),
        PitanjeAnketa("pitanje5","Anketa 29", "tesko istrazivanje"),
        PitanjeAnketa("pitanje4","Anketa 29", "tesko istrazivanje"),
        PitanjeAnketa("pitanje3","Anketa 30","tesko istrazivanje"),
        PitanjeAnketa("pitanje2","Anketa 30","tesko istrazivanje"),
        PitanjeAnketa("pitanje1","Anketa 30","tesko istrazivanje"),

        //31 32 33
        PitanjeAnketa("pitanje10","Anketa 31","tesko istrazivanje2"),
        PitanjeAnketa("pitanje9","Anketa 31","tesko istrazivanje2"),
        PitanjeAnketa("pitanje8","Anketa 31","tesko istrazivanje2"),
        PitanjeAnketa("pitanje7","Anketa 31","tesko istrazivanje2"),
        PitanjeAnketa("pitanje6","Anketa 32", "tesko istrazivanje2"),
        PitanjeAnketa("pitanje5","Anketa 32", "tesko istrazivanje2"),
        PitanjeAnketa("pitanje4","Anketa 32", "tesko istrazivanje2"),
        PitanjeAnketa("pitanje3","Anketa 33","tesko istrazivanje2"),
        PitanjeAnketa("pitanje2","Anketa 33","tesko istrazivanje2"),
        PitanjeAnketa("pitanje1","Anketa 33","tesko istrazivanje2")
    )
}

 */