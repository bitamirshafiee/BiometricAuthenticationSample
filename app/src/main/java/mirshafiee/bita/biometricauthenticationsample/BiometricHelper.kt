package mirshafiee.bita.biometricauthenticationsample

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import com.example.biometricauthenticationsample.R

interface BiometricHelper {

    fun onBiometricSupportedCheck(): Pair<Boolean, String>

    fun isBiometricAvailable(): Boolean
}

class BiometricHelperImpl constructor(private val ctx: AppCompatActivity) :
    BiometricHelper {

    private val biometricManager = BiometricManager.from(ctx)


    override fun isBiometricAvailable() =
        biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS


    override fun onBiometricSupportedCheck(): Pair<Boolean, String> {
        return when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> Pair(
                true,
                ctx.getString(R.string.str_biometric_unavailable_device)
            )
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Pair(
                false,
                ctx.getString(R.string.str_biometric_unavailable_device)
            )
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Pair(
                false,
                ctx.getString(R.string.str_biometric_currently_unavailable)
            )
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Pair(
                false,
                ctx.getString(R.string.str_biometric_credentials_not_added)
            )
            else -> Pair(
                false, ctx.getString(R.string.str_biometric_unavailable)
            )
        }
    }
}
