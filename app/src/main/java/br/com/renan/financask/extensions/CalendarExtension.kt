package br.com.renan.financask.extensions

import java.text.SimpleDateFormat
import java.util.Calendar

fun Calendar.formataParaBrasileiro(): String {
    val formatoBrasileiro = "dd/MM/yyyy"
    val sdf = SimpleDateFormat(formatoBrasileiro)
    return sdf.format(this.time)
}