package br.com.renan.financask.ui.adapter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import br.com.renan.financask.R
import br.com.renan.financask.extensions.formataParaBrasileiro
import br.com.renan.financask.extensions.limitaEmAte
import br.com.renan.financask.model.Tipo
import br.com.renan.financask.model.Transacao
import kotlinx.android.synthetic.main.transacao_item.view.*

class ListaTransacoesAdapter(private val transacoes: List<Transacao>, private val context: Context) : BaseAdapter() {

    private val limiteDaCategoria = 14

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater.from(context).inflate(R.layout.transacao_item, parent, false)

        val transacao = transacoes[position]

        adicionaValor(transacao, viewCriada)
        adicionaIcone(transacao, viewCriada)
        adicionaCategoria(viewCriada, transacao)
        adicionaData(viewCriada, transacao)

        return viewCriada
    }

    private fun adicionaData(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_data.text = transacao.data.formataParaBrasileiro()
    }

    private fun adicionaCategoria(viewCriada: View, transacao: Transacao) {
        viewCriada.transacao_categoria.text = transacao.categoria.limitaEmAte(limiteDaCategoria)
    }

    private fun adicionaIcone(transacao: Transacao, viewCriada: View) {
        val icone: Int = iconePor(transacao.tipo)
        viewCriada.transacao_icone.setBackgroundResource(icone)
    }

    private fun iconePor(tipo: Tipo): Int {

        return when (tipo) {
            Tipo.RECEITA -> R.drawable.icone_transacao_item_receita
            else -> return R.drawable.icone_transacao_item_despesa
        }

//        if (tipo == Tipo.RECEITA)
//            return R.drawable.icone_transacao_item_receita
//
//        return R.drawable.icone_transacao_item_despesa
    }

    private fun adicionaValor(transacao: Transacao, viewCriada: View) {
        var cor: Int = corPor(transacao.tipo)

        with(viewCriada.transacao_valor) {
            setTextColor(cor)
            text = transacao.valor.formataParaBrasileiro()
        }
    }

    private fun corPor(tipo: Tipo): Int {

        return when (tipo) {
            Tipo.RECEITA -> ContextCompat.getColor(context, R.color.receita)
            else -> ContextCompat.getColor(context, R.color.despesa)
        }

//        if (tipo == Tipo.RECEITA)
//            return ContextCompat.getColor(context, R.color.receita)
//
//        return ContextCompat.getColor(context, R.color.despesa)
    }

    override fun getItem(position: Int): Transacao {
        return transacoes[position]
    }

    override fun getItemId(position: Int): Long {
        return 0;
    }

    override fun getCount(): Int {
        return transacoes.size
    }
}
