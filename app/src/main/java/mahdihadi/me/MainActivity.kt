package mahdihadi.me

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.WindowCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.core.content.ContextCompat
import mahdihadi.me.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentOnView()
    }

    private fun setContentOnView() {

        binding.userNameTextView.text = getString(R.string.user_name)

        binding.emailButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
            }
            startActivity(intent)
        }

        binding.callButton.setOnClickListener {
            checkCallPermissionAndAction()
        }

        binding.userImageView.setImageResource(R.drawable.ic_launcher_background)

        fillUserInfoText()

        for (i in 1..5) {
            Toast.makeText(this, "Hello Mahdi Hadi!", Toast.LENGTH_SHORT).show()
        }
//        displayToast(Toast.makeText(this, "Hello Mahdi Hadi!", Toast.LENGTH_SHORT))
    }

    private fun displayToast(toast:Toast) {
        Thread {
            for (i in 1..5) {
                toast.show()
                Thread.sleep(2000)
                toast.cancel()
            }
        }.start()
    }

    private fun fillUserInfoText() {

        val text = resources.openRawResource(R.raw.text_file)
            .bufferedReader().use { it.readText() }

        binding.contentTextView.text = text
    }

    private fun checkCallPermissionAndAction(){
        if (ContextCompat.checkSelfPermission(this, CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            actionNavigateToCallPage()
        } else {
            getCallPermission()
        }
    }

    private fun getCallPermission(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(CALL_PHONE),
            1000
        )
    }

    private fun actionNavigateToCallPage() {
        val intent = Intent(
//            Intent.ACTION_CALL,
            Intent.ACTION_DIAL,
            Uri.parse("tel:09198178163")
        )
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode){
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults.first() == PackageManager.PERMISSION_GRANTED){
                    actionNavigateToCallPage()
                } else {
                    Toast.makeText(this, getString(R.string.message_grant_call_permission), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}