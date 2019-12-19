package br.com.renan.financask.ui.dialog

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import br.com.renan.financask.R
import br.com.renan.financask.delegate.TransacaoDelegate
import br.com.renan.financask.extensions.converteParaCalendar
import br.com.renan.financask.extensions.formataParaBrasileiro
import br.com.renan.financask.model.Tipo
import br.com.renan.financask.model.Transacao
import kotlinx.android.synthetic.main.form_transacao.view.*
import java.math.BigDecimal
import java.util.*

class AdicionaTransacaoDialog(private val context: Context,
                              private val viewGroup: ViewGroup) {

    private val viewCriada = criarLayout()
    private val campoValor = viewCriada.form_transacao_valor
    private val campoData = viewCriada.form_transacao_data
    private val campoCategoria = viewCriada.form_transacao_categoria

    fun chamaDialog(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        configuraCampoData()
        configuraCampoCategoria(tipo)

        //Para adicionar uma transação ao clicar no botão de adicionar receita
        //primeiro cria-se um AlertDialog
        configuraFormulario(tipo, transacaoDelegate)
    }

    private fun configuraFormulario(tipo: Tipo, transacaoDelegate: TransacaoDelegate) {

        val titulo = tituloPor(tipo)

        AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCriada)
            .setPositiveButton("Adicionar")
            { _, _ ->
                val valorEmTexto = campoValor.text.toString()
                val dataEmTexto = campoData.text.toString()
                val categoriaEmTexto = campoCategoria.selectedItem.toString()

                //Usando try/catch para não dar erro caso não seja passado nenhum valor para a transação,
                // logo é adicionado valor zero
                val valor = converteCampoValor(valorEmTexto) //BigDecimal(valorEmText)
                val data = dataEmTexto.converteParaCalendar()

                val transacaoCriada = Transacao(
                    tipo = tipo,
                    valor = valor,
                    data = data,
                    categoria = categoriaEmTexto
                )

                transacaoDelegate.delegate(transacaoCriada)

//                activity.atualizarTransacoes(transacaoCriada)
//                lista_transacoes_adiciona_menu.close(true)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun tituloPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.string.adiciona_receita

        return R.string.adiciona_despesa
    }

    private fun converteCampoValor(valorEmTexto: String): BigDecimal {
        return try {
            BigDecimal(valorEmTexto)
        } catch (exception: NumberFormatException) {
            Toast.makeText(context, "Falha na conversão de valores", Toast.LENGTH_LONG)
                .show()
            BigDecimal.ZERO
        }
    }

    private fun configuraCampoCategoria(tipo: Tipo) {

        val categorias = categoriasPor(tipo)

        val adapter = ArrayAdapter
            .createFromResource(
                context,
                categorias,
                android.R.layout.simple_spinner_dropdown_item
            )

        campoCategoria.adapter = adapter
    }

    private fun categoriasPor(tipo: Tipo): Int {
        if (tipo == Tipo.RECEITA)
            return R.array.categorias_de_receita

        return R.array.categorias_de_despesa
    }

    private fun configuraCampoData() {
        val hoje = Calendar.getInstance()

        var ano = hoje.get(Calendar.YEAR)
        var mes = hoje.get(Calendar.MONTH)
        var dia = hoje.get(Calendar.DAY_OF_MONTH)

        //Colocando a data atual no campo da data da transação
        campoData.setText(hoje.formataParaBrasileiro())

        //Dando a ação de clicar no campo de data e passar uma data para a transação
        campoData.setOnClickListener {
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val dataSelecionada = Calendar.getInstance()
                    dataSelecionada.set(year, month, dayOfMonth)
                    campoData.setText(dataSelecionada.formataParaBrasileiro())
                }, ano, mes, dia
            )
                .show()
        }
    }

    private fun criarLayout(): View {
        return LayoutInflater
            .from(context)
            .inflate(R.layout.form_transacao, viewGroup, false)
    }
}