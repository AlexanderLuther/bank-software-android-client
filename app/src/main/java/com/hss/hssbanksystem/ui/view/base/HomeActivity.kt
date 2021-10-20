package com.hss.hssbanksystem.ui.view.base

import android.content.ClipData
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.hss.hssbanksystem.R
import com.hss.hssbanksystem.core.DataStoreHelper
import com.hss.hssbanksystem.core.RetrofitHelper
import com.hss.hssbanksystem.core.startNewActivity
import com.hss.hssbanksystem.data.network.AuthenticationApi
import com.hss.hssbanksystem.data.network.UserApi
import com.hss.hssbanksystem.data.repository.UserRepository
import com.hss.hssbanksystem.databinding.ActivityHomeBinding
import com.hss.hssbanksystem.ui.viewmodel.home.HomeViewModel
import com.hss.hssbanksystem.ui.viewmodel.base.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dataStoreHelper : DataStoreHelper
    private lateinit var repository : UserRepository
    private lateinit var viewModel : HomeViewModel
    private val retrofitHelper = RetrofitHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inicializar todas las variables
        repository = UserRepository(retrofitHelper.buildApi(UserApi::class.java))
        viewModel =  ViewModelProvider(this, ViewModelFactory(repository)).get(HomeViewModel::class.java)
        dataStoreHelper = DataStoreHelper(this@HomeActivity)

        //Setear ViewBinding
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Setear barra de menu
        setSupportActionBar(binding.appBarHome.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navHomeFragment, R.id.navProfileFragment, R.id.navBankAccountRequestFragment, R.id.navCreditCardRequestFragment,
                R.id.navDebitCardRequestFragment,  R.id.navCancellationCardRequestFragment, R.id.navLoanRequestFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Accion a realizar cuando se presiona el menu Cerrar Sesion
        navView.menu.findItem(R.id.navLogout).setOnMenuItemClickListener {
            logout()
            Toast.makeText(baseContext, "Cerrando sesion", Toast.LENGTH_SHORT).show()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /**
     * Funcion que realiza el cerrado de sesion
     */
    private fun logout() = lifecycleScope.launch {
        val token = dataStoreHelper.authenticationToken.first()
        val api = retrofitHelper.buildApi(AuthenticationApi::class.java, token)
        viewModel.logout(api)
        dataStoreHelper.clear()
        finish()
        startNewActivity(NoLoggedUserActivity::class.java)
    }


}