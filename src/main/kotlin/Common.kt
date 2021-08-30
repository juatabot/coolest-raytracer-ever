import kotlin.math.PI
import kotlin.random.Random.Default.nextDouble

fun degreesToRadian(d: Double): Double {
    return d * PI / 180.0
}

fun clamp(x: Double, min: Double, max: Double) : Double {
    if (x < min) {
        return min
    }
    else if (x > max) {
        return max
    }
    return x
}