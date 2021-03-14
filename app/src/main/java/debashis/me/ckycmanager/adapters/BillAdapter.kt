package debashis.me.ckycmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.ContextCompat.getDrawable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.databinding.BillListItemBinding

class BillAdapter(private val context: Context): RecyclerView.Adapter<BillListView>() {

    lateinit var listBinding: BillListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillListView {
        val inflater = LayoutInflater.from(parent.context)
       listBinding = DataBindingUtil.inflate(inflater,R.layout.bill_list_item,parent,false)
        return BillListView(listBinding)
    }

    override fun onBindViewHolder(holder: BillListView, position: Int) {
            holder.addOrViewBill(false)
    }

    override fun getItemCount(): Int {
        return  10
    }


}
class BillListView(private val listBinding:BillListItemBinding):RecyclerView.ViewHolder(listBinding.root){

    fun bind(){

    }



    fun addOrViewBill(isAvailable: Boolean){
            listBinding.billButton.apply {

                if(!isAvailable){
                    setBackgroundResource(R.drawable.green_background)
                    text = "Add Bill"
                    setTextColor(getColor(this.context,R.color.green))
                    setCompoundDrawablesWithIntrinsicBounds(getDrawable(this.context,R.drawable.ic_add_note),null,null,null)
                }

            }



    }


}