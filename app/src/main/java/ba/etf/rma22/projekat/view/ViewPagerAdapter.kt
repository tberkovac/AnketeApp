package ba.etf.rma22.projekat.view

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ba.etf.rma22.projekat.MainActivity


class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private var fragments = mutableListOf(FragmentAnkete(), FragmentIstrazivanje())


    fun add(index: Int, fragment: Fragment) {
        fragments.add(index, fragment)
        notifyItemChanged(index)
    }

    fun addOnLastPosition(fragment: Fragment) {
        fragments.add(fragments.size, fragment)
        notifyItemChanged(fragments.size)
    }

    fun remove(index: Int) {
        fragments.removeAt(index)
        notifyItemChanged(index)
    }

    fun removeAll(){
        fragments.clear()
        notifyDataSetChanged()
    }

    fun refreshFragment(index: Int, fragment: Fragment) {
        if(index >= fragments.size)
            fragments.add(1,FragmentIstrazivanje())
        fragments[index] = fragment
        notifyItemChanged(index)
    }

    fun vratiNaPocetnoStanje(){
        fragments.clear()
        fragments.add(0, FragmentAnkete())
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

  /* fun instantiateView(position: Int): View {
     //   return fragments[position]
    }*/

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }





}