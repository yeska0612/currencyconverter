package com.example.currencyconverter

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.currencyconverter.databinding.ActivityConverterBinding

class ConverterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConverterBinding
    private lateinit var currencyList: List<Currency>
    private lateinit var selectedCurrency: Currency

    private var isFromMntToForeign = false
    private val mntCode = "MNT"
    private val mntFlagResId = R.drawable.flag_mnt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConverterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        currencyList = listOf(
            Currency("Евро", "EUR", 4032.0, R.drawable.flag_eur),
            Currency("Английн фунт", "GBP", 4715.0, R.drawable.flag_gbp),
            Currency("Оросын рубль", "RUB", 44.8, R.drawable.flag_rub),
            Currency("Хятадын юань", "CNY", 515.0, R.drawable.flag_cny),
            Currency("Японы иен", "JPY", 27.1, R.drawable.flag_jpy),
            Currency("БНСУ-ын вон", "KRW", 2.8, R.drawable.flag_krw),
            Currency("Австрали доллар", "AUD", 2620.0, R.drawable.flag_aud),
            Currency("Швейцарь франк", "CHF", 4210.0, R.drawable.flag_chf),
            Currency("Канад доллар", "CAD", 2740.0, R.drawable.flag_cad),
            Currency("Сингапур доллар", "SGD", 2980.0, R.drawable.flag_sgd),
            Currency("Швед крон", "SEK", 355.0, R.drawable.flag_sek),
            Currency("Туркийн Лир", "TRY", 116.0, R.drawable.flag_try),
            Currency("Гонконг доллар", "HKD", 470.0, R.drawable.flag_hkd),
            Currency("Казахстан тенге", "KZT", 7.56, R.drawable.flag_kzt)
        )

        val passedCode = intent.getStringExtra("currency_code") ?: "EUR"

        val spinnerItems = currencyList.map { it.code }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            spinnerItems
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCurrency.adapter = adapter

        val initialIndex = currencyList.indexOfFirst { it.code == passedCode }.let {
            if (it == -1) 0 else it
        }

        binding.spinnerCurrency.setSelection(initialIndex)
        selectedCurrency = currencyList[initialIndex]
        binding.etAmount.setText("1")
        updateUI()

        binding.spinnerCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
                selectedCurrency = currencyList[position]
                updateUI()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.btnSwap.setOnClickListener {
            isFromMntToForeign = !isFromMntToForeign
            updateUI()
            convertCurrency()
        }

        binding.btnConvert.setOnClickListener {
            convertCurrency()
        }
    }

    private fun updateUI() {
        if (!isFromMntToForeign) {
            binding.imgFlagTop.setImageResource(selectedCurrency.flagResId)
            binding.tvTopCode.text = selectedCurrency.code

            binding.imgFlagBottom.setImageResource(mntFlagResId)
            binding.tvBottomCode.text = mntCode

            binding.tvAmountHint.text = "${selectedCurrency.name} хэмжээ"
        } else {
            binding.imgFlagTop.setImageResource(mntFlagResId)
            binding.tvTopCode.text = mntCode

            binding.imgFlagBottom.setImageResource(selectedCurrency.flagResId)
            binding.tvBottomCode.text = selectedCurrency.code

            binding.tvAmountHint.text = "Төгрөгийн хэмжээ"
        }
    }

    private fun convertCurrency() {
        val amountText = binding.etAmount.text.toString().trim()

        if (amountText.isEmpty()) {
            binding.etAmount.error = "Дүн оруулна уу"
            return
        }

        val amount = amountText.toDoubleOrNull()
        if (amount == null) {
            binding.etAmount.error = "Зөв тоо оруулна уу"
            return
        }

        val result = if (!isFromMntToForeign) {
            amount * selectedCurrency.rate
        } else {
            amount / selectedCurrency.rate
        }

        binding.tvResult.text = String.format("%,.2f", result)
    }
}