package ba.etf.rma22.projekat

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.AnketaRepository
import ba.etf.rma22.projekat.data.repositories.PitanjeAnketaRepository
import org.hamcrest.Matchers.*


import ba.etf.rma22.projekat.viewmodel.KorisnikViewModel
import org.hamcrest.CoreMatchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

@RunWith(AndroidJUnit4::class)
class MySpirala2AndroidTest {

    @get:Rule
    val intentsTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun prviZadatak(){
        onView(withId(R.id.pager)).perform(swipeLeft())

        onView(withId(R.id.odabirGodina)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirIstrazivanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odabirGrupa)).check(matches(isDisplayed()))
        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(isDisplayed()))

        sleep(400);

        onView(withId(R.id.odabirGodina)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("5")
            )
        ).perform(click())

        onView(withId(R.id.odabirIstrazivanja)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("tesko istrazivanje2")
            )
        ).perform(click())

        onView(withId(R.id.dodajIstrazivanjeDugme)).check(matches(not(isEnabled())))

        onView(withId(R.id.odabirGrupa)).perform(click())
        Espresso.onData(
            CoreMatchers.allOf(
                CoreMatchers.`is`(CoreMatchers.instanceOf(String::class.java)),
                CoreMatchers.`is`("grupica 2")
            )
        ).perform(click())

        onView(withId(R.id.dodajIstrazivanjeDugme)).perform(click())

        sleep(400)

        onView(withId(R.id.poruka)).check(matches( withText("Uspješno ste upisani u grupu grupica 2 istraživanja tesko istrazivanje2!")))


        onView(withId(R.id.pager)).perform(swipeRight())
        sleep(300)
        onView(withId(R.id.listaAnketa)).check(matches(isDisplayed()))
/*
        onView(withId(R.id.listaAnketa))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(Korisnik.upisaneGrupe.size/2))

 */
       /* val ankete = AnketaRepository.getAll().filter { anketa -> anketa.nazivGrupe == "grupica 2" && anketa.nazivIstrazivanja == "tesko istrazivanje2" }

        UtilTestClass.itemTest(R.id.listaAnketa, ankete[0])

        */

        val ankete = AnketaRepository.getMyAnkete()
        onView(withId(R.id.listaAnketa)).check(UtilTestClass.hasItemCount(ankete.size))
        for (anketa in ankete) {
            UtilTestClass.itemTest(R.id.listaAnketa, anketa)
        }
    }

    @Test
    fun drugiZadatak(){
        val anketa = AnketaRepository.getAll().filter { anketa -> anketa.nazivGrupe == "grupica 1" && anketa.nazivIstrazivanja == "lagano istrazivanje" }
        onView(withId(R.id.listaAnketa))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))
        sleep(600)
        onView(withId(R.id.tekstPitanja)).check(matches(isDisplayed()))
        onView(withId(R.id.odgovoriLista)).check(matches(isDisplayed()))
        onView(withId(R.id.dugmeZaustavi)).check(matches(isDisplayed()))

        onView(withId(R.id.dugmeZaustavi)).check(matches(isClickable()))

        val pitanja = PitanjeAnketaRepository.getPitanja(anketa[4].naziv, anketa[4].nazivIstrazivanja)

        pitanja.forEach { pitanje ->
            onView(AllOf.allOf(isDisplayed(), withId(R.id.tekstPitanja))).check(matches(withText(pitanje.tekst)))
            onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(0).perform(click())
            onView(withId(R.id.pager)).perform(swipeLeft())

            sleep(300)
        }

        onView(withId(R.id.progresTekst)).check(matches(withText("100%")))

        sleep(1000)
        onView(withId(R.id.dugmePredaj)).check(matches(isEnabled()))

        onView(withId(R.id.dugmePredaj)).perform(click())

        sleep(800)
        onView(allOf(withId(R.id.poruka), withText("Završili ste anketu Anketa 5 u okviru istraživanja lagano istrazivanje")))

        onView(withId(R.id.pager)).perform(swipeRight())

        sleep(300)

        onView(withId(R.id.listaAnketa))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click()))
        sleep(300)

        val pitanja2 = PitanjeAnketaRepository.getPitanja(anketa[4].naziv, anketa[4].nazivIstrazivanja)

        sleep(900)
        pitanja2.forEach { pitanje ->
            onView(AllOf.allOf(isDisplayed(), withId(R.id.tekstPitanja))).check(matches(withText(pitanje.tekst)))
            onData(anything()).inAdapterView(withId(R.id.odgovoriLista)).atPosition(1).perform(click())
            onView(withId(R.id.pager)).perform(swipeLeft())

            sleep(400)
        }

        onView(withId(R.id.dugmePredaj)).check(matches(not(isEnabled())))
        sleep(300)
        onView(withId(R.id.pager)).perform(swipeRight())
        sleep(300)
        onView(withId(R.id.dugmeZaustavi)).perform(click())
        sleep(300)
        onView(withId(R.id.listaAnketa)).check(matches(isDisplayed()))


    }

}