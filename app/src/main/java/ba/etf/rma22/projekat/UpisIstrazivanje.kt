package ba.etf.rma22.projekat

import android.content.Intent
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
    var companion  = Companion
    companion object {
        var zadnji : String = ""
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upis_istrazivanje)

       // korisnik = Korisnik()
        spinerGodine = findViewById(R.id.odabirGodina)
        spinerIstrazivanja = findViewById(R.id.odabirIstrazivanja)
        spinerGrupe = findViewById(R.id.odabirGrupa)
        dodajIstrazivanjeDugme = findViewById(R.id.dodajIstrazivanjeDugme)

        val godine = listOf( "","1","2","3","4","5")

        val adapterGodineSpiner : ArrayAdapter<String> = ArrayAdapter(
            this,android.R.layout.simple_spinner_item,godine
        )
        val adapterIstrazivanjaSpiner : ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ArrayList<String>()
        )
        val adapterGrupeSpiner : ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, ArrayList<String>()
        )

        spinerGodine.adapter = adapterGodineSpiner
        spinerIstrazivanja.adapter = adapterIstrazivanjaSpiner
        spinerGrupe.adapter = adapterGrupeSpiner

        spinerGodine.setSelection(adapterGodineSpiner.getPosition(zadnji))

        spinerGodine.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                zadnji = spinerGodine.selectedItem.toString()
                if(spinerGodine.selectedItem == "") {
                    adapterIstrazivanjaSpiner.clear()
                    adapterIstrazivanjaSpiner.add("")
                    adapterGrupeSpiner.clear()
                    adapterGrupeSpiner.add("")
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                adapterIstrazivanjaSpiner.clear()
                adapterIstrazivanjaSpiner.add("")
                adapterIstrazivanjaSpiner.addAll(
                    IstrazivanjeRepository.getIstrazivanjeByGodina(spinerGodine.selectedItem.toString().toInt())
                    .filter { istrazivanje -> !korisnik.companion.upisanaIstrazivanja.contains(istrazivanje) }
                        . map { istrazivanje -> istrazivanje.naziv })
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
                zadnji = godine[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                spinerGodine.setSelection(zadnji.toInt())
            }
        }

        spinerIstrazivanja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerIstrazivanja.selectedItem == "") {
                    adapterGrupeSpiner.clear()
                    adapterGrupeSpiner.add("")
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                adapterGrupeSpiner.clear()
                adapterGrupeSpiner.add("")
                adapterGrupeSpiner
                    .addAll(
                        GrupaRepository.getGroupsByIstrazivanje(adapterIstrazivanjaSpiner.toString())
                    .filter { grupa -> grupa.nazivIstrazivanja ==  spinerIstrazivanja.selectedItem.toString()}
                        .filter { grupa -> !korisnik.companion.upisaneGrupe.contains(grupa) }
                            .map { grupa -> grupa.naziv })
                adapterGrupeSpiner.notifyDataSetChanged()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled = false
                adapterGrupeSpiner.clear()
            }
        }

        spinerGrupe.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(spinerGrupe.selectedItem == "") {
                    dodajIstrazivanjeDugme.isEnabled = false
                    return
                }
                dodajIstrazivanjeDugme.isEnabled = true
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                dodajIstrazivanjeDugme.isEnabled = false
            }

        }

        dodajIstrazivanjeDugme.setOnClickListener{
            if(spinerGodine.selectedItem != "" && spinerIstrazivanja.selectedItem !=""
                && spinerGrupe.selectedItem != ""){
                korisnik.companion.upisanaIstrazivanja = korisnik.companion.upisanaIstrazivanja.plus(Istrazivanje(spinerIstrazivanja.selectedItem.toString(),spinerGodine.selectedItemPosition))
                korisnik.companion.upisaneGrupe = korisnik.companion.upisaneGrupe.plus(Grupa(spinerGrupe.selectedItem.toString(),spinerIstrazivanja.selectedItem.toString()))

                adapterIstrazivanjaSpiner.remove(spinerIstrazivanja.selectedItem.toString())
                adapterGrupeSpiner.notifyDataSetChanged()
                adapterIstrazivanjaSpiner.notifyDataSetChanged()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

    }
}