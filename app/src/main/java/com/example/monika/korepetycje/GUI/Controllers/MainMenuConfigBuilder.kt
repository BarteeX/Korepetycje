package com.example.monika.korepetycje.GUI.Controllers

import android.app.Activity
import com.example.monika.korepetycje.GUI.ApplicationHelper
import com.example.monika.korepetycje.R
import java.util.*

class MainMenuConfigBuilder(val context : Activity) {

    fun build() : List<Map<String, Any>> {
        val list = ArrayList<Map<String,Any>>()
        list.add(buildAllStudentsElement())
        list.addAll(buildAllWeekDaysElements())
        return list
    }

    private fun buildAllStudentsElement() : Map<String,Any> {
        return buildCustomElement(R.drawable.list_icon, "Wszyscy", {
            ApplicationHelper.startStudentListIntent(null, context);
        })
    }

    private fun buildAllWeekDaysElements() : List<Map<String, Any>> {
        val list = ArrayList<Map<String,Any>>()
         (context.resources.getStringArray(R.array.days_array)).forEach { day ->
             list.add(buildCustomElement(R.drawable.list_icon, day as String, {
                 ApplicationHelper.startStudentListIntent(day, context);
             }))
        }
        return list
    }

    private fun buildCustomElement(icon: Int, text: String, action: () -> Unit) : Map <String,Any> {
        return mapOf(
                Pair("icon", icon),
                Pair("text", text),
                Pair("action", action)
        )
    }
}