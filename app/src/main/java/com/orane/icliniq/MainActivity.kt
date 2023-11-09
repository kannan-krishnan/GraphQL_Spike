package com.orane.icliniq

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.apollographql.apollo3.exception.ApolloException
import com.orane.icliniq.ui.theme.GraphQLSpikeTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphQLSpikeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen(AuthViewModel(AuthRepository()))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    val loginResult by remember { mutableStateOf<LoginMutation.Data?>(null) }
    var error by remember { mutableStateOf<ApolloException?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val token = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            label = { Text("Username") }
        )
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        token.value = authViewModel.loginWithToken(username.value)
                        Toast.makeText(context, if(token.value) "Login with token success" else "Login with token failed" , Toast.LENGTH_SHORT).show()
                    } catch (e: ApolloException) {
                        error = e
                    }
                }

                      },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Login")
        }
        loginResult?.let { data ->
            Log.d("data", "LoginScreen: $data")
            Toast.makeText(context,"success", Toast.LENGTH_SHORT).show()
        }
        error?.let { e ->
            Toast.makeText(context,"${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("error", "LoginScreen: ${e.message}" )
        }

        if (token.value){

            Text(text ="New token is :${authViewModel._sampleLiveData.value}")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GraphQLSpikeTheme {
        LoginScreen(AuthViewModel(AuthRepository()))
    }
}