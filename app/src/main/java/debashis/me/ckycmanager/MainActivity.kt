package debashis.me.ckycmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import debashis.me.ckycmanager.adapters.BILL_PHOTO_REQUEST
import debashis.me.ckycmanager.fragments.BillsFragment
import debashis.me.ckycmanager.fragments.DashboardFragment
import debashis.me.ckycmanager.fragments.PricesFragment
import debashis.me.ckycmanager.viewModel.KycViewModel


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity: "
    lateinit var viewModel: KycViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*supportFragmentManager.commit {
            add(R.id.layout_container,DashboardFragment.newInstance())
            setReorderingAllowed(true)
        }
        findViewById<ChipNavigationBar>(R.id.bottom_nav).setItemSelected(R.id.dashboard,true)

        setupBottomNav()*/

        viewModel= ViewModelProvider(this).get(KycViewModel::class.java)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navControler = findNavController(R.id.fragment)

        bottomNav.setupWithNavController(navControler)



    }

   /* fun setupBottomNav(){

        findViewById<ChipNavigationBar>(R.id.bottom_nav).setOnItemSelectedListener { id->

          supportFragmentManager.commit {
              replace(R.id.layout_container,when(id){
                  R.id.dashboard -> DashboardFragment.newInstance()
                  R.id.bills -> BillsFragment.newInstance()
                  R.id.prices -> PricesFragment.newInstance()
                  else -> DashboardFragment.newInstance()
              })
              setReorderingAllowed(true)
          }
        }

    }*/

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//            if(resultCode == RESULT_OK && requestCode == BILL_PHOTO_REQUEST){
//
//            }
//
//        Log.i(TAG, "onActivityResult: $resultCode && $requestCode : ${data?.data} , Intent ${data?.getIntExtra("Bill_NO",0)}")
//
////        Navigation.findNavController(this,R.id.fragment).navigate(R.id.action_billsFragment_to_imageViweFragment)
//
//    }









    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}