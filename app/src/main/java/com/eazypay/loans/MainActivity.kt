package com.eazypay.loans

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintManager
import android.provider.ContactsContract
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.WebView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var webView: WebView? = null
   // private var fullName: String? = null
   // private var Gender: String? = null
   // private var Username: String? = null
 //   private var Email: String? = null
    private var ProfileImage: String? = null
    private var Email: String? = null
    private var Password: String? = null
    private var PhoneNumber: String? = null
    private var UID: String? = null
    private var UserName: String? = null

    private var mFirebaseDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null
    private val mAuthListener: AuthStateListener? = null
    private var myRef: DatabaseReference? = null
    private var userID: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webView = findViewById(R.id.webview1)
        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        myRef = mFirebaseDatabase!!.reference
        val user = mAuth?.getCurrentUser()
        userID = user!!.uid

        myRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    ProfileImage = ds.child(userID!!).child("email").value.toString()
                   // Email = ds.child(userID!!).child("fullName").value.toString()
                  //  Password = ds.child(userID!!).child("gender").value.toString()
                   // PhoneNumber = ds.child(userID!!).child("profileImageUrl").value.toString()
                    UID = ds.child(userID!!).child("uid").value.toString()
                    UserName = ds.child(userID!!).child("username").value.toString()

                    val html = "<!DOCTYPE html>\n" +
                            "<html>\n" +
                            "<body> \n" +
                            "<center>\n" +
                            "<style>\n" +
                            ".signature, .title { \n" +
                            "float:left;\n" +
                            "  border-top: 1px solid #000;\n" +
                            "  width: 150px height:150px; \n" +
                            "  text-align: center;\n" +
                            "}\n" +
                            "</style>\n" +
                            "<div style=\"width:700px; height:600px; padding:20px; text-align:center; border: 10px solid #787878\">\n" +
                            "<div style=\"width:650px; height:550px; padding:20px; text-align:center; border: 5px solid #FFD700\">\n" +
                            "\t<span style=\"font-size:50px; font-weight:bold\">Certificate of Clearance</span>\n" +
                            "<br><br>\n" +
                            "       <span style=\"font-size:25px\"><i>This is to certify that</i>" +
                            "<img src=$ProfileImage width=\"120px\" height=\"120px\"style=\"margin-top:10px;float:left;border-radius: 50%\">" +
                            "<img src=\"https://firebasestorage.googleapis.com/v0/b/eazypay-75d00.appspot.com/o/QR.jpeg?alt=media&token=51c3b3ff-fea0-45fb-ae12-5573dd89e7b3\" width=\"120px\" height=\"120px\"style=\"margin-top:10px;float:right\"></span>\n" +
                            "       <br><br>\n" +
                            "       <span style=\"font-size:30px\"><b>$UserName</b></span><br/><br/>\n" +
                            "       <span style=\"font-size:25px\"><i>has satisfied the</i></span> <br/><br/>\n" +
                            "       <span style=\"font-size:30px\">CREDIT REFERENCE BUREAU</span> <br/><br/>\n" +
                            "       <span style=\"font-size:25px\"><i>that he/she has no accrued debt whatsoever, as at the time of writing,</i></span> <br/><br/>\n" +

                            "       <span style=\"font-size:20px\">with a credit score of <b>85%</b></span>\n" +
                            " <br/><br/>\n" +
                            "       <span style=\"font-size:20px\"><i>Certificate ID:-</i></span><br>\n" +
                            "       <span style=\"font-size:22px\"><i>$UID</i></span><br>\n" +
                            "<table style=\"margin-top:1px;float:left\">\n" +
                            "     <tr>\n" +
                            "            <td><span><b><img src=\"https://firebasestorage.googleapis.com/v0/b/eazypay-75d00.appspot.com/o/PicsArt_01-21-09.40.54%20(1).png?alt=media&token=fb61c7ee-74bd-430e-9e26-61f79727c513\" width=\"80\" height=\"100\"></b></td>\n" +
                            "     \n" +
                            "      \n" +
                            "</table>\n" +
                            "<table style=\"margin-top:40px;float:right\">\n" +
                            "    <tr>\n" +
                            "            <td><span><img src=\"https://firebasestorage.googleapis.com/v0/b/eazypay-75d00.appspot.com/o/metro-trns.jpeg?alt=media&token=09a63d6f-bec1-4b8a-a876-d8e8a9b6d6ac\" width=\"180\" height=\"70\"></td>\n" +
                            " \n" +
                            " \n" +
                            "</table>\n" +
                            "</div>\n" +
                            "</div>\n" +
                            "</center>\n" +
                            "</html>"

                    webView?.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    // CREATE THE WEBWIEW TO PRINT IN PDF FORMET AND DOWNLOAD THE LOCAL MOBILE STORE
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun CreatePdf(view: View?) {
        val context: Context = this@MainActivity
        val printManager = this@MainActivity.getSystemService(Context.PRINT_SERVICE) as PrintManager
        var adapter: PrintDocumentAdapter? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = webView!!.createPrintDocumentAdapter()
        }
        val JobName = getString(R.string.app_name) + "Document"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val printJob = printManager.print(JobName, adapter, PrintAttributes.Builder().build())
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}



