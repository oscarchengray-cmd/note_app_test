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
    val id: Int,
    val title: String,
    val body: String,
    val time: String,
    val color: Int
)

class ModelView(application: Application) : AndroidViewModel(application) {
    var list: List<Notedata> by mutableStateOf(emptyList())

    var page by mutableStateOf("write")
    fun read(){
        viewModelScope.launch {
            list = sql(application).read()
        }
    }

    fun write(text: Notedata) {
        viewModelScope.launch {
            sql(application).write(text)
        }
        read()
    }

    fun upgrade(text: Notedata) {
        viewModelScope.launch {
            sql(application).upgrade(text)
        }
        read()
    }

    fun delete(deleteid: Int) {
        viewModelScope.launch {
            sql(application).delete(deleteid)
        }
        read()
    }
}