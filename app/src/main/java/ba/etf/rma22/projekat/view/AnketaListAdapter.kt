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
import java.time.LocalDate
import java.util.*


class AnketaListAdapter(
    private var ankete : List<Anketa>,
    private val onItemClicked: suspend (anketa : Anketa) -> Unit
) : RecyclerView.Adapter<AnketaListAdapter.AnketaViewHolder>(){
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

        val anketa = ankete[position]

        holder.nazivAnkete.text = anketa.naziv
      //  holder.brojIstrazivanja.text = anketa.nazivIstrazivanja
      //  holder.progresZavrsetka.setProgress(zaokruziProgres(anketa.progres), false)


        var cal: Calendar = Calendar.getInstance()
        cal.set(LocalDate.now().year,LocalDate.now().monthValue,LocalDate.now().dayOfMonth)
        var date: Date = cal.time

        val boja: String

     /*   if(anketa.datumRada == null){
            if(date < anketa.datumPocetak){
                boja = "zuta"
                holder.pismeniStatus.text = "Vrijeme aktiviranja: " + formatirajDatum(anketa.datumPocetak)
            }else if( date > anketa.datumKraj){
                boja = "crvena"
                holder.pismeniStatus.text = "Anketa zatvorena: " + formatirajDatum(anketa.datumKraj)
            }else{
                boja = "zelena"
                holder.pismeniStatus.text = "Vrijeme zatvaranja: " + formatirajDatum(anketa.datumKraj)

            }
        }else{
            holder.pismeniStatus.text = "Anketa urađena: " + formatirajDatum(anketa.datumRada!!)
            boja = "plava"
        }

      */

        val context: Context = holder.statusAnkete.context
        var id: Int = context.resources.getIdentifier("zelena", "drawable", context.packageName)
        holder.statusAnkete.setImageResource(id)

        holder.pismeniStatus.text
      /*
        holder.itemView.setOnClickListener {
            if(korisnikViewModel.jeLiUpisanaAnketa(anketa))
                withContext(Dispatchers.IO){
                    onItemClicked(ankete[position])
                }

       */


    }

    private fun formatirajDatum(date : Date) : String {
        return SimpleDateFormat("dd.MM.yyyy").format(date)
    }

    private fun zaokruziProgres(progres: Float): Int {
        var rez : Int = (progres*10).toInt()
        if(rez % 2 != 0) rez += 1
        rez *= 10
        return rez
    }

    override fun getItemCount(): Int {
        return ankete.size
    }

    fun updateAnkete(ankete: List<Anketa>){
        this.ankete = ankete
        notifyDataSetChanged()
    }
}

