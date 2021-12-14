package com.one.bee.sample.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.one.bee.R

class FragmentSampleActivity : AppCompatActivity() {

    val TAG = "FirstFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_sample)

        val firstFragment = FirstFragment.newInstance("1", "2")
        val secondFragment = SecondFragment.newInstance("1", "2")
        val thirdFragment = ThirdFragment.newInstance("1", "2")


        val fr = supportFragmentManager.findFragmentByTag(TAG)
        if (fr == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, firstFragment,TAG)
//            .replace(R.id.container, thirdFragment)
//            .remove(thirdFragment)
//            .remove(secondFragment)
//            .add(R.id.container, firstFragment)
//            .addToBackStack(null)
                .commit()
        }



    }
}