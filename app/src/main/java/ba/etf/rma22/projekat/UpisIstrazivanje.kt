package ba.etf.rma22.projekat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository


class UpisIstrazivanje : AppCompatActivity() {
    private lateinit var spinerGodine : Spinner
    private lateinit var spinerIstrazivanja : Spinner
    private lateinit var spinerGrupe : Spinner
    private var korisnik : Korisnik = Korisnik()
    private lateinit var dodajIstrazivanjeDugme : Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanje)

       // korisnik = Korisnik()
        spinerGodine = findViewById(R.id.odabirGodina)
        spinerIstrazivanja = findViewById(R.id.odabirIstrazivanja)
        spinerGrupe = findViewById(R.id.odabirGrupa)
        dodajIstrazivanjeDugme = findViewById(R.id.dodajIstrazivanjeDugme)

        val godine = listOf( "1","2","3","4","5")

        val adapterGodineSpiner : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_spinner_item,godine
        )
        val adapterIstrazivanjaSpiner : ArrayAdapter<Istrazivanje> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item
        )
        val adapterGrupeSpiner : ArrayAdapter<Grupa> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item
        )

        spinerGodine.adapter = adapterGodineSpiner
        spinerIstrazivanja.adapter = adapterIstrazivanjaSpiner
        spinerGrupe.adapter = adapterGrupeSpiner

        spinerGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               // val vrijednost : Int = godine[p2]
                adapterIstrazivanjaSpiner.clear()
                adapterIstrazivanjaSpiner.addAll(
                    IstrazivanjeRepository.getAll()
                    .filter { istrazivanje -> istrazivanje.godina.toString()==godine[p2] }
                    .filter { istrazivanje -> !korisnik.upisanaIstrazivanja.contains(istrazivanje) })
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spinerIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                adapterGrupeSpiner.clear()
                adapterGrupeSpiner
                    .addAll(
                        GrupaRepository.getGroupsByIstrazivanje(adapterIstrazivanjaSpiner.toString())
                    .filter { grupa -> grupa.nazivIstrazivanja ==  spinerIstrazivanja.selectedItem.toString()}
                        .filter { grupa -> !korisnik.upisaneGrupe.contains(grupa) })
                adapterGrupeSpiner.notifyDataSetChanged()
            }



            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        dodajIstrazivanjeDugme.setOnClickListener{
            if(spinerGodine.selectedItem != null && spinerIstrazivanja.selectedItem !=null
                && spinerGrupe.selectedItem != null){
                korisnik.upisanaIstrazivanja = korisnik.upisanaIstrazivanja.plus(Istrazivanje(spinerIstrazivanja.selectedItem.toString(),spinerGodine.selectedItemPosition+1))
                //plusElement(spinerIstrazivanja.selectedItem)
                //= append(spinerIstrazivanja.selectedItem)
                korisnik.upisaneGrupe = korisnik.upisaneGrupe.plus(Grupa(spinerGrupe.selectedItem.toString(),spinerIstrazivanja.selectedItem.toString()))

                Log.v("NOVO ISTRAZIVANJE", korisnik.upisanaIstrazivanja.toString())
                Log.v("NOVO GRUPA", korisnik.upisaneGrupe.toString())


                adapterIstrazivanjaSpiner.remove(Istrazivanje(spinerIstrazivanja.selectedItem.toString(),spinerGodine.selectedItemPosition+1))
                adapterGrupeSpiner.notifyDataSetChanged()
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
            }
        }

    }
}