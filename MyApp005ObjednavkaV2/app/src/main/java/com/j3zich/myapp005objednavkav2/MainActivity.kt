package com.j3zich.myapp005objednavkav2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.j3zich.myapp005objednavkav2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		updateImage()
		updatePrice()

		binding.rgParameters.setOnCheckedChangeListener { _, _ ->
			updateImage()
			updatePrice()
		}
		binding.chb1.setOnCheckedChangeListener { _, _ -> updatePrice() }
		binding.chb2.setOnCheckedChangeListener { _, _ -> updatePrice() }
		binding.chb3.setOnCheckedChangeListener { _, _ -> updatePrice() }

		binding.btnOrder.setOnClickListener {
			val tvName = when (binding.rgParameters.checkedRadioButtonId) {
				binding.rb1.id -> binding.rb1.text.toString()
				binding.rb2.id -> binding.rb2.text.toString()
				binding.rb3.id -> binding.rb3.text.toString()
				else -> getString(R.string.unknown_tv)
			}

			val intent = Intent(this, SummaryActivity::class.java)
			intent.putExtra("EXTRA_TV_NAME", tvName)
			intent.putExtra("EXTRA_TOTAL_PRICE", currentPrice())
			intent.putExtra("EXTRA_HAS_SPEAKERS", binding.chb1.isChecked)
			intent.putExtra("EXTRA_HAS_PANEL", binding.chb2.isChecked)
			intent.putExtra("EXTRA_HAS_STAND", binding.chb3.isChecked)
			startActivity(intent)
		}
	}

	private fun updateImage() {
		val drawableRes = when (binding.rgParameters.checkedRadioButtonId) {
			binding.rb1.id -> R.drawable.tv_43
			binding.rb2.id -> R.drawable.tv_55
			binding.rb3.id -> R.drawable.tv_65
			else -> R.drawable.tv_43
		}
		binding.imageView.setImageResource(drawableRes)
	}

	private fun currentPrice(): Int {
		var totalPrice = when (binding.rgParameters.checkedRadioButtonId) {
			binding.rb1.id -> 12000
			binding.rb2.id -> 18000
			binding.rb3.id -> 25000
			else -> 12000
		}

		if (binding.chb1.isChecked) totalPrice += 2000
		if (binding.chb2.isChecked) totalPrice += 3000
		if (binding.chb3.isChecked) totalPrice += 1000

		return totalPrice
	}

	private fun updatePrice() {
		binding.tvTotalPrice.text = getString(R.string.price_format, currentPrice())
	}
}
