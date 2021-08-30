import kotlin.math.tan

class Camera (var lookFrom: MyVector, var lookAt: MyVector, var vUp: MyVector, var vfov: Double, var aspectRatio: Double, var aperture: Double, var focusDist: Double) {

    val theta = degreesToRadian(vfov)
    val h = tan(theta / 2)
    val viewportHeight = 2.0 * h
    val viewportWidth = aspectRatio * viewportHeight

    val w = lookFrom.sub(lookAt).unit()
    val u = vUp.cross(w).unit()
    val v = w.cross(u)

    val origin = lookFrom
    val horizontal = u.multiply(viewportWidth).multiply(focusDist)
    val vertical = v.multiply(viewportHeight).multiply(focusDist)

    // Lower Left Corner
    val lowerLeftCorner = origin
        .sub(horizontal.divide(2.0))
        .sub(vertical.divide(2.0))
        .sub(w.multiply(focusDist))

    val lensRadius = aperture / 2.0


    fun getRay(s: Double, t: Double) : MyRay {
        // DOF
        val rd = MyVector.randomInUnitDisk().multiply(lensRadius)
        val offset = u.multiply(rd.x).add(v.multiply(rd.y))

        val dir = lowerLeftCorner.add(horizontal.multiply(s)).add(vertical.multiply(t)).sub(origin).sub(offset)
        return MyRay(origin.add(offset), dir)
    }
}