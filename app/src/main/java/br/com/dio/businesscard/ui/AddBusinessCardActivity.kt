package br.com.dio.businesscard.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.dio.businesscard.App
import br.com.dio.businesscard.R
import br.com.dio.businesscard.data.BusinessCard
import br.com.dio.businesscard.databinding.ActivityAddBusinessCardBinding
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener

class AddBusinessCardActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddBusinessCardBinding.inflate(layoutInflater) }

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        insertListeners()
        initComponents()
    }

    private fun insertListeners() {
        binding.btnConfirm.setOnClickListener {
            val businessCard = BusinessCard(
                nome = binding.tilNome.text.toString(),
                empresa = binding.tilEmpresa.text.toString(),
                telefone = binding.tilTelefone.text.toString(),
                email = binding.tilEmail.text.toString(),
                fundoPersonalizado = binding.tilCor.text.toString()
            )

            if(businessCard.nome == "") {
                Toast.makeText(this, R.string.toast_fata_nome, Toast.LENGTH_SHORT).show()
            } else  if(businessCard.telefone == "") {
                Toast.makeText(this, R.string.label_falta_telefone, Toast.LENGTH_SHORT).show()
            } else  if(businessCard.email == "") {
                Toast.makeText(this, R.string.label_falta_email, Toast.LENGTH_SHORT).show()
            } else  if(businessCard.empresa == "") {
                Toast.makeText(this, R.string.label_falta_empresa, Toast.LENGTH_SHORT).show()
            } else  if(businessCard.fundoPersonalizado == "") {
                Toast.makeText(this, R.string.label_falta_corCartao, Toast.LENGTH_SHORT).show()
            } else if(!businessCard.fundoPersonalizado.contains("#")) {
                Toast.makeText(this, R.string.label_corHexadecimalInvalida, Toast.LENGTH_SHORT).show()
            } else if(businessCard.fundoPersonalizado.length < 7 || businessCard.fundoPersonalizado.length > 7) {
                Toast.makeText(this, R.string.label_corHexadecimalInvalida, Toast.LENGTH_SHORT).show()
            } else if(businessCard.telefone.toString().length < 15) {
                Toast.makeText(this, R.string.label_telefoneInvalido, Toast.LENGTH_SHORT).show()
            }
            else {
                mainViewModel.insert(businessCard)
                Toast.makeText(this, R.string.label_show_success, Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        binding.btnClose.setOnClickListener {
            finish()
        }
    }

    private fun initComponents() {
        val maskTEL = MaskEditTextChangedListener("(##) #####-####", binding.tilTelefone)
        binding.tilTelefone.addTextChangedListener(maskTEL)
    }
}
