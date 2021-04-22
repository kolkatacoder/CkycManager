package debashis.me.ckycmanager.fragments

import android.app.DatePickerDialog
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
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import debashis.me.ckycmanager.DataEntryActivity
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.adapters.RecentAdapter
import debashis.me.ckycmanager.data.*
import debashis.me.ckycmanager.databinding.FragmentDashboardBinding
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.viewModel.KycViewModel
import es.dmoral.toasty.Toasty
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import java.util.*


class DashboardFragment : Fragment() {

    private val TAG = "DashBoardFragment"

    lateinit var madapter: RecentAdapter
    lateinit var kycList: List<Kyc>


    val kycViewModel: KycViewModel by activityViewModels()
    lateinit var binding: FragmentDashboardBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
//        return inflater.inflate(R.layout.fragment_dashboard, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinner()
        initFloatinfbutton()
        init()

    }

    //Initialize Recyclerview
    private fun init() {

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.recent.layoutManager = layoutManager
        ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.recent)
        
        binding.recent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                Log.i(TAG, "onScrollStateChanged: newState: $newState")
                if(newState == RecyclerView.SCROLL_INDICATOR_START){
                    binding.fab.show()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.i(TAG, "onScrolled: dx:$dx  dy:$dy")
                if (dy <= 0 ) {
                    binding.fab.show()
                } else {
                    binding.fab.hide()
                }
            }
        })
        
    }

    fun initSpinner() {
        kycViewModel.allMonth.observe(viewLifecycleOwner, Observer { list ->
            val adapter = ArrayAdapter(
                requireContext(), R.layout.drop_down_style, listToMonthIndexList(
                    list
                )
            )
            binding.selectMonth.adapter = adapter
        })

        binding.selectMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                kycViewModel.getDataByMonth(
                    kycViewModel.allMonth.value?.get(position)?.toMonthIndex() ?: "03"
                ).observe(viewLifecycleOwner, Observer { kycs ->
                    val animationControler: LayoutAnimationController =
                        AnimationUtils.loadLayoutAnimation(
                            requireContext(),
                            R.anim.slide_up_layout
                        )
                    binding.recent.layoutAnimation = animationControler
                    kycList = kycs
                    madapter = RecentAdapter(kycList)
                    binding.recent.adapter = madapter
                    binding.totalMonthValue.text =
                        "${Keys.getMonthlyMoney(kycs, requireContext())}"
                })
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }


    //Initialize floating button
    fun initFloatinfbutton() {

        binding.fab.setOnClickListener {

            val dialog = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.date_choose_dailog,requireActivity().findViewById<ConstraintLayout>(R.id.layout_dialogeContainer))
            dialog.setView(view)
            val alert = dialog.create()
            alert.window?.setBackgroundDrawable(ColorDrawable(0))
            view.findViewById<Button>(R.id.today)?.setOnClickListener {

                val cal = Calendar.getInstance()
                val date = Dates(
                    cal.get(Calendar.DAY_OF_MONTH), (cal.get(Calendar.MONTH) + 1), cal.get(
                        Calendar.YEAR
                    )
                )
                startDataEntryActivity(date)
                alert.dismiss()
            }

            view.findViewById<Button>(R.id.pick_date)?.setOnClickListener {

                pickupdate()
                alert.dismiss()
            }




            alert.show()

        }
    }


    // Pick Date
    private fun pickupdate() {

        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    val date = Dates(dayOfMonth, (month + 1), year)
                    startDataEntryActivity(date)
                }

            },
            cal[Calendar.YEAR],
            cal[Calendar.MONTH],
            cal[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()

    }

    // Start second Activity
    fun startDataEntryActivity(date: Dates) {
        Intent(requireContext(), DataEntryActivity::class.java).apply {
            putExtra(Keys.DAY, date.day)
            putExtra(Keys.MONTH, date.month)
            putExtra(Keys.YEAR, date.year)
            startActivity(this)
        }
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
            val position = viewHolder.adapterPosition

            val dialog = AlertDialog.Builder(requireContext())
            val view = LayoutInflater.from(requireContext()).inflate(R.layout.delete_alert_dialog,requireActivity().findViewById<ConstraintLayout>(R.id.delete_dialogContainer))
            dialog.setView(view)
            val alertDialog = dialog.create()
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(0))
            view.findViewById<Button>(R.id.yes_btn).setOnClickListener {
                when (direction) {
                    ItemTouchHelper.RIGHT -> {
                        kycViewModel.deleteKyc(kycList[position])
                        madapter.notifyItemRemoved(position)
                        Toasty.success(requireContext(),"Delete Successfully", Toast.LENGTH_SHORT,false).show()
                        alertDialog.dismiss()
                    }
                }
            }
            view.findViewById<Button>(R.id.no_btn).setOnClickListener{
                madapter.notifyItemChanged(position)
                alertDialog.dismiss()
            }

            alertDialog.show()


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

            RecyclerViewSwipeDecorator.Builder(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
                .addBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
                .addActionIcon(R.drawable.ic_delete)
                .addSwipeRightLabel("DELETE")
                .setSwipeRightLabelColor(Color.WHITE)
                .setSwipeRightLabelTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
                .create()
                .decorate()
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        }


    }


    companion object {

        @JvmStatic
        fun newInstance() = DashboardFragment()
    }
}