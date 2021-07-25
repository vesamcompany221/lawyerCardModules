package com.vesam.barexamlibrary.ui.view.adapter.slider_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.imageview.ShapeableImageView
import com.vesam.barexamlibrary.R
import com.vesam.barexamlibrary.interfaces.OnClickListenerAny
import com.vesam.barexamlibrary.utils.tools.GlideTools
import java.util.*

class AdapterImageSlider(private val context: Context, private val glideTools: GlideTools) : PagerAdapter() {

    private lateinit var onClickListenerAny: OnClickListenerAny
    private val list: ArrayList<String> = ArrayList()

    fun setOnItemClickListener(onClickListenerAny: OnClickListenerAny) {
        this.onClickListenerAny = onClickListenerAny
    }

    override fun getCount(): Int = list.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as RelativeLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val link: String = list[position]
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v: View = inflater.inflate(R.layout.item_slider, container, false)
        val imageSlider = v.findViewById<View>(R.id.imageSlider) as ShapeableImageView
        val lnParent = v.findViewById<View>(R.id.lnParent) as RelativeLayout
        glideTools.displaySliderImage(
            imageSlider,
            link,
            R.drawable.shape_round_slider_place_holder,
            R.drawable.shape_round_slider_place_holder
        )
        lnParent.setOnClickListener { onClickListenerAny.onClickListener(link) }
        (container as ViewPager).addView(v)
        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    fun updateList(listImage: ArrayList<String>) {
        list.clear()
        list.addAll(listImage)
        notifyDataSetChanged()
    }
}