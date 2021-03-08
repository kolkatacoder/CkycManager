package debashis.me.ckycmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.adapters.BillAdapter

class Bills : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bills, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRviewAndAdapter(view)
    }

    fun initializeRviewAndAdapter(view: View){

      val rView =  view.findViewById<RecyclerView>(R.id.bill_recycle)
        rView.layoutManager = LinearLayoutManager(requireContext())

        rView.adapter = BillAdapter(requireContext())



    }




    companion object {

        @JvmStatic
        fun newInstance() = Bills()
    }
}