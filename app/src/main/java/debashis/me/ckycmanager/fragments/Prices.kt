package debashis.me.ckycmanager.fragments

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.xw.repo.BubbleSeekBar
import debashis.me.ckycmanager.R
import debashis.me.ckycmanager.databinding.FragmentPricesBinding


class Prices : Fragment() {


   lateinit var binding : FragmentPricesBinding




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

        binding.scanValue.text = binding.scanSeekbar.progress.toString()




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

    }






    companion object {

        fun newInstance() = Prices()

    }
}