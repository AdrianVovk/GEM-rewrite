package substance.mobile.gem.util

import android.content.Context
import android.media.AudioManager
import android.support.v7.widget.AppCompatSeekBar
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.SeekBar
import android.widget.Toast
import substance.mobile.gem.R

/**
 * TEMPORARY
 */
object NowPlayingUtil {

    fun doInitialConfig(view: View) {
        val progress_seekbar = view.findViewById(R.id.current_playback_position_seekbar) as AppCompatSeekBar
        progress_seekbar.progressDrawable = null
        progress_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Toast.makeText(view.context, progress.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        val volume_seekbar = view.findViewById(R.id.volume_seekbar) as AppCompatSeekBar
        val audioService = view.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        volume_seekbar.max = audioService.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        volume_seekbar.progress = audioService.getStreamVolume(AudioManager.STREAM_MUSIC)
        volume_seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioService.setStreamVolume(AudioManager.STREAM_MUSIC, progress, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE)
            }
        })
    }

    fun handleVolumeSlider(view: View, keyCode: Int) = when (keyCode) {
        KeyEvent.KEYCODE_VOLUME_UP -> {
            val volume_seekbar = view.findViewById(R.id.volume_seekbar) as AppCompatSeekBar
            val pos = volume_seekbar.progress + 1
            if (pos > volume_seekbar.max) volume_seekbar.progress = volume_seekbar.max
            else volume_seekbar.progress = pos
            true
        }
        KeyEvent.KEYCODE_VOLUME_DOWN -> {
            val volume_seekbar = view.findViewById(R.id.volume_seekbar) as AppCompatSeekBar
            val pos = volume_seekbar.progress - 1
            if (pos < 0) volume_seekbar.progress = 0
            else volume_seekbar.progress = pos
            true
        }
        KeyEvent.KEYCODE_VOLUME_MUTE -> {
            val volume_seekbar = view.findViewById(R.id.volume_seekbar) as AppCompatSeekBar
            volume_seekbar.progress = 0
            true
        }
        else -> false
    }


}