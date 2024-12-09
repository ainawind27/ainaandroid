package com.example.mp3alquran

import com.example.mp3alquran.R

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var nowPlayingTextView: TextView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        nowPlayingTextView = findViewById(R.id.tvNowPlaying)

        // List audio yang tersedia
        val audioList = listOf(
            AudioModel("Song 1", R.raw.dhuhaa),
            AudioModel("Song 2", R.raw.lail),
            AudioModel("Song 3", R.raw.syams)
        )
//aaa
        // Atur RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AudioAdapter(audioList) { audio ->
            playAudio(audio)
        }
    }

    private fun playAudio(audio: AudioModel) {
        // Jika ada media yang sedang diputar, hentikan
        mediaPlayer?.release()

        // Inisialisasi MediaPlayer baru
        mediaPlayer = MediaPlayer.create(this, audio.fileResId)
        mediaPlayer?.start()

        // Tampilkan informasi lagu yang sedang diputar
        nowPlayingTextView.text = "Now Playing: ${audio.title}"

        // Hentikan MediaPlayer saat selesai
        mediaPlayer?.setOnCompletionListener {
            nowPlayingTextView.text = "Now Playing: None"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}
