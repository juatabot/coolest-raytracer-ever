import kotlin.test.Test
import kotlin.test.assertEquals

class TestMyVector {
    @Test
    fun testDot(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v2 = MyVector(2.0, 2.0, 1.0)
        assertEquals(3.0, v1.dot(v2))
    }

    @Test
    fun testCross(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v2 = MyVector(2.0, 2.0, 1.0)
        val v3 = v1.cross(v2)
        assertEquals(-2.0, v3.x)
        assertEquals(1.0, v3.y)
        assertEquals(2.0, v3.z)
    }

    @Test
    fun testAdd(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v2 = MyVector(2.0, 2.0, 1.0)
        val v3 = v1.add(v2)
        assertEquals(3.0, v3.x)
        assertEquals(2.0, v3.y)
        assertEquals(2.0, v3.z)
    }

    @Test
    fun testSub(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v2 = MyVector(2.0, 2.0, 1.0)
        val v3 = v1.sub(v2)
        assertEquals(-1.0, v3.x)
        assertEquals(-2.0, v3.y)
        assertEquals(.0, v3.z)
    }

    @Test
    fun testLength(){
        val v1 = MyVector(0.0, 0.0, 1.0)
        assertEquals(1.0, v1.length())
    }

    @Test
    fun testMultiply(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v3 = v1.multiply(2.0)
        assertEquals(2.0, v3.x)
        assertEquals(0.0, v3.y)
        assertEquals(2.0, v3.z)

        val v4 = v1.multiply(0.5)
        assertEquals(0.5, v4.x)
        assertEquals(0.0, v4.y)
        assertEquals(0.5, v4.z)
    }

    @Test
    fun testDivide(){
        val v1 = MyVector(1.0, 0.0, 1.0)
        val v3 = v1.divide(2.0)
        assertEquals(0.5, v3.x)
        assertEquals(0.0, v3.y)
        assertEquals(0.5, v3.z)
    }

    @Test
    fun testUnit(){
        val v1 = MyVector(0.0, 0.0, 1.0)
        val v3 = v1.unit()
        assertEquals(0.0, v3.x)
        assertEquals(0.0, v3.y)
        assertEquals(1.0, v3.z)
    }

    @Test
    fun testUnitSphere(){
        val u = MyVector.randomInUnitSphere()
        assertEquals(true, u.length() <= 1)
    }

    @Test
    fun testNearZero() {
        assertEquals(true, MyVector(0.0000000000001, 0.0000000000001, 0.0000000000001).nearZero())
    }

    @Test
    fun testPairMultiply() {
        val v1 = MyVector(2.0, 0.0, 1.0)
        val v2 = MyVector(3.0, 2.0, 1.0)
        val v3 = v1.pairMultiply(v2)
        assertEquals(6.0, v3.x)
        assertEquals(0.0, v3.y)
        assertEquals(1.0, v3.z)
    }

    @Test
    fun testRandomInUnitDisk() {
        val r = MyVector.randomInUnitDisk()
        assertEquals(true, r.x <= 1.0)
        assertEquals(true, r.x >= -1.0)
        assertEquals(true, r.y <= 1.0)
        assertEquals(true, r.y >= -1.0)
        assertEquals(true, r.z == 0.0)
    }
}
