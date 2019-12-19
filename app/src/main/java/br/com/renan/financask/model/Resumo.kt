package br.com.renan.financask.model

import java.math.BigDecimal

class Resumo(private val transacoes: List<Transacao>) {

    fun getReceita() {              // Função normalmente escrita em java
        somaPor(Tipo.RECEITA)       // usando a sintaxe padrão
        // da linguagem Kotlin
    }

    val receita get() = somaPor(Tipo.RECEITA) // Função feita conforme exemplo acima,
    // mas convertida em uma property,
    // porém ambas trazendo a mesma funcionalidade

    /*var totalReceita = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.RECEITA) {
                totalReceita = totalReceita.plus(transacao.valor)
            }
        }*/

    val despesa get() = somaPor(Tipo.DESPESA)

    /*var totalDespesa = BigDecimal.ZERO
        for (transacao in transacoes) {
            if (transacao.tipo == Tipo.DESPESA) {
                totalDespesa = totalDespesa.plus(transacao.valor)
            }
        }*/


    val total get() = receita.subtract(despesa)

    private fun somaPor(tipo: Tipo): BigDecimal {
        val somaDeTransacoesPeloTipo = BigDecimal(transacoes
            .filter { it.tipo == tipo }
            .sumByDouble { it.valor.toDouble() })

        return somaDeTransacoesPeloTipo
    }
}

