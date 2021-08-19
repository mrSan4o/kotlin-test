package test

import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP

fun Double.toPenny(): Int = (this * 100).toBigDecimal().setScale(0, HALF_UP).toInt()
fun Int.toPriceAsPenny(): PriceType = PriceType.ofInt(this)

fun BigDecimal.toPrice(): PriceType = this.toDouble().toPrice()
fun Double.toPrice(): PriceType = PriceType.ofDouble(this)
fun Double?.toPriceOrZero(): PriceType = this?.toPrice().orZero()

operator fun PriceType.unaryMinus(): PriceType = this.negate()
fun PriceType.min(another: PriceType): PriceType = if (this > another) another else this
fun PriceType.negate(): PriceType = PriceType.ofInt(-1 * this.toIntAsPenny())
fun PriceType?.orZero(): PriceType = this ?: PriceType.ZERO

operator fun Int.times(another: PriceType): PriceType {
    return (this * another.toIntAsPenny()).toPriceAsPenny()
}

fun <T> Iterable<T>.sumPriceOfDouble(selector: (T) -> Double): PriceType {
    return this.sumOf(selector).toPrice()
}

fun <T> Iterable<T>.sumPriceOf(selector: (T) -> PriceType): PriceType {
    return this.sumOf { selector(it).toIntAsPenny() }.toPriceAsPenny()
}

fun <T> Sequence<T>.sumPriceOf(selector: (T) -> PriceType): PriceType {
    return this.sumOf { selector(it).toIntAsPenny() }.toPriceAsPenny()
}
