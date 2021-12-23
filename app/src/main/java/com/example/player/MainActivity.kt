package com.example.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.net.Uri


enum class MediaFormat {
    MP3,
    MP4,
    NONE
}

class MainActivity : AppCompatActivity() {
    private lateinit var vvPlayer: VideoView
    private lateinit var etOnlineResource: EditText
    private lateinit var mcController: MediaController
    private lateinit var bPlayOnline: Button
    private lateinit var rgFormat: RadioGroup
    private var mediaFormat = MediaFormat.NONE
    private lateinit var bPlayLocal: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vvPlayer = findViewById(R.id.vv_player)
        etOnlineResource = findViewById(R.id.et_media_url)
        mcController = MediaController(this)
        bPlayOnline = findViewById(R.id.b_play_online)
        rgFormat = findViewById(R.id.rg_media_type)
        bPlayLocal = findViewById(R.id.b_local_play)

        vvPlayer.setMediaController(mcController)
        mcController.setMediaPlayer(vvPlayer)

        bPlayOnline.setOnClickListener{
            val url = etOnlineResource.text.toString()
            if (url.isEmpty()) {
                Toast.makeText(applicationContext, "Error: empty url!", Toast.LENGTH_SHORT).show()
            }
            else{
                when(mediaFormat) {
                    MediaFormat.MP3 -> playAudio(url)
                    MediaFormat.MP4 -> playVideo(url)
                    MediaFormat.NONE -> {
                        Toast.makeText(applicationContext, "Choose correct media format", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                }
            }
        }

        bPlayLocal.setOnClickListener{
            when(mediaFormat) {
                MediaFormat.MP3 -> playAudio("android.resource://" + packageName + "/" + R.raw.audio)
                MediaFormat.MP4 -> playVideo("android.resource://" + packageName + "/" + R.raw.video)
                MediaFormat.NONE -> {
                    Toast.makeText(applicationContext, "Choose correct media format", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }
        }


        rgFormat.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener {_, checkedId ->
            mediaFormat = when(checkedId){
                R.id.rb_audio_option -> MediaFormat.MP3
                R.id.rb_video_option -> MediaFormat.MP4
                else -> MediaFormat.NONE
            }

        })
    }

    private fun playVideo(source: String) {
        vvPlayer.stopPlayback()
        vvPlayer.alpha = 1F
        var uri = Uri.parse(source)
        vvPlayer.setVideoURI(uri)
        vvPlayer.start()
    }

    private fun playAudio(source: String) {
        vvPlayer.stopPlayback()
        vvPlayer.alpha = 0F
        vvPlayer.setVideoURI(Uri.parse(source))
        vvPlayer.start()
        mcController.show(0)
    }
}


// https://static.videezy.com/system/resources/previews/000/056/873/original/Comp-3-zoom.mp4
// https://samplelib.com/lib/preview/mp3/sample-12s.mp3