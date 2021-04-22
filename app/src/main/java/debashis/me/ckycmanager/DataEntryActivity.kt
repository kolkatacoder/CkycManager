package debashis.me.ckycmanager

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import debashis.me.ckycmanager.data.DateConverter
import debashis.me.ckycmanager.data.Day
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.viewModel.KycViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class DataEntryActivity : AppCompatActivity() {

    private val TAG = "DataEntryActivity"

    var numberOfField = 0
    var day =0
    var month =0
    var year =0
    lateinit var mKycViewModel :KycViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //initialized data
        mKycViewModel = ViewModelProvider(this).get(KycViewModel::class.java)


        //Add a default date
        addView(findViewById<LinearLayout>(R.id.linearLayout))
        //set date in the text
         day = intent.getIntExtra(Keys.DAY,0)
         month = intent.getIntExtra(Keys.MONTH,0)
        year = intent.getIntExtra(Keys.YEAR,0)
        findViewById<TextView>(R.id.heading_text).text="ADD $day ${Keys.getMonthName(month.toString())}'s DATA"


        //Add button
        findViewById<Button>(R.id.addBtn).setOnClickListener {
            val layout = findViewById<LinearLayout>(R.id.linearLayout)
            if(numberOfField < 5) {
                numberOfField++
                addView(layout)
            }
        }
        //Save button on ClickListener
        findViewById<Button>(R.id.saveBtn).setOnClickListener {
           addDataIntoDatabase()
       }


    }



    /**Add new Data Or row in the table kye_data_table */
    private fun addDataIntoDatabase(){
        val layoutList = findViewById<LinearLayout>(R.id.linearLayout)
        val dataPair:MutableMap<Int,Int> = mutableMapOf<Int,Int>()

        for(i:Int in 0 until layoutList.childCount){

            val datalist = layoutList.getChildAt(i)
            val text =  datalist.findViewById<EditText>(R.id.value)
            val spinner = datalist.findViewById<Spinner>(R.id.dataSpiner)

            if(text.text.toString() == ""){
                dataPair[spinner.selectedItemPosition] = 0
            }else{
                dataPair[spinner.selectedItemPosition] = text.text.toString().toInt()
            }

        }

        val kyc = Kyc(id=0,
                date = DateConverter().StringToDate("$year-$month-$day"),
                ckyc = dataPair.getOrDefault(0,0),
                web_maker = dataPair.getOrDefault(1,0),
                web_checker = dataPair.getOrDefault(2,0),
                maker = dataPair.getOrDefault(3,0),
                checker = dataPair.getOrDefault(4,0),
                account_numbering = dataPair.getOrDefault(5,0))
       val resCode =  mKycViewModel.addKyc(kyc)
        startActivity(Intent(this,MainActivity::class.java))
        finish()

    }

    //Add view
    private fun addView(layout: LinearLayout){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val parent = inflater.inflate(R.layout.add_data_list_view,null)
        //Initialize Spinner and add adapter
        val spinner = parent.findViewById<Spinner>(R.id.dataSpiner)
        val adapter = ArrayAdapter(parent.context,android.R.layout.simple_spinner_dropdown_item,Keys.datatype)
        spinner.adapter = adapter

        //Cancel button implementation
        parent.findViewById<ImageButton>(R.id.cancle_button).setOnClickListener {
            if(numberOfField >0) {
                numberOfField--
                layout.removeView(parent)
            }
        }
        layout.addView(parent)
    }




}