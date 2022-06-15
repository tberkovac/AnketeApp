package ba.etf.rma22.projekat.view

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma22.projekat.R
import ba.etf.rma22.projekat.data.models.Anketa
import ba.etf.rma22.projekat.data.models.AnketaTaken
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Pitanje
import ba.etf.rma22.projekat.viewmodel.*
import java.util.*


class AnketaListAdapter(
    private var ankete : List<Anketa>,
    private val mContext : Context,
    private val onItemClicked:  (anketa : Anketa) -> Unit
) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>(){

    var postotakPravi = 0
    inner class AnketaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nazivAnkete : TextView = itemView.findViewById(R.id.nazivAnkete)
        val statusAnkete : ImageView = itemView.findViewById(R.id.statusAnkete)
        val brojIstrazivanja : TextView = itemView.findViewById(R.id.brojIstrazivanja)
        val progresZavrsetka : ProgressBar = itemView.findViewById(R.id.progresZavrsetka)
        val pismeniStatus : TextView = itemView.findViewById(R.id.pismeniStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnketaViewHolder {
        val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.kartice_elementi,parent,false)
        return AnketaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnketaViewHolder, position: Int) {

        val anketaListViewModel = AnketeListViewModel()
        val istrazivanjeIGrupaViewModel = IstrazivanjeIGrupaViewModel()
        val takeAnketaViewModel = TakeAnketaViewModel()
        val pitanjeAnketaViewModel = PitanjeAnketaViewModel()
        val anketa = ankete[position]
        var pokusaj : AnketaTaken? = null

        holder.nazivAnkete.text = anketa.naziv


        fun nista(anketa: Anketa, list: List<Pitanje>) {

        }
        pitanjeAnketaViewModel.getPitanja(mContext,anketa,::nista)
        //popunjava nazive istrazivanja
        fun popuniIstrazivanja(listIstrazivanje: List<Istrazivanje> ){
            popuniIstrazivanjeTextView(listIstrazivanje.toSet(), holder)
        }
        istrazivanjeIGrupaViewModel.getIstrazivanjaZaAnketu(mContext, anketa, ::popuniIstrazivanja)

        //popunjava progres
        fun popuniPokusaje (listaPokusaja: List<AnketaTaken>){
            postaviProgres(anketa, listaPokusaja, holder)
        }
        takeAnketaViewModel.getPoceteAnkete(mContext, ::popuniPokusaje)

        fun pocetaSuccess(anketaTaken: AnketaTaken?) {
            pokusaj = anketaTaken
            jeLiSveOdgovoreno(mContext, anketa,holder, pokusaj)
        }
        takeAnketaViewModel.getPocetaAnketa(mContext, anketa.id,::pocetaSuccess)

        fun onSuccess(uspjelo : Boolean){
            if(uspjelo)
            holder.itemView.setOnClickListener{
                onItemClicked(ankete[position])
            }
        }
        anketaListViewModel.jeLiUpisanaAnketa(mContext, anketa.id, ::onSuccess)

    }

    private fun jeLiSveOdgovoreno(context: Context, anketa: Anketa, holder: AnketaViewHolder, pokusaj: AnketaTaken?) {
        val odgovorViewModel = OdgovorViewModel(context)
        odgovorViewModel.postotakOdgovorenih(anketa, ::postaviStatusIPoruku, holder, pokusaj)
    }



    private fun postaviStatusIPoruku(int : Int, holder: AnketaViewHolder, anketa: Anketa, pokusaj: AnketaTaken?) {
        val date = Calendar.getInstance().time
        postotakPravi = int
        val context: Context = holder.statusAnkete.context
        var id : Int
        if(int == 100 && anketa.datumKraj!= null && date > anketa.datumKraj){
            id = context.resources.getIdentifier("plava", "drawable", context.packageName)
          //  val pokusaj = TakeAnketaRepository.getPoceteAnkete()
            holder.pismeniStatus.text = "Anketa urađena: " + if(pokusaj != null)formatirajDatum(pokusaj.datumRada) else ""
        }
        else if(date < anketa.datumPocetak){
            id = context.resources.getIdentifier("zuta", "drawable", context.packageName)
            holder.pismeniStatus.text = "Vrijeme aktiviranja: " + formatirajDatum(anketa.datumPocetak)
        }
        else if( anketa.datumKraj!= null && date > anketa.datumKraj && int == 0){
            id = context.resources.getIdentifier("crvena", "drawable", context.packageName)
            holder.pismeniStatus.text = "Anketa zatvorena: " + formatirajDatum(anketa.datumKraj)
        }
        else{
            id = context.resources.getIdentifier("zelena", "drawable", context.packageName)

            holder.pismeniStatus.text = "Vrijeme zatvaranja: " + if(anketa.datumKraj!=null)
                formatirajDatum(anketa.datumKraj) else
                    "nepoznato"

        }

        holder.statusAnkete.setImageResource(id)
        postotakPravi = 0
    }

    private fun formatirajDatum(date : Date) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(date)
    }


    override fun getItemCount(): Int {
        return ankete.size
    }

    fun updateAnkete(ankete: List<Anketa>){
        this.ankete = ankete
        notifyDataSetChanged()
    }

    fun popuniIstrazivanjeTextView(setIstrazivanja: Set<Istrazivanje>, holder: AnketaViewHolder){
        val istrazivanja = setIstrazivanja.toList()
        if(istrazivanja.size == 1){
            holder.brojIstrazivanja.text = istrazivanja[0].naziv
            return
        }
        var tekst = ""
        for ((i, istrazivanje) in istrazivanja.withIndex()) {
            tekst += if(i != istrazivanja.size-1)
                istrazivanje.naziv + ","
            else
                istrazivanje.naziv

            holder.brojIstrazivanja.text = tekst
        }
    }

    fun postaviProgres (anketa: Anketa, listaPokusaja: List<AnketaTaken>, holder: AnketaViewHolder) {
        for (anketaTaken in listaPokusaja) {
            if(anketaTaken.AnketumId == anketa.id){
                holder.progresZavrsetka.progress = anketaTaken.progres
                return
            }
        }
        holder.progresZavrsetka.progress = 0
    }
}

