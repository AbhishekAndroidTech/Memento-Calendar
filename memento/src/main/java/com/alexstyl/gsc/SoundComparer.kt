package com.alexstyl.gsc

class SoundComparer {
    companion object {
        private val soundRules = SoundRules.instance

        fun areSame(first: String, second: String) = soundRules.compare(first, second, false)

        fun startsWith(first: String, second: String) = soundRules.compare(first, second, true)
    }
}
