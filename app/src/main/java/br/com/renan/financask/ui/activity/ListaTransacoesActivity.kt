package br.com.renan.financask.ui.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import br.com.renan.financask.R
import br.com.renan.financask.delegate.TransacaoDelegate
import br.com.renan.financask.model.Tipo
import br.com.renan.financask.model.Transacao
import br.com.renan.financask.ui.ResumoView
import br.com.renan.financask.ui.adapter.ListaTransacoesAdapter
import br.com.renan.financask.ui.dialog.AdicionaTransacaoDialog
import kotlinx.android.synthetic.main.activity_lista_transacoes.*

class ListaTransacoesActivity() : Activity() {

    private val transacoes: MutableList<Transacao> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_transacoes)

        configuraResumo()

        configuraLista()

        //Responsável por chamar as Float Action Button (Botão de adicionar receita/despesa)
        configuraFAB()
    }

    private fun configuraFAB() {
        lista_transacoes_adiciona_receita.setOnClickListener {

            chamaDialogDeAdicao(Tipo.RECEITA)
        }

        lista_transacoes_adiciona_despesa.setOnClickListener {

            chamaDialogDeAdicao(Tipo.DESPESA)
        }
    }

    private fun chamaDialogDeAdicao(tipo: Tipo) {
        val view = window.decorView

        AdicionaTransacaoDialog(this, view as ViewGroup)
            .chamaDialog(tipo, object : TransacaoDelegate {
                override fun delegate(transacao: Transacao) {
                    atualizarTransacoes(transacao)
                    lista_transacoes_adiciona_menu.close(true)
                }

            })
    }

    private fun atualizarTransacoes(transacaoCriada: Transacao) {
        transacoes.add(transacaoCriada)
        configuraResumo()
        configuraLista()
    }

    private fun configuraResumo() {
        val view: View = window.decorView
        val resumoView = ResumoView(this, view, transacoes)

        resumoView.atualiza()
    }

    private fun configuraLista() {
        lista_transacoes_listview.adapter = ListaTransacoesAdapter(transacoes, this)
    }
}
