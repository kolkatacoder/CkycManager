package debashis.me.ckycmanager.db

import androidx.lifecycle.LiveData
import java.time.Month

class KycRepository(private val kycDao: KycDAO) {

    fun dataByMonth(month: Int):LiveData<List<Kyc>>{
        return kycDao.getDataByMonth(month)
    }


  suspend  fun addKyc(kyc:Kyc){
        kycDao.insert(kyc)


    }

}