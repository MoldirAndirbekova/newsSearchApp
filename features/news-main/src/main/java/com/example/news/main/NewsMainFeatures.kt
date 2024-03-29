package com.example.news.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NewsMainUI() {
    NewsMainUI(viewModel = viewModel())
}

@Composable
internal fun NewsMainUI(viewModel: NewsMainViewModel) {
    val state by viewModel.state.collectAsState()
    when(val currentState = state) {
        is State.Success -> Articles(currentState.articles)
        is State.Error -> TODO()
        is State.Loading -> TODO()
        is State.None -> TODO()

    }
}

@Composable
private fun Articles(articles: List<ArticleUI>) {
    LazyColumn {
        items(articles) { article ->
            key(article.id) {
                Article(article)
            }
        }
    }

}

@Preview
@Composable
internal fun Article(@PreviewParameter(ArticlePreviewProvider::class) article: ArticleUI) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = article.title, style = MaterialTheme.typography.headlineMedium, maxLines = 1)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium, maxLines = 3)

    }
}

private class ArticlePreviewProvider() : PreviewParameterProvider<ArticleUI> {
    override val values = sequenceOf(
        ArticleUI(
            1,
            "Android Studio news",
            "Android Studio Upgraded",
            imageUrl = null,
            url = ""
        ),
        ArticleUI(
            2,
            "Gemini",
            "Gemini Upgraded",
            imageUrl = null,
            url = ""
        ),
        ArticleUI(
            3,
            "Shape animations",
            "Animations Upgraded",
            imageUrl = null,
            url = ""
        ),
    )


}
