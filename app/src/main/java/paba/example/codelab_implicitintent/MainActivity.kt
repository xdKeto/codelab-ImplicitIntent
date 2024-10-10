package paba.example.codelab_implicitintent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.CalendarContract
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var _kirimPesan = findViewById<Button>(R.id.kirimPesan)
        _kirimPesan.setOnClickListener {
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra("address", "08123460265")
                putExtra("sms_body", "ISI SMS")
                type = "text/plain"

            }

            if (intent.resolveActivity(packageManager) != null){
                startActivity(Intent.createChooser(intent, "PILIH APLIKASI") )
            }
        }

        var _timerAlarm = findViewById<Button>(R.id.timerAlarm)
        var _jamAlarm = findViewById<EditText>(R.id.jamAlarm)
        var _menitAlarm = findViewById<EditText>(R.id.menitAlarm)
        var _ketAlarm = findViewById<EditText>(R.id.ketAlarm)
        _timerAlarm.setOnClickListener {
            val jam = _jamAlarm.text.toString().toInt()
            val menit = _menitAlarm.text.toString().toInt()
            val ket = _ketAlarm.text.toString()

            val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, ket)
                putExtra(AlarmClock.EXTRA_HOUR,  jam)
                putExtra(AlarmClock.EXTRA_MINUTES, menit)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            }

            startActivity(intent)
        }

        var _mulaiAlarm = findViewById<Button>(R.id.mulaiAlarm)
        _mulaiAlarm.setOnClickListener {
            val intent = Intent(AlarmClock.ACTION_SET_TIMER).apply {
                putExtra(AlarmClock.EXTRA_MESSAGE, "ISI ALARM")
                putExtra(AlarmClock.EXTRA_LENGTH, 40)
                putExtra(AlarmClock.EXTRA_SKIP_UI, true)

            }

            startActivity(intent)

        }

        var _TvAlamat = findViewById<EditText>(R.id.tvAlamat)
        var _buttonWebsite = findViewById<Button>(R.id.buttonWebsite)
        _buttonWebsite.setOnClickListener {
            val _webIntent = Intent (
                Intent.ACTION_VIEW,
                Uri.parse("http://" + _TvAlamat.text.toString()))

            if (intent.resolveActivity(packageManager) != null){
                startActivity(_webIntent)
            }
        }

        var _setEvent = findViewById<Button>(R.id.setEvent)
        var eventTitle = findViewById<EditText>(R.id.titleEvent)
        var eventDesc = findViewById<EditText>(R.id.descEvent)
        var emailADD= findViewById<EditText>(R.id.emailADD)
        var eventStart = findViewById<EditText>(R.id.startEvent)
        var eventEnd = findViewById<EditText>(R.id.endEvent)
        _setEvent.setOnClickListener {
            val title = eventTitle.text.toString()
            val desc = eventDesc.text.toString()
            val email = emailADD.text.toString()
            val start = eventStart.text.toString()
            val end = eventEnd.text.toString()

            Log.i("test",start)

            val waktuAwal : Long = Calendar.getInstance().run {
                set(
                    start.substring(0, 4).toInt(),
                    start.substring(5, 7).toInt(),
                    start.substring(8, 10).toInt(),
                    start.substring(11, 13).toInt(),
                    start.substring(14, 16).toInt())
                timeInMillis
            }

            val waktuAkhir : Long = Calendar.getInstance().run {
                set(
                    end.substring(0, 4).toInt(),
                    end.substring(5, 7).toInt(),
                    end.substring(8, 10).toInt(),
                    end.substring(11, 13).toInt(),
                    end.substring(14, 16).toInt())
                timeInMillis
            }

            val intent = Intent(Intent.ACTION_INSERT).apply {
                data = CalendarContract.Events.CONTENT_URI
                putExtra(CalendarContract.Events.TITLE, title)
                putExtra(CalendarContract.Events.DESCRIPTION, desc)
                putExtra(CalendarContract.Events.ACCOUNT_NAME, email)
                putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, waktuAwal)
                putExtra(CalendarContract.EXTRA_EVENT_END_TIME, waktuAkhir)
            }

            startActivity(intent)
        }

        var _buttonCam = findViewById<Button>(R.id.buttonCam)
        _buttonCam.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, pic_id)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val _tampilPic = findViewById<ImageView>(R.id.tampilPic)
        if (requestCode == pic_id && resultCode == Activity.RESULT_OK) {
            val photo: Bitmap = data!!.extras!!.get("data") as Bitmap
            _tampilPic.setImageBitmap(photo)
        }
    }

    companion object {
        private const val pic_id = 123
    }
}