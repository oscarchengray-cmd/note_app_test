package edu.saihs.skills08.noteapptest

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

data class Notedata(
    val id : Int,
    val title: String,
    val body: String
)

class ModelView(application: Application): AndroidViewModel(application){
    var page by mutableStateOf("write")
    fun read(): List<Notedata>{
        var list: List<Notedata> by mutableStateOf(emptyList())
        viewModelScope.launch {
            list=sql(application).read()
        }
        return list
    }
    fun write(text: Notedata){
        viewModelScope.launch {
            sql(application).write(text)
        }
    }
}