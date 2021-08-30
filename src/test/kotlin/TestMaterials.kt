import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class TestMaterials {
    @Test
    fun testScatter() {
        try {
            val materialGround = Lambertian(MyVector(0.8, 0.8, 0.0))

            val scattered = MyRay(MyVector(0.0, 0.0, 0.0), MyVector(0.0, 0.0, 0.0))
            val attenuation = MyVector(0.0, 0.0, 0.0)
            val scatterResult = Material.ScatterResult(attenuation, scattered)

            val r = MyRay(MyVector(0.0, 0.0, 0.0), MyVector(0.0, 0.0, 0.0))
            val hitRec = HitRecord(MyVector(0.0, 0.0, 0.0), MyVector(0.0, 0.0, 0.0), 0.0)
            hitRec.setFaceNormal(r, MyVector(0.0, 0.0, 1.0))
            if (materialGround.scatter(r, hitRec, scatterResult)) {
                assertEquals(0.8, scatterResult.attenuation.x)
            }
        } catch (e: Exception) {
            for (i in e.stackTrace) {
                println("$i")
            }
        }
    }
}