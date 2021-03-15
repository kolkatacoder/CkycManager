package debashis.me.ckycmanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import debashis.me.ckycmanager.data.Day
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.viewModel.KycViewModel

class DataEntryActivity : AppCompatActivity() {

    var numberOfField = 0
    var day =0
    var month =0
    var year =0
    lateinit var mKycViewModel :KycViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)


        //initialized data
        mKycViewModel = ViewModelProvider(this).get(KycViewModel::class.java)


        //Add a default date
        addView(findViewById<LinearLayout>(R.id.linearLayout))
        //set date in the text
         day = intent.getIntExtra(Keys.DAY,0)
         month = intent.getIntExtra(Keys.MONTH,0)
        year = intent.getIntExtra(Keys.YEAR,0)
        findViewById<TextView>(R.id.heading_text).text="ADD $day ${Keys.getMonthName(month)} DATA"


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

        val kyc = Kyc(id="$day-$month-$year",date=day,month = month,year=year,
                ckyc = dataPair.getOrDefault(0,0),
                web_maker = dataPair.getOrDefault(1,0),
                web_checker = dataPair.getOrDefault(2,0),
                maker = dataPair.getOrDefault(3,0),
                checker = dataPair.getOrDefault(4,0),
                account_numbering = dataPair.getOrDefault(5,0))
        mKycViewModel.addKyc(kyc)
        Toast.makeText(this,kyc.toString(),Toast.LENGTH_SHORT).show()
        Log.i("DataEntry Activity", "onCreate: ${dataPair.toString()}")
    }

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