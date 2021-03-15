package debashis.me.ckycmanager.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import debashis.me.ckycmanager.db.Kyc
import debashis.me.ckycmanager.db.KycDatabase
import debashis.me.ckycmanager.db.KycRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KycViewModel(application: Application): AndroidViewModel(application) {

        private val repository: KycRepository
        fun getDataByMonth(month:Int) = repository.dataByMonth(month)

    init {
        val kycDao = KycDatabase.getDatabase(application)?.kycDao
        repository = KycRepository(kycDao!!)

    }


    fun addKyc(kyc: Kyc){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addKyc(kyc)
        }

    }


}