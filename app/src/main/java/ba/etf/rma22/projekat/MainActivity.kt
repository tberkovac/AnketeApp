package ba.etf.rma22.projekat

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import ba.etf.rma22.projekat.view.FragmentIstrazivanje
import ba.etf.rma22.projekat.view.ViewPagerAdapter


class MainActivity : AppCompatActivity() {

    companion object{
        lateinit var adapter : ViewPagerAdapter
        lateinit var viewPager2 : ViewPager2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager2 = findViewById(R.id.pager)
        adapter = ViewPagerAdapter(viewPager2.context, this)

        viewPager2.adapter = adapter

    }



}