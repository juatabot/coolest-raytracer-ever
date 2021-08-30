import kotlin.math.sqrt
import kotlin.random.Random

class MyVector(val x: Double, val y: Double, val z: Double) {
    fun dot(other: MyVector): Double{
        return x * other.x + y * other.y + z * other.z
    }

    fun cross(other: MyVector): MyVector{
        return MyVector(
            y * other.z - z * other.y,
            z * other.x - x * other.z,
            x * other.y - y * other.x
        )
    }

    fun add(other: MyVector): MyVector{
        return MyVector(x + other.x, y + other.y, z + other.z)
    }

    fun sub(other: MyVector): MyVector{
//        return MyVector(x - other.x, y - other.y, z - other.z)
        return add(other.multiply(-1.0))
    }

    fun length(): Double{
        return sqrt(x * x + y * y + z * z)
    }

    fun multiply(s: Double): MyVector {
        return MyVector(x * s, y * s, z * s)
    }

    fun divide(d: Double): MyVector{
        return multiply(1 / d)
    }

    fun unit(): MyVector{
        return divide(length())
    }

    fun magnitudeSquared() : Double {
        return x * x + y * y + z * z
    }

    override fun toString(): String {
        return "($x, $y, $z)"
    }

    fun nearZero() : Boolean {
        val s = 0.0000001
        return (x < s && y < s && z < s)
    }

    fun pairMultiply(other: MyVector): MyVector {
        return MyVector(x * other.x, y * other.y, z * other.z)
    }

    companion object {

        fun random(): MyVector {
            return MyVector(Random.nextDouble(), Random.nextDouble(), Random.nextDouble())
        }

        fun random(min: Double, max: Double): MyVector {
            return MyVector(Random.nextDouble(min, max), Random.nextDouble(min, max), Random.nextDouble(min, max))
        }

        // Interchangeable for diffuse lighting
        fun randomInUnitSphere(): MyVector {
            while (true) {
                val p = random(-1.0, 1.0)
                if (p.magnitudeSquared() >= 1.0) {
                    continue
                }
                return p
            }
        }

        fun randomUnitVector(): MyVector {
            return randomInUnitSphere().unit()
        }

        fun randomInHemisphere(normal: MyVector) : MyVector {
            val inUnitSphere = randomInUnitSphere()
            if (inUnitSphere.dot(normal) > 0.0){
                return inUnitSphere
            }
            else {
                return inUnitSphere.multiply(-1.0)
            }
        }

        fun reflect(v: MyVector, n: MyVector) : MyVector{
            return v.sub(n.multiply(v.dot(n)).multiply(2.0))
        }

        fun randomInUnitDisk() : MyVector{
            var p: MyVector
            while (true) {
                p = MyVector(Random.nextDouble(-1.0, 1.0), Random.nextDouble(-1.0, 1.0), 0.0)
                if (p.length() * p.length() >= 1.0){
                    continue
                }
                return p
            }
        }
    }
}