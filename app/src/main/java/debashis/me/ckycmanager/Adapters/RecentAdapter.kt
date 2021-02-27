package debashis.me.ckycmanager.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.DayData
import kotlin.coroutines.coroutineContext

class RecentAdapter(val dayArray:List<DayData>,var context: Context):RecyclerView.Adapter<RecentAdapter.RecentViewHolder>() {


    inner class RecentViewHolder(v: View):RecyclerView.ViewHolder(v){
            var dateText: TextView = v.findViewById(R.id.dateText)
        var moneyText: TextView = v.findViewById(R.id.moneyText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_data_list_view,parent,false)
        Log.d("RecentAdapter", "onCreateViewHolder: ")
            return RecentViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.moneyText.text = dayArray[position].money
        holder.dateText.text = dayArray[position].date
        Log.d("RecentAdapter", "onBindViewHolder: ")
    }

    override fun getItemCount(): Int {
      return  dayArray.size
    }

}