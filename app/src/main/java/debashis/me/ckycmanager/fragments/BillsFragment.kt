package debashis.me.ckycmanager.fragments

import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.adapters.BILL_PHOTO_REQUEST
import debashis.me.ckycmanager.adapters.BillAdapter
import debashis.me.ckycmanager.adapters.BillStatus
import debashis.me.ckycmanager.data.DateConverter
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.databinding.FragmentBillsBinding
import debashis.me.ckycmanager.db.Bill
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.viewModel.KycViewModel
import debashis.me.ckycmanager.viewModel.dataStore
import es.dmoral.toasty.Toasty
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*


 var FragmentBillProvider: Bill? = null

class BillsFragment : Fragment() {



    private val TAG = "BillFragment"
    lateinit var binding: FragmentBillsBinding
    val kycviewModel: KycViewModel by activityViewModels()
    lateinit var mAdapter: BillAdapter
    lateinit var billList: List<Bill>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }




    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bills, container, false)

        // Inflate the layout for this fragment //        return inflater.inflate(R.layout.fragment_bills, container, false)
        return binding.root
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeRviewAndAdapter()

        binding.newBillFloatingBtn.setOnClickListener {
            onFloatingButtonClick()
        }


    }




    //** Initialized Recycler View
    fun initializeRviewAndAdapter() {
        mAdapter = BillAdapter(this)

        binding.billRecycle.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.billRecycle)
        kycviewModel.getAllBill().observe(viewLifecycleOwner, Observer {
            val animationControler: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(requireContext(),R.anim.slide_up_layout)
            binding.billRecycle.layoutAnimation = animationControler
            billList = it
            mAdapter.setData(it)
        })

        binding.billRecycle.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy <= 0) {

                    binding.newBillFloatingBtn.show()
                } else {
                    binding.newBillFloatingBtn.hide()
                }

            }
        })

    }





    //** On Floating action Button clickListener
    fun onFloatingButtonClick() {
        show()
    }

    fun show() {
        val d = AlertDialog.Builder(requireContext())
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog,requireActivity().findViewById<ConstraintLayout>(R.id.bill_dailogContainer))
        d.setView(view)
        val alertDialog = d.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))
        val b1 = view.findViewById<Button>(R.id.button1)
        val b2 = view.findViewById<Button>(R.id.button2)
        val np = view.findViewById<NumberPicker>(R.id.numberPicker1)
        np?.maxValue = 30
        np?.minValue = 3
        np?.wrapSelectorWheel = false
        b1?.setOnClickListener {
            lifecycleScope.launch(IO) {
                 insertIntoDB(np.value)
            }
            alertDialog.dismiss()
        }
        b2?.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()
    }





    /**
     * Insert into Database
     * */
    suspend fun insertIntoDB(limit: Int) {
        var kycs: List<Kyc>?
        var billno: Int = (requireContext().dataStore.data.first()[Keys.last_billno_key])?.toInt()
                ?: 0
        Log.d(TAG, "insertIntoDB: billno $billno")

        if (billno == 0) {
            billno = 1
            kycviewModel.saveDataInSetting(mapOf(Pair(Keys.last_billno_key, billno.toString())))
            kycs = kycviewModel.getDataByDay(limit)
        } else {
            Log.i(TAG, "insertIntoDB: $billno")
            val lastbill: Bill = kycviewModel.getByBillNo(billno)
            kycs = DateConverter().DateToString(lastbill.endDate)?.let { kycviewModel.getDataByDay(limit, it) }

            billno++
            kycviewModel.saveDataInSetting(mapOf(Pair(Keys.last_billno_key, billno.toString())))
        }


        Log.d("BillFragment", "insertIntoDB: ${kycs?.size} $billno")

        if (kycs != null && kycs.size == limit) {

            Log.d("BillFragment", "inside if : ${kycs.size} $billno")


            val bill = Bill(id = 0,
                    billNumber = billno,
                    startDate = kycs[0].date,
                    endDate = kycs.last().date,
                    issueDate = Date(),
                    passDate = null,
                    billPath = "",
                    billStatus = BillStatus.NOT_PAID,
                    totalValue = Keys.getMonthlyMoney(kycs,requireContext())
            )
//            Log.d(TAG, "insertIntoDB: $bill")
            kycviewModel.addBill(bill)

        } else {
            billno--
            kycviewModel.saveDataInSetting(mapOf(Pair(Keys.last_billno_key, billno.toString())))
            lifecycleScope.launch(Main) { Toasty.warning(requireContext(),"There are't Enough Dates Available. Its Been Only ${kycs?.size?:0} Days",Toast.LENGTH_SHORT,false).show() }

        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        
        if(resultCode == RESULT_OK && requestCode == BILL_PHOTO_REQUEST){
            if(FragmentBillProvider != null && data != null){
                
                lifecycleScope.launch {
                    Log.i(TAG, "onActivityResult: inside lifecycle")
                kycviewModel.updateBillAddPhoto(FragmentBillProvider!!.billNumber,data?.data.toString(),BillStatus.BILL_AVAILABLE)
                    FragmentBillProvider = null
                }
            }else{
                FragmentBillProvider = null
                Log.i(TAG, "onActivityResult: inside else tag")
            }

        }
        Log.i(TAG, "onActivityResult: $resultCode && $requestCode : ${data?.data} : ${data?.getIntExtra("Bill_No",-1)}")

    }



    private val simpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            TODO("Not yet implemented")
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            lifecycleScope.launch(Main){
                val position = viewHolder.adapterPosition
                var billNumber =  kycviewModel.sharedData(Keys.last_billno_key)?.toInt()
                if(billNumber == (position+1)){
                    val dialog = AlertDialog.Builder(requireContext())
                    val view = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_dialog,requireActivity().findViewById<ConstraintLayout>(R.id.delete_dialogContainer))
                    dialog.setView(view)
                    val alertDialog = dialog.create()
                    alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))
                    view.findViewById<Button>(R.id.yes_btn).setOnClickListener {
                        when (direction) {
                            ItemTouchHelper.RIGHT -> {
                                runBlocking { kycviewModel.saveDataInSetting(mapOf(Pair(Keys.last_billno_key,position.toString()))) }
                                kycviewModel.deleteBill(billList[position])
                                mAdapter.notifyItemRemoved(position)
                                Toasty.success(requireContext(),"Delete Successfully", Toast.LENGTH_SHORT,false).show()
                                alertDialog.dismiss()
                            }
                        }
                    }
                    view.findViewById<Button>(R.id.no_btn).setOnClickListener{
                        mAdapter.notifyItemChanged(position)
                        alertDialog.dismiss()
                    }

                    alertDialog.show()
                  /*  val dialog =
                        AlertDialog.Builder(requireContext())
                            .setTitle("Are You Sure !")
                            .setMessage("You Want To Delete")
                            .setNegativeButton("No") { _: DialogInterface, _: Int ->
                                mAdapter.notifyItemChanged(position)
                            }
                            .setPositiveButton("Yes") { _, _ ->
                                when (direction) {
                                    ItemTouchHelper.RIGHT -> {
                                       runBlocking { kycviewModel.saveDataInSetting(mapOf(Pair(Keys.last_billno_key,position.toString()))) }
                                        kycviewModel.deleteBill(billList[position])
                                        mAdapter.notifyItemRemoved(position)
                                        Toasty.success(requireContext(),"Delete Successfully", Toast.LENGTH_SHORT,false).show()
                                    }
                                }
                            }
                            .create()*/

                }else{
                    Toasty.error(requireContext(),"Only the last bill can be Delete",Toast.LENGTH_SHORT,false).show()
                    mAdapter.notifyItemChanged(position)
                }
            }


        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                .addActionIcon(R.drawable.ic_delete)
                .addSwipeRightLabel("DELETE")
                .setSwipeRightLabelColor(Color.WHITE)
                .setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP,20f)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        }


    }


    companion object {

        @JvmStatic
        fun newInstance() = BillsFragment()
    }
}