package com.example.structdestruct.currencies

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import java.lang.NumberFormatException

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val editDaysNumberPreference: EditTextPreference? = findPreference("days_number")
        editDaysNumberPreference?.onPreferenceChangeListener =
            object : Preference.OnPreferenceChangeListener {
                override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
                    try {
                        Log.i("SettingsFragment", "try to change days_number!")
                        val daysNumber = (newValue as String).toInt()
                        if (daysNumber <= 0) {
                            Toast.makeText(context, "days number should be more than 0", Toast.LENGTH_SHORT).show()
                            return false
                        }
                        return true
                    } catch (nfe: NumberFormatException) {
                        Toast.makeText(context, "invalid number, enter integer!", Toast.LENGTH_SHORT).show()
                        return false
                    }
                }
            }

    }
}