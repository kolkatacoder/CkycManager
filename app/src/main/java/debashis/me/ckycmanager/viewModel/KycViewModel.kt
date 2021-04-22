package debashis.me.ckycmanager.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.material.tabs.TabLayout
import debashis.me.ckycmanager.adapters.BillStatus
import debashis.me.ckycmanager.db.*
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class KycViewModel(val mapplication: Application) : AndroidViewModel(mapplication) {

    private val TAG = "KycViewModel"

    private val kycRepository: KycRepository
    private val billRepository: BillRepository

    init {
        val kycDao = KycDatabase.getDatabase(mapplication)?.kycDao
        kycRepository = KycRepository(kycDao!!)
        billRepository = BillRepository(KycDatabase.getDatabase(mapplication)!!.billDao)

    }

    fun makeToast() = Toast.makeText(mapplication,"this is a demo",Toast.LENGTH_SHORT).show()



    /********Preferences*************************/
    suspend fun saveDataInSetting(pair: Map<Preferences.Key<String>, String>) {
        mapplication.dataStore.edit { preference ->
            pair.forEach {
                preference[it.key] = it.value
            }
        }
    }

    fun readDataFromSetting(key: Preferences.Key<String>): Flow<String> =
        mapplication.dataStore.data.map { setting ->
            setting[key] ?: "0"
        }

    suspend fun sharedData(key:Preferences.Key<String>) = viewModelScope.async { mapplication.dataStore.data.first()[key] }.await()


    /********************************Kyc DAO *****************************************************/

    fun addKyc(kyc: Kyc) = viewModelScope.launch(IO) {


      val n =  kycRepository.addKyc(kyc)
        Log.i(TAG, "addKyc: $n")
        if(n>0){
            viewModelScope.launch(Main){ Toasty.success(mapplication,"Data Added Successfully").show()}
        }else{
            Log.i(TAG, "addKyc: inside else $n")
            viewModelScope.launch(Main){ Toasty.error(mapplication,"Data Already Available").show()}
        }

        /*  if( responseCode > 0){
//              Toast.makeText(mapplication,"Data Added Successfully :)",Toast.LENGTH_SHORT).show()
             Log.i("KycViewModel", "addKyc: data add successfully :) $responseCode")
         }else{
//              Toast.makeText(mapplication,"Data Already have been exist :(",Toast.LENGTH_SHORT).show()
             Log.i("KycViewModel", "addKyc: data already exist :( $responseCode")

         }*/

    }

    fun deleteKyc(kyc: Kyc) {
        viewModelScope.launch(IO){
            kycRepository.delete(kyc)
        }
    }

    val allMonth: LiveData<List<String>> = kycRepository.getAllMonth()

    fun getDataByMonth(month: String) = kycRepository.dataByMonth(month)

    fun getDataByDay(limit: Int) = kycRepository.getDataByDay(limit)

    suspend fun getDataByDay(limit: Int, afterDate: String) =
        withContext(viewModelScope.coroutineContext + IO) {
            kycRepository.getDataByDay(
                limit,
                afterDate
            )
        }


    /*************************************Bill DAO*************************************************/
    fun addBill(bill: Bill) {
        viewModelScope.launch(IO) {
            billRepository.addbill(bill)
        }
    }

    fun deleteBill(bill: Bill){
        viewModelScope.launch(IO) {
            billRepository.deleteBill(bill)
        }
    }

    fun getAllBill() = billRepository.getAllBill()

    suspend fun getByBillNo(no: Int) =
        withContext(viewModelScope.coroutineContext + IO) { billRepository.getBillByNo(no) }

    suspend fun updatebillByBillNo(no: Int, date: Date, status: BillStatus) =
        viewModelScope.launch(IO) {
            billRepository.updateBillByNo(no, date, status)
        }

    suspend fun updateBillAddPhoto(no: Int, path: String, status: BillStatus) =
        viewModelScope.launch(IO) {
            billRepository.updateBillAddPhoto(no, path, status)
        }
}
