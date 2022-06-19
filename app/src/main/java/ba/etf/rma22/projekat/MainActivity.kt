package ba.etf.rma22.projekat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.OnlineProvjera
import ba.etf.rma22.projekat.data.repositories.*
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import ba.etf.rma22.projekat.viewmodel.*


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var adapter : ViewPagerAdapter
        lateinit var viewPager2 : ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountViewModel = AccountViewModel()
        val odgovorViewModel = OdgovorViewModel()
        val istrazivanjaIGrupaViewModel = IstrazivanjeIGrupaViewModel()
        val takeAnketaViewModel = TakeAnketaViewModel()

        AccountRepository.setContext(this.applicationContext)
        AnketaRepository.setContext(this.applicationContext)
        IstrazivanjeIGrupaRepository.setContext(this.applicationContext)
        OdgovorRepository.setContext(this.applicationContext)
        PitanjeAnketaRepository.setContext(this.applicationContext)
        TakeAnketaRepository.setContext(this.applicationContext)

        if(OnlineProvjera.isOnline(this.applicationContext)) {
            odgovorViewModel.obrisiSve()
            istrazivanjaIGrupaViewModel.obrisiIstrazivanjaDB()
            takeAnketaViewModel.obrisiPokusajeDB()
        }

        val hash = intent?.getStringExtra("payload")

        if(hash != null)
        accountViewModel.postaviHash( hash!!)

        viewPager2 = findViewById(R.id.pager)
        adapter = ViewPagerAdapter(viewPager2.context, this)

        viewPager2.adapter = adapter
    }
/* override fun onResume() {
        val accountViewModel = AccountViewModel()
        super.onResume()
        val hash = intent?.getStringExtra("payload")

        if(hash != null)
        accountViewModel.postaviHash(viewPager2.context, hash!!)
    }

 */
}