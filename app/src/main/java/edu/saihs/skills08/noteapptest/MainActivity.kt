package edu.saihs.skills08.noteapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import edu.saihs.skills08.noteapptest.ui.theme.NoteAppTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTestTheme {
                val viewmodel: ModelView by viewModels()
                mainpage(viewmodel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainpage(viewModel: ModelView) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.White),
                title = { Text(viewModel.page) })
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = viewModel.page == "write",
                    onClick = { viewModel.page = "write" },
                    icon = { Text("寫") },
                    label = { Text("write") }
                )
                NavigationBarItem(
                    selected = viewModel.page == "read",
                    onClick = { viewModel.page = "read" },
                    icon = { Text("讀") },
                    label = { Text("read") }
                )
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            when (viewModel.page) {
                "write" -> write(viewModel)
                "read" -> read(viewModel)
            }
        }


    }
}

@Composable
fun read(viewModel: ModelView) {
    viewModel.read()
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            itemsIndexed(viewModel.list) {index,it ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(
                            color = when (it.color) {
                                1 -> Color.Red
                                2 -> Color.Yellow
                                3 -> Color.Blue
                                4 -> Color.Green
                                else -> {
                                    Color.Transparent
                                }
                            }
                        )
                        .combinedClickable(onClick = {}, onDoubleClick = {viewModel.delete(it.id)})) {
                    Column() {
                        Text(it.title, fontSize = 25.sp)
                        Text(it.body, fontSize = 20.sp)
                        Text(it.time, fontSize = 20.sp)
                        Text(it.id.toString(), fontSize = 20.sp)
                    }

                }

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun write(viewModel: ModelView) {
    var text1 by remember { mutableStateOf("") }
    var text2 by remember { mutableStateOf("") }
    var choosecolor by remember { mutableStateOf(0) }

    var show by remember { mutableStateOf(false) }
    var timePickerState = rememberTimePickerState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = text1,
            onValueChange = { text1 = it },
        )

        OutlinedTextField(
            value = text2,
            onValueChange = { text2 = it },
        )
        OutlinedButton(onClick = {
            show = !show
        }) { Text("${timePickerState.hour} : ${timePickerState.minute}") }
        Row() {
            for (i in 1..4) {
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .background(
                            color = when (i) {
                                1 -> Color.Red
                                2 -> Color.Yellow
                                3 -> Color.Blue
                                4 -> Color.Green
                                else -> {
                                    Color.Transparent
                                }
                            }, shape = RoundedCornerShape(100.dp)
                        )
                        .clickable {
                            choosecolor = i
                        }
                ) {
                    if (i == choosecolor) {
                        Icon(painterResource(R.drawable.baseline_check_24), null)
                    }
                }
            }
        }
        OutlinedButton(onClick = {
            viewModel.write(
                Notedata(
                    id = 1,
                    title = text1,
                    body = text2,
                    time = "${timePickerState.hour}:${timePickerState.minute}",
                    color = choosecolor
                )
            )
        }) {
            Text("in")
        }
        if (show) {
            AlertDialog(
                onDismissRequest = { show = !show },
                content = {
                    Card() {
                        TimePicker(timePickerState, modifier = Modifier.padding(10.dp))
                    }
                }
            )
        }
    }


}