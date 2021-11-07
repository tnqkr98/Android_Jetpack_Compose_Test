package com.rkqnt.jetpackcomposebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rkqnt.jetpackcomposebasics.ui.theme.JetpackComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBasicsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    // 상호작용에 의해 UI의 변경이 있을때, Recomposition을 수행하는데, remember 객체는 이전 상태를 기억하여
    // Recomposition 수행시 값이 초기화되어버리지 않도록 한다.
    // 즉, 이 Composable 함수는 expanded 라는 상태를 구독한 것.
    var expanded = remember { mutableStateOf(false) }
    var extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(     // Surface는 컨테이너 레이아웃의 느낌이 아니라, 모든 컴포넌트에 한번에 설정할 Configuration의 느낌
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            ) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expanded.value = !expanded.value }
            ) {
                Text(if(expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> =List(1000){"$it"}){
    // Lazycolumn 은 RecyclerView 와 기능은 동일하나 내부 구조는 다름
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items=names){ name->
            Greeting(name = name)
        }
    }
}

@Composable
fun MyApp(){
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    // 온보딩 페이지를 만드는 용도(액티비티 없이)
    if(shouldShowOnboarding){
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    }else{
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit){
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,       // 열 컴포넌트들이 중앙에 위치
            horizontalAlignment = Alignment.CenterHorizontally      // 수평기준 가운데 정렬
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ){
                Text("Continue")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicsTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}


@Preview(showBackground = true, name = "Text Preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    JetpackComposeBasicsTheme {
        Greetings()
    }
}

