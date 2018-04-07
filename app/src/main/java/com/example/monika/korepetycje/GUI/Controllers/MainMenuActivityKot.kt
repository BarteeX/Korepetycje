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

        gridView.adapter = MenuGridAdapter(this, configMap)
    }
}