package com.j3zich.myapp004objednavka

import android.graphics.Rect
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.j3zich.myapp004objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//enableEdgeToEdge()

		// Binding inflates layout to binding instance
		binding = ActivityMainBinding.inflate(layoutInflater)

		// Set root view to activity view
		setContentView(binding.root)

		//setContentView(R.layout.activity_main)

		// Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
		val basePadding = Rect(binding.main.paddingLeft, binding.main.paddingTop, binding.main.paddingRight, binding.main.paddingBottom)
		ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(
				basePadding.left + systemBars.left,
				basePadding.top + systemBars.top,
				basePadding.right + systemBars.right,
				basePadding.bottom + systemBars.bottom
			)
			insets
		}

		binding.btnOrder.setOnClickListener {
			val tv = when (binding.rgTv.checkedRadioButtonId) {
				binding.rb65.id -> binding.rb65
				binding.rb55.id -> binding.rb55
				binding.rb43.id -> binding.rb43

				else -> binding.rb65
			}

			val panel = binding.cbPanel.isChecked
			val stand = binding.cbStand.isChecked
			val speakers = binding.cbSpeakers.isChecked

			val orderText = getString(R.string.textview_order_summary_text, tv.text) +
					(if (panel) ", " + binding.cbPanel.text else "") +
					(if (stand) ", " + binding.cbStand.text else "") +
					(if (speakers) ", " + binding.cbSpeakers.text else "")

			binding.tvOrder.text = orderText

			binding.rb65.setOnClickListener {
				binding.ivTV.setImageResource(R.drawable.tv_65)
			}
			binding.rb55.setOnClickListener {
				binding.ivTV.setImageResource(R.drawable.tv_55)
			}
			binding.rb43.setOnClickListener {
				binding.ivTV.setImageResource(R.drawable.tv_43)

			}
		}

		updatePrice()
		binding.rgTv.setOnCheckedChangeListener { _, _ -> updatePrice() }
		binding.cbPanel.setOnCheckedChangeListener { _, _ -> updatePrice() }
		binding.cbStand.setOnCheckedChangeListener { _, _ -> updatePrice() }
		binding.cbSpeakers.setOnCheckedChangeListener { _, _ -> updatePrice() }
	}

	private fun updatePrice() {
		val basePrice = when (binding.rgTv.checkedRadioButtonId) {
			binding.rb65.id -> 25000
			binding.rb55.id -> 18000
			binding.rb43.id -> 12000
			else -> 25000
		}

		val totalPriceCzk = basePrice +
				(if (binding.cbPanel.isChecked) 3000 else 0) +
				(if (binding.cbStand.isChecked) 1000 else 0) +
				(if (binding.cbSpeakers.isChecked) 2000 else 0)

		// Fixed rates for dev purposes
		val exchangeRate = when (resources.configuration.locales[0].language) {
			"en" -> 0.043
			"de" -> 0.040
			else -> 1.0
		}

		val totalPrice = (totalPriceCzk * exchangeRate).toInt()

		binding.tvPrice.text = getString(R.string.price_format, totalPrice)
	}
}
