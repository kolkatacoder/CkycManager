package debashis.me.ckycmanager.db

import androidx.lifecycle.LiveData

class KycRepository(private val kycDao: KycDAO) {

    fun dataByMonth(month: String): LiveData<List<Kyc>> = kycDao.getDataByMonth(month)

    fun getAllMonth(): LiveData<List<String>> = kycDao.allMonth()


    fun getDataByDay(limit: Int): List<Kyc> = kycDao.getDataByDay(limit)

    fun getDataByDay(limit: Int, afterDate: String): List<Kyc> =
        kycDao.getDataByDay(limit, afterDate)

    suspend fun addKyc(kyc: Kyc): Long = kycDao.insert(kyc)

    suspend fun delete(kyc: Kyc) = kycDao.delete(kyc)


}