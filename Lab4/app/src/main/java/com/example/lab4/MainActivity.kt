package com.example.lab4

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var exoPlayer: ExoPlayer? = null

    private lateinit var playerView: PlayerView
    private lateinit var audioStatus: TextView

    private var isAudio = true
    private var selectedUri: Uri? = null

    companion object {
        private const val PICK_MEDIA_FILE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mediaTypeGroup = findViewById<RadioGroup>(R.id.mediaTypeGroup)
        val chooseFile = findViewById<Button>(R.id.buttonChooseFile)
        val audioPlay = findViewById<Button>(R.id.audioPlay)
        val audioPause = findViewById<Button>(R.id.audioPause)
        val audioStop = findViewById<Button>(R.id.audioStop)
        audioStatus = findViewById(R.id.audioStatus)
        playerView = findViewById(R.id.videoPlayer)
        val editUrl = findViewById<EditText>(R.id.editUrl)
        val buttonPlayUrl = findViewById<Button>(R.id.buttonPlayUrl)

        buttonPlayUrl.setOnClickListener {
            val url = editUrl.text.toString().trim()
            if (url.isEmpty()) {
                Toast.makeText(this, "Введіть URL", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            selectedUri = Uri.parse(url)
            playMedia()
        }


        mediaTypeGroup.setOnCheckedChangeListener { _, id ->
            isAudio = id == R.id.radioAudio
            playerView.visibility = if (isAudio) View.GONE else View.VISIBLE
        }

        chooseFile.setOnClickListener {
            val type = if (isAudio) "audio/*" else "video/*"
            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
                this.type = type
            }
            startActivityForResult(
                Intent.createChooser(intent, "Оберіть файл"),
                PICK_MEDIA_FILE
            )
        }

        audioPlay.setOnClickListener { playMedia() }
        audioPause.setOnClickListener { pauseMedia() }
        audioStop.setOnClickListener { stopMedia() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_MEDIA_FILE && resultCode == Activity.RESULT_OK) {
            selectedUri = data?.data
            playMedia()
        }
    }

    private fun playMedia() {
        selectedUri?.let { uri ->
            if (isAudio) {
                releaseAudio()
                mediaPlayer = MediaPlayer().apply {
                    setDataSource(applicationContext, uri)
                    prepare()
                    start()
                }
                audioStatus.text = "Грає аудіо..."
            } else {
                releaseVideo()
                exoPlayer = ExoPlayer.Builder(this).build().also { player ->
                    playerView.player = player
                    player.setMediaItem(MediaItem.fromUri(uri))
                    player.prepare()
                    player.play()
                }
            }
        } ?: run {
            Toast.makeText(this, "Файл не вибрано", Toast.LENGTH_SHORT).show()
        }
    }

    private fun pauseMedia() {
        if (isAudio && ::mediaPlayer.isInitialized && mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            audioStatus.text = "Пауза"
        } else {
            exoPlayer?.pause()
        }
    }

    private fun stopMedia() {
        if (isAudio && ::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            audioStatus.text = "Зупинено"
        } else {
            exoPlayer?.stop()
        }
    }

    private fun releaseAudio() {
        if (::mediaPlayer.isInitialized) mediaPlayer.release()
    }

    private fun releaseVideo() {
        exoPlayer?.release()
        exoPlayer = null
    }

    override fun onStop() {
        super.onStop()
        releaseAudio()
        releaseVideo()
    }
}
