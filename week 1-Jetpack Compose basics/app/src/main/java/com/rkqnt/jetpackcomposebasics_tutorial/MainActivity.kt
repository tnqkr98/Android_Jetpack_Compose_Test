package com.rkqnt.jetpackcomposebasics_tutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rkqnt.jetpackcomposebasics_tutorial.ui.theme.JetpackComposeBasics_tnqkr98Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeBasics_tnqkr98Theme {
                // A surface container using the 'background' color from the theme
            //    Surface(color = MaterialTheme.colors.background) {
                Conversation(messages = SampleData.conversationSample)
                    //Greeting("Android")
           //     }
            }
        }
    }
}

data class Message(val author : String, var body : String)

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable                 // Composable Function
fun PreviewMessageCard(){
    JetpackComposeBasics_tnqkr98Theme {
        MessageCard(Message("Android", "JetPack Compose"))
    }
}

// Modifier ??? ?????? XML ?????? ??????

@Composable
fun MessageCard(msg : Message){
    // Layout Composable : Row(Linear-Hori), Column(Linear-Verti), Box (FrameLayout)
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription ="Contact profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        // isExpanded ??? boolean ????????? remember??? ?????? ????????? ???????????? ??????. ?????? false ??????
        // ????????? mutableStateOf??? ????????? ??????????????? ??????
        var isExpanded by remember { mutableStateOf(false)}

        // (???????????????)???????????? ?????? ??????, (false) primary -> surface, (true) ??????
        val surfaceColor: Color by animateColorAsState(
            if(isExpanded) MaterialTheme.colors.primary else MaterialTheme.colors.surface
        )
        
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded}) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondaryVariant,
                style = MaterialTheme.typography.subtitle2
            )
            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                elevation = 1.dp,
                color = surfaceColor,   // ?????? ???????????????
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,   // isExpanded ??? false?????? ????????? ?????????
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@Composable
fun Conversation(messages: List<Message>){
    // List ????????? ??????????????? Composable
    LazyColumn{
        items(messages){ message ->
            MessageCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation(){
    JetpackComposeBasics_tnqkr98Theme {
        Conversation(messages = SampleData.conversationSample)
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackComposeBasics_tnqkr98Theme {
        //Greeting("Android")
    }
}