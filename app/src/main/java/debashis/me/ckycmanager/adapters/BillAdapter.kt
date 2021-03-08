package debashis.me.ckycmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R

class BillAdapter(private val context: Context): RecyclerView.Adapter<BillAdapter.BillListView>() {

    class BillListView(view: View):RecyclerView.ViewHolder(view){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillListView {
       val layuot = LayoutInflater.from(context).inflate(R.layout.bill_list_item,parent,false)
        return BillListView(layuot)
    }

    override fun onBindViewHolder(holder: BillListView, position: Int) {

    }

    override fun getItemCount(): Int {
        return  10
    }


}