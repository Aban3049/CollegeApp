package com.pandaapps.abmsstudies.about

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pandaapps.abmsstudies.R
import com.pandaapps.abmsstudies.databinding.ActivityAboutUsBinding

class AboutUsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAboutUsBinding

    private companion object {
        private const val TAG = "ABOUT_US_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAboutUsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val resumeDetail = "resume"
        val githubDetail = "github"


        binding.resumeRl.setOnClickListener {
            val intent = Intent(this@AboutUsActivity, AboutDetailActivity::class.java)
            intent.putExtra("Detail", resumeDetail)
            startActivity(intent)
        }

        binding.githubRl.setOnClickListener {
            val intent = Intent(this@AboutUsActivity, AboutDetailActivity::class.java)
            intent.putExtra("Detail", githubDetail)
            startActivity(intent)
        }

        binding.emailiv.setOnClickListener {
            gmailSender()
        }

        binding.emailTv1.setOnClickListener {
            gmailSender()
        }

        binding.whatsAppIv.setOnClickListener {
            openWhatsApp()
        }

        binding.whatsAppTv.setOnClickListener {
            openWhatsApp()
        }


        loadImages()

    }

    private fun gmailSender() {
        val recipient = "allabouttechnolgy@gmail.com"
        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$recipient"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Email body")
        startActivity(Intent.createChooser(intent, "Send Email"))
    }

    private fun loadImages() {
        Log.d(TAG, "loadImages: ")

        try {

            Glide.with(this@AboutUsActivity)
                .load(R.drawable.leader)
                .placeholder(R.drawable.ic_person_white)
                .into(binding.personIv)
        } catch (e: Exception) {
            Log.e(TAG, "loadImages:exception: $e")
            Toast.makeText(this@AboutUsActivity, "${e.message}", Toast.LENGTH_SHORT).show()
        }


    }

    private fun openWhatsApp() {

        try {

            val phoneNumber = "03154595060"
            
            val intent = Intent(Intent.ACTION_VIEW)

            intent.setPackage("com.whatsapp")

            intent.data = Uri.parse("whatsapp://send?phone=" + phoneNumber)

            startActivity(intent)
        }catch (e:Exception){
            Log.e(TAG, "openWhatsApp: $e", )
            Toast.makeText(this@AboutUsActivity, "Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
        }


    }

}





