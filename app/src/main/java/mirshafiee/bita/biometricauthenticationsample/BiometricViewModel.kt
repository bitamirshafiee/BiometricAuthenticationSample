package mirshafiee.bita.biometricauthenticationsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BiometricViewModel : ViewModel(){

    private var showBiometric               : SingleLiveEvent<Pair<Boolean, String>> =
        SingleLiveEvent()
    fun getShowBiometric()                  : SingleLiveEvent<Pair<Boolean, String>> = showBiometric

    abstract fun checkBiometrics()
}
class BiometricViewModelImpl(private val biometricHelper: BiometricHelper) : BiometricViewModel(){

    override fun checkBiometrics() {
        getShowBiometric().postValue(biometricHelper.onBiometricSupportedCheck())
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val biometricHelper: BiometricHelper
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BiometricViewModelImpl(
                biometricHelper
            ) as T
        }
    }
}