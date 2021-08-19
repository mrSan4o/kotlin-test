package test

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class PriceType(private val value: Int) : Comparable<PriceType>{


    operator fun plus(another: PriceType): PriceType {
        return (this.value + another.value).toPriceAsPenny()
    }

     operator fun minus(another: PriceType): PriceType {
        return (this.value - another.value).toPriceAsPenny()
    }

     operator fun times(another: PriceType): PriceType {
        return (this.value * another.value).toPriceAsPenny()
    }

     operator fun plus(another: Int): PriceType {
        return (this.value + another).toPriceAsPenny()
    }

     operator fun times(another: Int): PriceType {
        return (this.value * another).toPriceAsPenny()
    }

     operator fun div(another: PriceType): PriceType {
        return (this.value / another.value).toPriceAsPenny()
    }

     operator fun div(another: Int): PriceType {
        return (this.value / another).toPriceAsPenny()
    }

    fun toBigDecimal(): BigDecimal = BigDecimal(this.value / 100.0).setScale(2, RoundingMode.HALF_UP)

    fun toDouble(): Double = this.toBigDecimal().toDouble()

    fun toIntAsPenny(): Int = this.value

    fun isZero(): Boolean = this.value == PriceType.ZERO.value

    override fun compareTo(other: PriceType): Int {
        return this.value.compareTo(other.value)
    }

    companion object {
        val ZERO: PriceType = ofDouble(0.0)

       

        fun ofDouble(value: Double): PriceType = PriceType(value.toPenny())
        fun ofInt(value: Int): PriceType = PriceType(value)
    }

}