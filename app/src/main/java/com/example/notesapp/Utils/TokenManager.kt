package com.example.notesapp.Utils

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import com.example.notesapp.Utils.Constants.PREFS_TOKEN_FILE
import com.example.notesapp.Utils.Constants.USER_TOKEN
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TokenManager @Inject constructor(@ApplicationContext context: Context){

    private var prefs = context.getSharedPreferences(PREFS_TOKEN_FILE, Context.MODE_PRIVATE)

    fun SaveToken(token : String){
        var editer = prefs.edit()
        editer.putString(USER_TOKEN, token)
        editer.apply()
    }

    fun GetToken() : String? {
        return prefs.getString(USER_TOKEN, null)
    }
}