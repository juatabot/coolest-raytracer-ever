import javolution.testing.TestContext.assertEquals
import org.junit.jupiter.api.Test

class TestTriangleRayIntersection {

    @Test
    fun testTriangleRayIntersection() {
        val t = Triangle(arrayListOf(MyVector(0.0, 1.0, -1.0),
            MyVector(0.0, 0.0, -1.0),
            MyVector(1.0, 0.0, -1.0)),
            Metal(MyVector(0.8, 0.6, 0.2))
        )

        val r = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(0.0, 0.0, -1.0)
        )
        val r2 = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(0.5, 0.5, -1.0)
        )
        val r3 = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(-1.0, 0.0, -1.0)
        )

        assertEquals(true, t.hit(r, 0.0, Double.POSITIVE_INFINITY) is HitRecord)
        assertEquals(true, t.hit(r2, 0.0, Double.POSITIVE_INFINITY) is HitRecord)
        assertEquals(null, t.hit(r3, 0.0, Double.POSITIVE_INFINITY))
    }

    @Test
    fun testTriangleRayIntersection2(){
        val t = Triangle(arrayListOf(MyVector(-1.0, -1.0, -1.0),
            MyVector(1.0, -1.0, -1.0),
            MyVector(1.0, 1.0, -1.0)),
            Metal(MyVector(0.8, 0.6, 0.2))
        )
        var r = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(0.0, 0.0, -1.0)
        )
        assertEquals(true, t.hit(r, 0.0, Double.POSITIVE_INFINITY) is HitRecord)

        r = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(-1.0, 0.0, -1.0)
        )
        assertEquals(null, t.hit(r, 0.0, Double.POSITIVE_INFINITY))
        println(t.hit(r, 0.0, Double.POSITIVE_INFINITY)?.t)
    }

    @Test
    fun testTriangleRayParallel() {
        val t = Triangle(arrayListOf(MyVector(0.0, 1.0, -1.0),
            MyVector(0.0, 1.0, -2.0),
            MyVector(0.0, -1.0, -1.0)),
            Metal(MyVector(0.8, 0.6, 0.2))
        )

        val r = MyRay(
            MyVector(0.0, 0.0, 0.0),
            MyVector(0.0, 0.0, -1.0)
        )
        assertEquals(null, t.hit(r, 0.0, Double.POSITIVE_INFINITY))
    }

    @Test
    fun testTriangleRayEpsilon() {
        val t = Triangle(arrayListOf(MyVector(0.0, 0.0, -1.0),
            MyVector(0.0, 0.0, -2.0),
            MyVector(0.0, 1.0, -1.0)),
            Metal(MyVector(0.8, 0.6, 0.2))
        )

        val r = MyRay(
            MyVector(1.0, 0.0, 0.0),
            MyVector(-1.0, 0.0, -2.0)
        )
        assertEquals(true, t.hit(r, 0.0, Double.POSITIVE_INFINITY) is HitRecord)
        assertEquals(1.0, t.hit(r, 0.0, Double.POSITIVE_INFINITY)?.normal?.x ?: null)
    }
}