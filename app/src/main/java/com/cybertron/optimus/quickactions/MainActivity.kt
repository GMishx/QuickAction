package com.cybertron.optimus.quickactions

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var upShortPress = false
    private var upLongPress = false
    private var dwnShortPress = false
    private var dwnLongPress = false

    override fun onKeyLongPress(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            Toast.makeText(this, "Next song", Toast.LENGTH_SHORT).show()
            mediaAction(KeyEvent.KEYCODE_MEDIA_NEXT)
            upShortPress = false
            upLongPress = true
            return true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            Toast.makeText(this, "Previous song", Toast.LENGTH_SHORT).show()
            mediaAction(KeyEvent.KEYCODE_MEDIA_PREVIOUS)
            dwnShortPress = false
            dwnLongPress = true
            return true
        }
        return super.onKeyLongPress(keyCode, event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?):Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            event?.startTracking()
            if (upLongPress) {
                upShortPress = false
            } else {
                upShortPress = true
                upLongPress = false
            }
            return true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event?.startTracking()
            if (dwnLongPress) {
                dwnShortPress = false
            } else {
                dwnShortPress = true
                dwnLongPress = false
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?):Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            event?.startTracking()
            if (upShortPress) {
                Toast.makeText(this, "Volume up", Toast.LENGTH_SHORT).show()
                mediaAction(KeyEvent.KEYCODE_VOLUME_UP)
            }
            upShortPress = true
            upLongPress = false
            return true
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            event?.startTracking()
            if (dwnShortPress) {
                Toast.makeText(this, "Volume down", Toast.LENGTH_SHORT).show()
                mediaAction(KeyEvent.KEYCODE_VOLUME_DOWN)
            }
            dwnShortPress = true
            dwnLongPress = false
            return true
        }

        return super.onKeyUp(keyCode, event)
    }

    fun mediaAction(key: Int) {
        val eventtime = SystemClock.uptimeMillis()

        val downIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null)
        val downEvent = KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_DOWN, key, 0)
        downIntent.putExtra(Intent.EXTRA_KEY_EVENT, downEvent)
        sendOrderedBroadcast(downIntent, null)

        val upIntent = Intent(Intent.ACTION_MEDIA_BUTTON, null)
        val upEvent = KeyEvent(eventtime, eventtime,
                KeyEvent.ACTION_UP, key, 0)
        upIntent.putExtra(Intent.EXTRA_KEY_EVENT, upEvent)
        sendOrderedBroadcast(upIntent, null)
    }
}
