package debashis.me.ckycmanager.adapters

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.view.isVisible
import androidx.core.view.marginEnd
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors.getColor
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.DateConverter
import debashis.me.ckycmanager.data.Day
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.data.toDayMonth
import debashis.me.ckycmanager.databinding.DailyDataListViewBinding
import debashis.me.ckycmanager.databinding.DailyDataListViewBindingImpl
import debashis.me.ckycmanager.databinding.DailyDataListViewTestModeOnBinding
import debashis.me.ckycmanager.databinding.DailyDataListViewTestModeOnBindingImpl
import debashis.me.ckycmanager.db.Kyc
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat


class RecentAdapter(private var kycData: List<Kyc>) : RecyclerView.Adapter<RecentViewHolder>() {

    lateinit var binding: DailyDataListViewBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(inflater, R.layout.daily_data_list_view, parent, false)
        return RecentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        holder.bindView(kycData[position])


    }



    override fun getItemCount(): Int {
        Log.i("Recent Adapter", "getItemCount: ${kycData.size}")
        return kycData.size
    }

    fun setData(list: List<Kyc>) {
        kycData = list
        notifyDataSetChanged()
    }

}

class RecentViewHolder(private val binding: DailyDataListViewBinding) : RecyclerView.ViewHolder(binding.root) {

    var isgray = true
    var noViewAvailable = true



    fun bindView(kyc: Kyc) {

        binding.dateText.text = kyc.date?.toDayMonth() ?: ""
        binding.moneyText.text = "${runBlocking { Keys.getTotalMoney(kyc,binding.dateText.context) }}"

        /**Check if the view is already build or not if: Yes  then don't need to rebuild again if:Not then build it
        default if :Not */
        if (noViewAvailable) {
            addViewAndData(kyc)
            noViewAvailable = false
        }

//            binding.upperConst.setOnClickListener {
//
//                if (binding.lowerConst.isVisible) {
//                    binding.lowerConst.visibility = View.GONE
//                    binding.downArrow.rotation = -90f
//                } else {
//                    binding.lowerConst.visibility = View.VISIBLE
//                    binding.downArrow.rotation = 0f
//                }
//            }

    }


    private fun addMetaData(pair: Pair<String, Int>) {
        val layout = LayoutInflater.from(binding.dateText.context).inflate(R.layout.drop_down_item, null)
        val key = layout.findViewById<TextView>(R.id.item_name)
        val value = layout.findViewById<TextView>(R.id.item_value)
        val container = layout.findViewById<ConstraintLayout>(R.id.container)
        if (isgray) {
            container.setBackgroundColor(ContextCompat.getColor(binding.dateText.context, R.color.light_gray))
            isgray = false
        } else {
            isgray = true
        }
        key.text = pair.first
        value.text = pair.second.toString()

        binding.lowerContainer.addView(layout)
    }

    private fun addViewAndData(kyc: Kyc) {

        if (kyc.ckyc > 0) addMetaData("Scan" to kyc.ckyc)
        if (kyc.maker > 0) addMetaData("Maker" to kyc.maker)
        if (kyc.checker > 0) addMetaData("Checker" to kyc.checker)
        if (kyc.web_maker > 0) addMetaData("Web Maker" to kyc.web_maker)
        if (kyc.web_checker > 0) addMetaData("Web Checker" to kyc.web_checker)
        if (kyc.account_numbering > 0) addMetaData("A/C Number" to kyc.account_numbering)

    }


}