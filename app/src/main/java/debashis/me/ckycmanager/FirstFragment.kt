package debashis.me.ckycmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.Adapters.RecentAdapter
import debashis.me.ckycmanager.data.DayData

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)

    }

    fun init(v: View){
     val recylerView =  v.findViewById<RecyclerView>(R.id.recent)
        val adapter = RecentAdapter(daydata(),requireContext())
        val layoutManager =  LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL

        recylerView.layoutManager = layoutManager

        recylerView.adapter = adapter

    }
    fun daydata():List<DayData>{
        val list = ArrayList<DayData>()
        list.add(DayData("Today","650"))
        list.add(DayData("yester day","300"))
        list.add(DayData("10 Feb","342"))
        list.add(DayData("8 Feb","800"))
        list.add(DayData("7 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        list.add(DayData("8 Feb","500"))
        return list
    }

}