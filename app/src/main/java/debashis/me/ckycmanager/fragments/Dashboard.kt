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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import debashis.me.ckycmanager.adapters.RecentAdapter
import debashis.me.ckycmanager.DataEntryActivity
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.Dates
import debashis.me.ckycmanager.data.Day
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.viewModel.KycViewModel
import java.util.*
import androidx.lifecycle.Observer


 class Dashboard : Fragment() {


    lateinit var kycViewModel:KycViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            kycViewModel = ViewModelProvider(this).get(KycViewModel::class.java)

       initFloatinfbutton(view)
        init(view)
    }

    //Initialize Recycalerview
    fun init(v: View){
        val recylerView =  v.findViewById<RecyclerView>(R.id.recent)
        val adapter = RecentAdapter(requireContext())
        val layoutManager =  LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        recylerView.layoutManager = layoutManager
        recylerView.adapter = adapter
        kycViewModel.getDataByMonth(3).observe(viewLifecycleOwner, Observer {kyc->
            adapter.setData(kyc)
        })

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
                        val date = Dates(cal.get(Calendar.DAY_OF_MONTH),(cal.get(Calendar.MONTH)+1), cal.get(Calendar.YEAR))
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
    private fun pickupdate() {

        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(requireContext(),object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
              val date = Dates(dayOfMonth,(month+1),year)

                startDataEntryActivity(date)
            }

        },cal[Calendar.YEAR],cal[Calendar.MONTH],cal[Calendar.DAY_OF_MONTH])
        datePickerDialog.show()

    }

    // Start second Activity
    fun startDataEntryActivity(date: Dates){
        val intent = Intent(requireContext(), DataEntryActivity::class.java).apply {
            putExtra(Keys.DAY,date.day)
            putExtra(Keys.MONTH,date.month)
            putExtra(Keys.YEAR,date.year)
        startActivity(this)
        }
    }






    companion object {

        @JvmStatic
        fun newInstance() = Dashboard()
    }
}