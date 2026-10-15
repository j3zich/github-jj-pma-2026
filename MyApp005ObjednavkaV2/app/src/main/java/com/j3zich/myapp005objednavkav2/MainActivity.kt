package com.j3zich.myapp005objednavkav2

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

		updatePrice()
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
		binding.tvTotalPrice.text = "${currentPrice()} Kč"
	}
}
