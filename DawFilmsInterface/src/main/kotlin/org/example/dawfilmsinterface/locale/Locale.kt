package org.example.dawfilmsinterface.locale

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Funcion para pasar a fecha local (ES)
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
fun LocalDate.toDefaultDateString(): String {
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale("es", "ES"))
    return this.format(formatter)
}

/**
 * Funcion para pasar a moneda local (€)
 * @author Jaime León, Alba García, Natalia González, Javier Ruiz, Germán Fernández
 * @since 1.0.0
 */
fun Double.toDefaultMoneyString(): String {
    return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(this)
}