package debashis.me.ckycmanager.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.*
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.toDayMonth
import debashis.me.ckycmanager.databinding.BillListItemBinding
import debashis.me.ckycmanager.db.Bill
import debashis.me.ckycmanager.fragments.FragmentBillProvider
import debashis.me.ckycmanager.viewModel.KycViewModel
import kotlinx.coroutines.launch
import java.util.*

const val BILL_PHOTO_REQUEST = 56


class BillAdapter(private val activity: Fragment) : RecyclerView.Adapter<BillListView>() {

    lateinit var listBinding: BillListItemBinding
    var bills = emptyList<Bill>()
    lateinit var viewModel: KycViewModel


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BillListView {
        val inflater = LayoutInflater.from(parent.context)
        listBinding = DataBindingUtil.inflate(inflater, R.layout.bill_list_item, parent, false)
        viewModel = ViewModelProvider(activity).get(KycViewModel::class.java)
        return BillListView(listBinding, activity, viewModel)
    }

    override fun onBindViewHolder(holder: BillListView, position: Int) {
        holder.bind(bills[position])
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    fun setData(bills: List<Bill>) {
        this.bills = bills
        notifyDataSetChanged()
    }


}

class BillListView(
    private val listBinding: BillListItemBinding,
    private val activity: Fragment,
    private val viewModel: KycViewModel
) : RecyclerView.ViewHolder(listBinding.root) {

    private val TAG = "Bill List View"
    var billStatus = BillStatus.NOT_PAID


    fun bind(bill: Bill) {
        listBinding.billNo.text = "${bill.billNumber}:"
        listBinding.startDate.text = bill.startDate?.toDayMonth() ?: ""
        listBinding.endDate.text = bill.endDate?.toDayMonth() ?: ""
        listBinding.amount.text = "${bill.totalValue}"
        listBinding.issueDate.text = bill.issueDate?.toDayMonth() ?: ""
        billStatus = bill.billStatus

        //Set the Bill Status : if bill is pending only then show *pending Otherwise *Complete
        listBinding.statusValue.apply {
            if (billStatus == BillStatus.NOT_PAID) {
                setCompoundDrawablesWithIntrinsicBounds(
                    getDrawable(this.context, R.drawable.ic_watch),
                    null,
                    null,
                    null
                )
                text = "Pending"
                compoundDrawablePadding = 5
            } else {
                setCompoundDrawablesWithIntrinsicBounds(
                    getDrawable(this.context, R.drawable.ic_gray_double_check),
                    null,
                    null,
                    null
                )
                text = "Complete"
                compoundDrawablePadding = 5
            }
        }

        /** If bill Status is pending then hide the paid text and value
         * else Show the paid date*/
        if (billStatus == BillStatus.NOT_PAID) {
            listBinding.paid.visibility = View.GONE
            listBinding.paidDate.visibility = View.GONE
        } else {
            listBinding.paid.visibility = View.VISIBLE
            listBinding.paidDate.visibility = View.VISIBLE
            listBinding.paidDate.text = bill.passDate?.toDayMonth()
        }

        //Set Button appearance for the first time
        billAppearences(billStatus)

        //Bill Button click listener
        listBinding.billButton.setOnClickListener {
            when (billStatus) {
                BillStatus.NOT_PAID -> {
                    //Update the bill date and Status and Mark it Complete
                    activity.lifecycleScope.launch {
                        viewModel.updatebillByBillNo(bill.billNumber, Date(), BillStatus.COMPLETE)
                    }
//                    billStatus = BillStatus.COMPLETE
//                    billAppearences(billStatus)
                }
                BillStatus.COMPLETE -> {
                    //Update Bill photo Path into Database
                    Intent(Intent.ACTION_GET_CONTENT).apply {
                        type = "image/*"
                    }.also {
                        FragmentBillProvider = bill
                        activity.startActivityForResult(it, BILL_PHOTO_REQUEST)

                    }

                }
                BillStatus.BILL_AVAILABLE -> {
                    Intent(Intent.ACTION_VIEW).apply {
                        setDataAndType(bill.billPath.toUri(), "image/*")

                    }.also {
                        activity.startActivity(it)
                    }
                }
            }
        }
    }


    /** Bill Appearance Change the button appearance as the bill status is */
    fun billAppearences(status: BillStatus) {
        listBinding.billButton.apply {

            when (status) {

                BillStatus.COMPLETE -> {

                    setBackgroundResource(R.drawable.light_green_background)
                    text = "Add Bill"
                    setTextColor(getColor(this.context, R.color.green))
                    setCompoundDrawablesWithIntrinsicBounds(
                        getDrawable(
                            this.context,
                            R.drawable.ic_add_note
                        ), null, null, null
                    )
                }
                BillStatus.NOT_PAID -> {
                    setBackgroundResource(R.drawable.light_green_background)
                    text = "Complete"
                    setTextColor(getColor(this.context, R.color.green))
                    setCompoundDrawablesWithIntrinsicBounds(
                        getDrawable(
                            this.context,
                            R.drawable.ic_double_check
                        ), null, null, null
                    )

                }
                BillStatus.BILL_AVAILABLE -> {
                    setBackgroundResource(R.drawable.rounded_light_blue_background)
                    text = "View Bill"
                    setTextColor(getColor(this.context, R.color.text_blue))
                    setCompoundDrawablesWithIntrinsicBounds(
                        getDrawable(
                            this.context,
                            R.drawable.ic_invoice
                        ), null, null, null
                    )
                }
            }

        }

    }


}

enum class BillStatus {
    NOT_PAID, COMPLETE, BILL_AVAILABLE
}