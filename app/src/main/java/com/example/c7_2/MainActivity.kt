package com.example.c7_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.c7_2.ui.theme.C7_2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            C7_2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RepoScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoScreen(modifier: Modifier = Modifier,viewModel: GitViewModel = viewModel()) {
    var gitName by remember { mutableStateOf("") }
    val gitState by viewModel.gitState.collectAsState()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = gitName,
            onValueChange = { gitName = it },
            label = { Text(text = "Enter Username", fontSize = 20.sp) },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        Button(
            onClick = {
                if (gitName.isNotEmpty()) {
                    viewModel.fetchGit(gitName)
                }
            },
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Text("Find Repo", fontSize = 20.sp)
        }
        when(gitState){
            GitViewModel.GitState.Initial -> {}
            GitViewModel.GitState.Loading -> {
                CircularProgressIndicator()
            }
            is GitViewModel.GitState.Success -> {
                val gitResponse = (gitState as GitViewModel.GitState.Success).gitResponse

                if(gitResponse.isEmpty()){
                    Text("NO REPOS FOUND", fontSize = 20.sp)

                }else{
                    Button(
                        onClick = {
                            if (gitName.isNotEmpty()) {
                                viewModel.loadMoreRepos(gitName)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Text("Load More Repos", fontSize = 20.sp)
                    }
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(gitResponse) { git ->
                            GitItemView(git)
                        }
                    }
                }



            }
            is GitViewModel.GitState.Error -> {
                val errorMessage = (gitState as GitViewModel.GitState.Error).errorMessage
                gitName = ""
                Text("Error: $errorMessage", fontSize = 20.sp)

                viewModel.resetState()

            }
        }
    }
}

@Composable
fun GitItemView(item : GitResponse){
    Column(modifier = Modifier.padding(8.dp)) {

        Text(text = item.name, fontSize = 20.sp)
        Text(text = item.url, fontSize = 14.sp)
    }
}