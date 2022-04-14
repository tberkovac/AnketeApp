package ba.etf.rma22.projekat.view

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var fragments = mutableListOf(FragmentAnkete(), FragmentIstrazivanje())

    fun refreshFragment(index: Int, fragment: Fragment) {
        fragments[index] = fragment
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemId(position: Int): Long {
        return fragments[position].hashCode().toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return fragments.find { it.hashCode().toLong() == itemId } != null
    }

}