package debashis.me.ckycmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.commit
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import debashis.me.ckycmanager.fragments.Bills
import debashis.me.ckycmanager.fragments.Dashboard
import debashis.me.ckycmanager.fragments.Prices

const val CUSTOME_DATE="custome_date"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.commit {
            add(R.id.layout_container,Dashboard.newInstance())
            setReorderingAllowed(true)
        }
        findViewById<ChipNavigationBar>(R.id.bottom_nav).setItemSelected(R.id.dashboard,true)

        setupBottomNav()


    }

    fun setupBottomNav(){

        findViewById<ChipNavigationBar>(R.id.bottom_nav).setOnItemSelectedListener { id->

          supportFragmentManager.commit {
              replace(R.id.layout_container,when(id){
                  R.id.dashboard -> Dashboard.newInstance()
                  R.id.bills -> Bills.newInstance()
                  R.id.prices -> Prices.newInstance()
                  else -> Dashboard.newInstance()
              })
              setReorderingAllowed(true)
          }
        }

    }



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