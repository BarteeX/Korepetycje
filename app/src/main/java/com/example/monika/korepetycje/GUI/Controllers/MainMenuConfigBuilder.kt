package com.example.monika.korepetycje.GUI.Controllers

import android.app.Activity
import com.example.monika.korepetycje.GUI.ApplicationHelper
import com.example.monika.korepetycje.R
import com.example.monika.korepetycje.database.DBHelper
import java.util.*

class MainMenuConfigBuilder(val context : Activity) {

    fun build() : ArrayList<HashMap<String, Any>> {

        val list = ArrayList<HashMap<String,Any>>()
        list.add(buildAllStudentsElement())
        list.addAll(buildAllWeekDaysElements())

        return list
    }

    fun build(config : List<HashMap<String, Map<*,*>>>) : List<HashMap<String, Any>> {

        val list = build()

        list.addAll(buildCustomElements(config))
        return list
    }

    private fun buildAllStudentsElement() : HashMap<String,Any> {
        return buildCustomElement(R.drawable.list_icon, "Wszyscy", {
            ApplicationHelper.startStudentListIntent(null, context)
        })
    }

    private fun buildAllWeekDaysElements() : List<HashMap<String, Any>> {
        val list = ArrayList<HashMap<String,Any>>()
         (context.resources.getStringArray(R.array.days_array)).forEach { day ->
             list.add(buildCustomElement(
                 R.drawable.list_icon,
                 day as String,
                 {
                     ApplicationHelper.startStudentListIntent(day, context)
                 }
             ))
        }
        return list
    }

    private fun buildCustomElements(config : List<HashMap<String, Map<*,*>>>) : List<HashMap<String, Any>> {
        val configFromUser : List<HashMap<String, Map<*,*>>> = config
        val list = ArrayList<HashMap <String,Any>>()
        configFromUser.forEach({ cfg : HashMap<String, Map<*,*>> ->
            val buttonName = (cfg["additionalData"] as Map<*,*>)["buttonName"] as String
            cfg.remove("additionalData")
            list.add(buildQueryElement(buttonName, cfg))
        })

        return list
    }

    private fun buildQueryElement(buttonName : String, queryParams : HashMap<String, Map<*,*>>) : HashMap<String, Any> {
        return buildCustomElement(
            R.drawable.list_icon,
            buttonName,
            {
                ApplicationHelper.startStudentListWithQueryIntent(DBHelper.getQuery(queryParams), context)
            }
        )
    }

    private fun buildCustomElement(icon: Int, text: String, action: () -> Unit) : HashMap <String,Any> {
        return mapOf(
                Pair("icon", icon),
                Pair("text", text),
                Pair("action", action)
        ) as HashMap
    }

}