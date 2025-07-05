package com.thecode.vietglobe.presentation.newsdetails

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.thecode.vietglobe.R
import com.thecode.vietglobe.databinding.ActivityNewsDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsDetailsBinding
    private val viewModel: NewsDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // RECEIVE OUR DATA
        val i = intent
        val title = i.extras?.getString("title")
        val description = i.extras?.getString("description")
        val source = i.extras?.getString("source")
        val imageUrl = i.extras?.getString("imageUrl")
        val content = i.extras?.getString("content")
        val date = i.extras?.getString("date")
        val formattedDate = date?.split("T")?.get(0)

        setupViews(title, description, source, imageUrl, content, formattedDate)
    }

    private fun setupViews(
        title: String?,
        description: String?,
        source: String?,
        imageUrl: String?,
        content: String?,
        formattedDate: String?
    ) {
        binding.apply {
            Glide.with(this@NewsDetailsActivity).load(imageUrl)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .apply(RequestOptions().centerCrop())
                .into(imageNews)
                
            textSource.text = source
            textContent.text = "$description\n\n$content"
            textDate.text = formattedDate
        setTitle(title)
        }
    }
}
