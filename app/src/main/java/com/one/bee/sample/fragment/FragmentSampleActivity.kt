package com.one.bee.sample.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.one.bee.R
import kotlinx.android.synthetic.main.activity_fragment_sample.*

class FragmentSampleActivity : AppCompatActivity() {

    val TAG = "FirstFragment"
    val fragments = mutableListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_sample)

        val firstFragment = FirstFragment.newInstance("1", "2")
        val secondFragment = SecondFragment.newInstance("1", "2")
        val thirdFragment = ThirdFragment.newInstance("1", "2")



        fragments.add(firstFragment)
        fragments.add(secondFragment)
        fragments.add(thirdFragment)

        vp.adapter = MyAdapter(this)
        vp.orientation = ViewPager2.ORIENTATION_HORIZONTAL


    }

    inner class MyAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
           return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

    }
}