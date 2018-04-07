package com.example.monika.korepetycje.GUI.Controllers

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.monika.korepetycje.R


class MenuGridAdapter(
        private val mContext: Context,
        private val config : List<Any>
    ) : BaseAdapter() {

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ViewHolder", "NewApi")
    override fun getView(position: Int, view: View?, convertView: ViewGroup?): View? {
        val itemConfig : Map<*, *> = config[position] as Map<*, *>
        val tempConvertView: View

        val layoutInflater : LayoutInflater = LayoutInflater.from(mContext)
        tempConvertView = layoutInflater.inflate(R.layout.main_menu_item, null ,true) as ViewGroup

        val imageView: ImageView = tempConvertView.findViewById(R.id.menu_icon_button)
        val textView : TextView = tempConvertView.findViewById(R.id.menu_text_button)

        val viewHolder = MenuViewHolder(imageView = imageView, textView = textView)

        tempConvertView.setOnClickListener({
            invoke(itemConfig["action"] as () -> Unit)
        })

        viewHolder.imageView.setImageResource(itemConfig["icon"] as Int)
        viewHolder.textView.text = itemConfig["text"] as String

        tempConvertView.setTag(viewHolder)

        return tempConvertView
    }

    private fun invoke(action: () -> Unit) {
        action.invoke()
    }

    class MenuViewHolder (
            val imageView: ImageView,
            val textView : TextView
    )

    override fun getItem(p0: Int): Any? = config[p0]

    override fun getItemId(p0: Int): Long = 0

    override fun getCount(): Int = config.size

}