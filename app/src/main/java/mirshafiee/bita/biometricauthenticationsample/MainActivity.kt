package mirshafiee.bita.biometricauthenticationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.biometricauthenticationsample.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: BiometricViewModelImpl
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            BiometricViewModelImpl.Factory(
                BiometricHelperImpl(
                    this
                )
            )
        ).get(BiometricViewModelImpl::class.java)

        viewModel.checkBiometrics()

        viewModel.getShowBiometric().observe(this, Observer {
            if (it?.first!!) {
                setupBiometrics()
            } else
                txt_biometrics_availability.text = it.second
        })

        setContentView(R.layout.activity_main)
    }

    private fun setupBiometrics() {
        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity, "SUCCESS", Toast.LENGTH_SHORT).show()
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.str_login))
            .setNegativeButtonText("cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}