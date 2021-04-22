package debashis.me.ckycmanager.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.xw.repo.BubbleSeekBar
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.data.Keys
import debashis.me.ckycmanager.databinding.FragmentPricesBinding
import debashis.me.ckycmanager.viewModel.KycViewModel
import debashis.me.ckycmanager.viewModel.dataStore
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



class PricesFragment : Fragment() {


   lateinit var binding : FragmentPricesBinding
   val kycviewModel : KycViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding  = DataBindingUtil.inflate(inflater,R.layout.fragment_prices,container,false)
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_prices, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            observer()

            lifecycleScope.launch {
           val preferences =  requireContext().dataStore.data.first()
                preferences[Keys.scan_key]?.let { binding.scanSeekbar.setProgress(it.toFloat()) }
            }


        kycviewModel.readDataFromSetting(Keys.scan_key)
        binding.scanSeekbar.onProgressChangedListener = object :BubbleSeekBar.OnProgressChangedListener{
            override fun onProgressChanged(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                binding.scanValue.text = progress.toString()
            }

            override fun getProgressOnActionUp(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float) {
                binding.scanValue.text = progress.toString()
            }

            override fun getProgressOnFinally(bubbleSeekBar: BubbleSeekBar?, progress: Int, progressFloat: Float, fromUser: Boolean) {
                binding.scanValue.text = progress.toString()
            }


        }

        binding.saveButton.setOnClickListener {

            var pair = mutableMapOf<Preferences.Key<String>,String>()
            pair[Keys.scan_key] = binding.scanSeekbar.progress.toString()
            pair[Keys.maker_key] = binding.makerValue.text.toString()
            pair[Keys.checker_key] = binding.chekerValue.text.toString()
            pair[Keys.web_maker_key] = binding.webMakerValue.text.toString()
            pair[Keys.web_checker_key] = binding.webChekerValue.text.toString()
            pair[Keys.account_number_key] = binding.accountNumberingValue.text.toString()


            lifecycleScope.launch {
                kycviewModel.saveDataInSetting(pair)
                Toasty.info(requireContext(),"Price Save Successfully", Toast.LENGTH_SHORT,false).show()
            }
        }

    }

    fun observer(){
        kycviewModel.readDataFromSetting(Keys.scan_key).asLiveData().observe(viewLifecycleOwner) {
            binding.scanValue.text = it

        }
        kycviewModel.readDataFromSetting(Keys.maker_key).asLiveData().observe(viewLifecycleOwner) {
            binding.makerValue.setText(it)

        }
        kycviewModel.readDataFromSetting(Keys.checker_key).asLiveData().observe(viewLifecycleOwner) {
            binding.chekerValue.setText(it)

        }
        kycviewModel.readDataFromSetting(Keys.web_maker_key).asLiveData().observe(viewLifecycleOwner) {
            binding.webMakerValue.setText(it)

        }
        kycviewModel.readDataFromSetting(Keys.web_checker_key).asLiveData().observe(viewLifecycleOwner) {
            binding.webChekerValue.setText(it)

        }
        kycviewModel.readDataFromSetting(Keys.account_number_key).asLiveData().observe(viewLifecycleOwner) {
            binding.accountNumberingValue.setText(it)

        }
    }






    companion object {

        fun newInstance() = PricesFragment()

    }
}