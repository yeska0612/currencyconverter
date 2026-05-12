package com.example.currencyconverter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemEur.setOnClickListener {
            openConverter("Евро", "EUR", 4134.0, R.drawable.flag_eur)
        }

        binding.itemGbp.setOnClickListener {
            openConverter("Английн фунт", "GBP", 4735.0, R.drawable.flag_gbp)
        }

        binding.itemRub.setOnClickListener {
            openConverter("Оросын рубль", "RUB", 44.0, R.drawable.flag_rub)
        }

        binding.itemCny.setOnClickListener {
            openConverter("Хятадын юань", "CNY", 518.0, R.drawable.flag_cny)
        }

        binding.itemJpy.setOnClickListener {
            openConverter("Японы иен", "JPY", 22.0, R.drawable.flag_jpy)
        }

        binding.itemKrw.setOnClickListener {
            openConverter("БНСУ-ын вон", "KRW", 2.3, R.drawable.flag_krw)
        }

        binding.itemAud.setOnClickListener {
            openConverter("Австрали доллар", "AUD", 2473.0, R.drawable.flag_aud)
        }

        binding.itemChf.setOnClickListener {
            openConverter("Швейцарь франк", "CHF", 4493.0, R.drawable.flag_chf)
        }

        binding.itemCad.setOnClickListener {
            openConverter("Канад доллар", "CAD", 2566.0, R.drawable.flag_cad)
        }

        binding.itemSgd.setOnClickListener {
            openConverter("Сингапур доллар", "SGD", 2779.0, R.drawable.flag_sgd)
        }

        binding.itemSek.setOnClickListener {
            openConverter("Швед крон", "SEK", 379.0, R.drawable.flag_sek)
        }

        binding.itemTry.setOnClickListener {
            openConverter("Туркийн Лир", "TRY", 80.0, R.drawable.flag_try)
        }

        binding.itemHkd.setOnClickListener {
            openConverter("Гонконг доллар", "HKD", 455.0, R.drawable.flag_hkd)
        }

        binding.itemHkd.setOnClickListener {
            openConverter("Казахстан тенге", "KZT", 7.56, R.drawable.flag_kzt)
        }
    }

    private fun openConverter(name: String, code: String, rate: Double, flagResId: Int) {
        val intent = Intent(this, ConverterActivity::class.java)
        intent.putExtra("currency_name", name)
        intent.putExtra("currency_code", code)
        intent.putExtra("currency_rate", rate)
        intent.putExtra("currency_flag", flagResId)
        startActivity(intent)
    }
}