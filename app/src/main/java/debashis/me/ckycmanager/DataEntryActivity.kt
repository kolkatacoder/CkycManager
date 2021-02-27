package debashis.me.ckycmanager

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*

class DataEntryActivity : AppCompatActivity() {

    var numberOfField = 0
    var datatype = arrayOf("Scan","web Macker","Web Checker","Maker","Checker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_entry)


        //Add a default data
        addView(findViewById<LinearLayout>(R.id.linearLayout))
        //set date in the text
        val date = intent.getStringExtra(CUSTOME_DATE)
        findViewById<TextView>(R.id.heading_text).text="ADD $date DATA"


        //Add button
        findViewById<Button>(R.id.addBtn).setOnClickListener {
            val layout = findViewById<LinearLayout>(R.id.linearLayout)
            if(numberOfField != 6) {
                numberOfField++
                addView(layout)
            }
        }


    }

    fun addView(layout: LinearLayout){
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val parent = inflater.inflate(R.layout.add_data_list_view,null)
        //Initialize Spinner and add adapter
        val spinner = parent.findViewById<Spinner>(R.id.dataSpiner)
        val adapter = ArrayAdapter(parent.context,android.R.layout.simple_spinner_dropdown_item,datatype)
        spinner.adapter = adapter

        //Cancel button implementation
        parent.findViewById<Button>(R.id.cancle_button).setOnClickListener {
            if(numberOfField >0) {
                numberOfField--
                layout.removeView(parent)
            }
        }
        layout.addView(parent)
    }
}