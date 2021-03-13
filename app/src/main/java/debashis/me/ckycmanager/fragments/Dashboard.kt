package debashis.me.ckycmanager.fragments

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import debashis.me.ckycmanager.adapters.RecentAdapter
import debashis.me.ckycmanager.CUSTOME_DATE
import debashis.me.ckycmanager.DataEntryActivity
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.Day
import java.util.*


class Dashboard : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



       initFloatinfbutton(view)
        init(view)
    }

    //Initialize Recycalerview
    fun init(v: View){
        val recylerView =  v.findViewById<RecyclerView>(R.id.recent)
        val adapter = RecentAdapter(daydata(),requireContext())
        val layoutManager =  LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL

        recylerView.layoutManager = layoutManager

        recylerView.adapter = adapter

    }
    fun daydata():List<Day>{
        val list = ArrayList<Day>()
        list.add(Day("Today","650"))
        list.add(Day("yester day","300"))
        list.add(Day("10 Feb","342"))
        list.add(Day("8 Feb","800"))
        list.add(Day("7 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        list.add(Day("8 Feb","500"))
        return list
    }


    //Initialize floating button
    fun initFloatinfbutton(view : View){

        view.findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {


            val datePickerbox = AlertDialog.Builder(requireContext())
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



// Pick Date
    private fun pickupdate():String {
        val datepicker = DatePicker(requireContext())
        val cal = Calendar.getInstance()
        var date =""
        val datePickerDialog = DatePickerDialog(requireContext(),object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                date = "$dayOfMonth-${month+1}-$year"

                startDataEntryActivity(date)
            }

        },cal[Calendar.YEAR],cal[Calendar.MONTH],cal[Calendar.DAY_OF_MONTH])
        datePickerDialog.show()
        return date
    }

    // Start second Activity
    fun startDataEntryActivity(date: String){
        val intent = Intent(requireContext(), DataEntryActivity::class.java)
        intent.putExtra(CUSTOME_DATE,date)

        startActivity(intent)
    }





    companion object {

        @JvmStatic
        fun newInstance() = Dashboard()
    }
}