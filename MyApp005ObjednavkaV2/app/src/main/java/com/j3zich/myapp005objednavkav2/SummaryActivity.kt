package com.j3zich.myapp005objednavkav2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.j3zich.myapp005objednavkav2.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

	private lateinit var binding: ActivitySummaryBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivitySummaryBinding.inflate(layoutInflater)
		setContentView(binding.root)

		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		val tvName = intent.getStringExtra("EXTRA_TV_NAME") ?: getString(R.string.unknown_tv)
		val totalPrice = intent.getIntExtra("EXTRA_TOTAL_PRICE", 0)

		val hasSpeakers = intent.getBooleanExtra("EXTRA_HAS_SPEAKERS", false)
		val hasPanel = intent.getBooleanExtra("EXTRA_HAS_PANEL", false)
		val hasStand = intent.getBooleanExtra("EXTRA_HAS_STAND", false)

		var extrasText = getString(R.string.summary_extras) + "\n"
		var hasAnyExtra = false

		if (hasSpeakers) {
			extrasText += "- " + getString(R.string.check_box_speakers) + "\n"
			hasAnyExtra = true
		}

		if (hasPanel) {
			extrasText += "- " + getString(R.string.check_box_panel) + "\n"
			hasAnyExtra = true
		}

		if (hasStand) {
			extrasText += "- " + getString(R.string.check_box_stand) + "\n"
			hasAnyExtra = true
		}

		if (!hasAnyExtra) {
			extrasText += "- " + getString(R.string.summary_extras_none)
		}

		binding.tvSelectedTv.text = getString(R.string.summary_selected_tv, tvName)
		binding.tvExtras.text = extrasText
		binding.tvFinalPrice.text = getString(R.string.summary_final_price, totalPrice)

		binding.btnBack.setOnClickListener {
			finish()
		}
	}
}
