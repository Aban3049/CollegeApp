package com.pandaapps.abmsstudies

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.pandaapps.abmsstudies.ChatRoom.activities.ChatRoomActivity
import com.pandaapps.abmsstudies.NoticeBoard.activities.NoticeActivity
import com.pandaapps.abmsstudies.NoticeBoard.activities.NoticeAdminActivity
import com.pandaapps.abmsstudies.about.AboutUsActivity
import com.pandaapps.abmsstudies.books.activities.BooksAdminDashboardActivity
import com.pandaapps.abmsstudies.books.activities.BooksDashboardUserActivity
import com.pandaapps.abmsstudies.databinding.ActivityMainHomeBinding
import com.pandaapps.abmsstudies.gallery.activities.GalleryActivity
import com.pandaapps.abmsstudies.mathLecture.MathLecturesActivity
import com.pandaapps.abmsstudies.papers.activities.AdminPaperActivity
import com.pandaapps.abmsstudies.papers.activities.PaperUserActivity
import com.pandaapps.abmsstudies.sell.activities.LogIn
import com.pandaapps.abmsstudies.sell.activities.MainActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.pandaapps.abmsstudies.Ai.AIActivity

class MainHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private companion object {
        private const val TAG = "MAIN_HOME_ACTIVITY_TAG"
    }

    private var userMode = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        showsplash()




        getUserName()
        subscribeToTopic()

        if (firebaseAuth.currentUser == null) {
            startActivity(Intent(this@MainHomeActivity, LogIn::class.java))
        } else {
            updateFcmToken()
            askNotificationPermission()
        }

        binding.books.setOnClickListener {
            checkUserMode()
        }
        binding.chatRoomCv.setOnClickListener {
            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this, ChatRoomActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, ChatRoomActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()

                }
            }

        }

        binding.personHomeIv.setOnClickListener {
            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this@MainHomeActivity, AccountActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, AccountActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }

        binding.buyCv.setOnClickListener {
            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this@MainHomeActivity, MainActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, MainActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }

        binding.mathLecturesCv.setOnClickListener {
            startActivity(Intent(this@MainHomeActivity, MathLecturesActivity::class.java))
        }

        binding.noticeCv.setOnClickListener {

            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this, NoticeActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, NoticeAdminActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }

        binding.aiCv.setOnClickListener{
            startActivity(Intent(this@MainHomeActivity,AIActivity::class.java))
        }


        binding.accountCv.setOnClickListener {
            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this@MainHomeActivity, AccountActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, AccountActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }

        binding.logoutCv.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this@MainHomeActivity, LogIn::class.java))
            finish()
        }
        binding.aboutUsCv.setOnClickListener {
            startActivity(Intent(this@MainHomeActivity, AboutUsActivity::class.java))

        }
        binding.galleryCv.setOnClickListener {

            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this@MainHomeActivity, GalleryActivity::class.java))
                }

                "Guest" -> {
                    Toast.makeText(this@MainHomeActivity, "Account Restricted", Toast.LENGTH_SHORT)
                        .show()
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, GalleryActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }


        }
        binding.paperCv.setOnClickListener {
            when (userMode) {
                "USER" -> {
                    startActivity(Intent(this@MainHomeActivity, PaperUserActivity::class.java))
                }

                "Admin" -> {
                    startActivity(Intent(this@MainHomeActivity, AdminPaperActivity::class.java))
                }

                "Guest" -> {
                    startActivity(Intent(this@MainHomeActivity, PaperUserActivity::class.java))
                }

                else -> {
                    Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }


    }


    private fun getUserName() {
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child("${firebaseAuth.uid}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = "${snapshot.child("name").value}"
                val profileImageURl = "${snapshot.child("profileImageURl").value}"
                userMode = "${snapshot.child("userMode").value}"

                binding.nameTv.text = name

                try {
                    Glide.with(this@MainHomeActivity)
                        .load(profileImageURl)
                        .placeholder(R.drawable.ic_person_white)
                        .into(binding.personHomeIv)
                } catch (e: Exception) {
                    Log.e(TAG, "onDataChanged :", e)
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled: $error")

            }
        })
    }


    private fun showsplash() {
        val dialog =
            Dialog(this@MainHomeActivity, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.activity_splash_screan)
        dialog.setCancelable(true)
        dialog.show()
        val handler = Handler()
        val runnable = Runnable {
            if (firebaseAuth.currentUser != null) {
                dialog.dismiss()
            } else if (firebaseAuth.currentUser == null) {
                startActivity(Intent(this@MainHomeActivity, LogIn::class.java))
                finish()

            }

        }
        handler.postDelayed(runnable, 5000)
    }

    private fun checkUserMode() {
        when (userMode) {
            "USER" -> {
                startActivity(Intent(this, BooksDashboardUserActivity::class.java))
            }

            "Guest" -> {
                startActivity(Intent(this, BooksDashboardUserActivity::class.java))
            }

            "Admin" -> {
                startActivity(
                    Intent(
                        this@MainHomeActivity,
                        BooksAdminDashboardActivity::class.java
                    )
                )
            }

            else -> {
                Toast.makeText(this@MainHomeActivity, "User Not Recognized", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun updateFcmToken() {
        val myUid = "${firebaseAuth.uid}"
        Log.d(TAG, "updateFcmToken: ")

        FirebaseMessaging.getInstance().token

            .addOnSuccessListener { fcmToken ->

                Log.d(TAG, "updateFcmToken: fcmToken $fcmToken")
                val hashMap = HashMap<String, Any>()
                hashMap["fcmToken"] = fcmToken

                val ref = FirebaseDatabase.getInstance().getReference("Users")
                ref.child(myUid)
                    .updateChildren(hashMap)
                    .addOnSuccessListener {
                        Log.d(TAG, "updateFcmToken: FCM Token update to db success")
                    }
                    .addOnFailureListener { e ->
                        Log.e(TAG, "updateFcmToken: ", e)
                    }

            }
            .addOnFailureListener { e ->
                Log.e(TAG, "updateFcmToken: ", e)
            }

    }

    private fun askNotificationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {

            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) ==
                PackageManager.PERMISSION_DENIED
            ) {
                requestNotificationPermission.launch(android.Manifest.permission.POST_NOTIFICATIONS)
            }

        }

    }

    private val requestNotificationPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->

        Log.d(TAG, "requestNotificationPermission: isGranted: $isGranted")
    }

    private fun subscribeToTopic() {
// Get an instance of FirebaseMessaging
        val firebaseMessaging = Firebase.messaging

// Define the topic name
        val topic = "/topics/all"

// Subscribe the app to the topic
        firebaseMessaging.subscribeToTopic(topic)
            .addOnSuccessListener {
                Log.d(TAG, "Subscribed to topic: $topic")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, "Failed to subscribe to topic: $topic", exception)
            }

    }


}









