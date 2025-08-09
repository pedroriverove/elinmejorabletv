package com.elinmejorabletv.ui.mobile

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.elinmejorabletv.R
import com.elinmejorabletv.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_schedule, R.id.navigation_favorites, R.id.navigation_account
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        // Mostrar el texto de carreras en la toolbar secundaria
        binding.toolbarSecondary.tvRacesCount.text = getString(R.string.today_races_count, "16")

        // Manejar el clic en el botón de cuenta
        binding.btnAccount.setOnClickListener {
            navController.navigate(R.id.navigation_account)
        }

        // Seleccionar la pestaña USA por defecto
        binding.tabLayout.getTabAt(1)?.select()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun updateRaceCount(count: Int) {
        binding.toolbarSecondary.tvRacesCount.text = getString(R.string.today_races_count, count.toString())
    }
}