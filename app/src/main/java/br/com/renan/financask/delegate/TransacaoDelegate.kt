package br.com.renan.financask.delegate

import br.com.renan.financask.model.Transacao

interface TransacaoDelegate {
    fun delegate(transacao: Transacao)
}