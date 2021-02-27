package debashis.me.ckycmanager

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import java.text.SimpleDateFormat
import java.util.*

val CUSTOME_DATE="custome_date"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val ffrg = FirstFragment()
        supportFragmentManager.commit {
            add(R.id.fragment,ffrg)
            setReorderingAllowed(true)

        }

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {


            val datePickerbox = AlertDialog.Builder(this@MainActivity)
            datePickerbox.setItems(arrayOf("TO DAY ","PICK A DATE")){_,p->
                when(p){
                    0->{
                        val cal = Calendar.getInstance()
                     val year =   cal.get(Calendar.YEAR)
                     val month =   cal.get(Calendar.MONTH)
                     val day =   cal.get(Calendar.DAY_OF_MONTH)
                        val date = "$day-${month+1}-$year"
                        startDataEntryActivity(date)

                    }
                    1->{
                       val date = pickupdate()

                    }
                }
            }
         val pickupmenu =   datePickerbox.create()
            pickupmenu.show()
        }

    }

    private fun pickupdate():String {
            val datepicker = DatePicker(this)
            val cal = Calendar.getInstance()
            var date =""
            val datePickerDialog = DatePickerDialog(this,object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    date = "$dayOfMonth-${month+1}-$year"

                    startDataEntryActivity(date)
                }

            },cal[Calendar.YEAR],cal[Calendar.MONTH],cal[Calendar.DAY_OF_MONTH])
            datePickerDialog.show()
        return date
    }


    fun startDataEntryActivity(date: String){
        val intent = Intent(this,DataEntryActivity::class.java)
            intent.putExtra(CUSTOME_DATE,date)
            startActivity(intent)
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