package com.example.monika.korepetycje.GUI.Controllers

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.GridView
import com.example.monika.korepetycje.R

@SuppressLint("Registered")
class MainMenuActivityKot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        val gridView = findViewById<GridView>(R.id.menu_grid_layout)

        val configMap : List<Map<String, Any>> = MainMenuConfigBuilder(this).build()

        //val addButtonButton : Button = findViewById(R.id.add)

        gridView.adapter = MenuGridAdapter(this, configMap)

        TODO("""
            |MainMenuConfigBuilder(this).build(withConfig)
            |Zbudować formatkę filtra z comboboxem
            |Zbudować comboBox który będzie w łatwy sposób zbierał dane
            |Zbudować Akcję która zbierze dane oraz prześle je do buildera
        """.trimMargin())
    }
}