class HitRecord () {

    var frontFace: Boolean = true
    lateinit var mat: Material

    lateinit var p: MyVector
    lateinit var normal: MyVector
    var t = 0.0

    constructor(p: MyVector, normal: MyVector, t: Double) : this() {
        this.p = p
        this.normal = normal
        this.t = t
    }

    fun setFaceNormal(r: MyRay, outwardNormal: MyVector){
        frontFace = r.direction.dot(outwardNormal) < 0.0
        normal = if (frontFace) outwardNormal else outwardNormal.multiply(-1.0)
        assert(normal.length() == 1.0)
    }
}

abstract class Hittable {
    abstract fun hit(r: MyRay, tMin: Double, tMax: Double): HitRecord?
}