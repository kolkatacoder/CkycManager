package debashis.me.ckycmanager.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.Day
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.databinding.DailyDataListViewBinding
import debashis.me.ckycmanager.databinding.DailyDataListViewBindingImpl
import debashis.me.ckycmanager.db.Kyc

class RecentAdapter(var context: Context):RecyclerView.Adapter<RecentViewHolder>() {

    lateinit var binding: DailyDataListViewBinding
    var kycData = emptyList<Kyc>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
            val inflater = LayoutInflater.from(parent.context)
                binding = DataBindingUtil.inflate(inflater,R.layout.daily_data_list_view,parent,false)
            return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
       holder.bindView(kycData[position])
    }

    override fun getItemCount(): Int {
      return  kycData.size
    }

    fun setData(list:List<Kyc>){
        kycData = list
        notifyDataSetChanged()
    }

}

 class RecentViewHolder(private val binding: DailyDataListViewBinding):RecyclerView.ViewHolder(binding.root){

        fun bindView(kyc:Kyc){
            binding.dateText.text = "${kyc.date} ${Keys.getMonthName(kyc.month)}"
            binding.moneyText.text = "${Keys.getTotalMoney(kyc)}"
        }

}
