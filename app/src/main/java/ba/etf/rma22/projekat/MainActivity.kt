package ba.etf.rma22.projekat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.data.repositories.AccountRepository
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.ViewPagerAdapter
import ba.etf.rma22.projekat.viewmodel.AccountViewModel


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var adapter : ViewPagerAdapter
        lateinit var viewPager2 : ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val accountViewModel = AccountViewModel()

        val hash = intent?.getStringExtra("payload")

        if(hash != null)
        accountViewModel.postaviHash(viewPager2.context, hash!!)

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