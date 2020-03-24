package ru.adfmp.officegym.utils

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import java.util.*


class Audio(context: Context, private val onInit: (Audio) -> Unit = {}) :
    TextToSpeech.OnInitListener, UtteranceProgressListener() {

    private val textToSpeech = TextToSpeech(context, this)
    private val callbacks = hashMapOf<String, () -> Unit>()
    private var enabled = false

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val locale = Locale.ENGLISH
            if (textToSpeech.isLanguageAvailable(locale) == TextToSpeech.LANG_AVAILABLE) {
                textToSpeech.language = locale
                enabled = true
            }
            textToSpeech.setPitch(PITCH_RATE)
            textToSpeech.setSpeechRate(SPEECH_RATE)
            textToSpeech.setOnUtteranceProgressListener(this)
        }
        onInit(this)
    }

    fun speak(text: String, onFinish: () -> Unit = {}) {
        if (!enabled) {
            onFinish()
            return
        }
        val utteranceId = text.hashCode().toString()
        callbacks[utteranceId] = onFinish
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } else {
            val parameters = hashMapOf(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID to utteranceId)
            @Suppress("DEPRECATION")
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, parameters)
        }
    }

    fun shutdown() {
        textToSpeech.shutdown()
        for (callback in callbacks.values) {
            callback()
        }
        callbacks.clear()
    }

    companion object {
        private const val PITCH_RATE = 1.3f
        private const val SPEECH_RATE = 0.8f
    }

    private fun onFinish(utteranceId: String?) {
        callbacks[utteranceId]?.invoke()
        callbacks.remove(utteranceId)
    }

    override fun onDone(utteranceId: String?) = onFinish(utteranceId)
    override fun onError(utteranceId: String?) = onFinish(utteranceId)
    override fun onStart(utteranceId: String?) {}
}
